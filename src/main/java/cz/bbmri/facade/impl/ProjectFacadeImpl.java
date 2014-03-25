package cz.bbmri.facade.impl;

import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.AttachmentType;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.facade.ProjectFacade;
import cz.bbmri.service.*;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
@Controller("projectFacade")
public class ProjectFacadeImpl extends BasicFacade implements ProjectFacade {

    @Value("${StoragePath}")
    private String storagePath;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectAdministratorService projectAdministratorService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private BiobankAdministratorService biobankAdministratorService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private SampleQuestionService sampleQuestionService;

    public boolean approveProject(Long projectId, Long loggedUserId, ValidationErrors errors) {
        notNull(projectId);
        notNull(loggedUserId);

        if (!projectService.approve(projectId, loggedUserId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.ApproveFailed"));
            return false;
        }
        // Project can't be null - otherwise projectService.approve would have failed
        Project project = projectService.get(projectId);
        String msg = "Project: " + project.getName() + " was approved.";

        notificationService.create(getProjectAdministratorsUsers(projectId),
                NotificationType.PROJECT_DETAIL, msg, project.getId());

        return true;
    }

    public boolean denyProject(Long projectId, Long loggedUserId, ValidationErrors errors) {
        notNull(projectId);
        notNull(loggedUserId);

        if (!projectService.deny(projectId, loggedUserId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.DenyFailed"));
            return false;
        }

        Project project = projectService.get(projectId);
        if (project != null) {

            String msg = "Project: " + project.getName() + " was denied.";

            notificationService.create(getProjectAdministratorsUsers(projectId),
                    NotificationType.PROJECT_DETAIL, msg, project.getId());
        }

        return true;
    }

//    public List<User> getProjectAdministratorsUsers(Long projectId) {
//        Project projectDB = projectService.eagerGet(projectId, true, false, false, false);
//        List<User> users = new ArrayList<User>();
//        for(ProjectAdministrator projectAdministrator : projectDB.getProjectAdministrators()){
//            users.add(projectAdministrator.getUser());
//        }
//        return users;
//    }

    //List<ProjectAdministrator> getProjectAdministrators(Long biobankId);

    public Project createProject(Project project,
                                 Long loggedUserId,
                                 ValidationErrors errors) {
        notNull(project);
        notNull(loggedUserId);
        notNull(errors);

        project = projectService.create(project, loggedUserId);

        if (project != null) {

            // If this is the first created instance of Project and Biobank than create cz.bbmri general folder
            if (!createFolderStructure(project, errors)) {
                projectService.remove(project.getId());
                return null;
            }
        }

        return project;

    }

    private boolean createFolderStructure( Project project, ValidationErrors errors) {

        String projectPath = storagePath + Attachment.PROJECT_FOLDER;
        String thisProjectPath = storagePath + Attachment.PROJECT_FOLDER_PATH + project.getId().toString();

        if (!FacadeUtils.folderExists(storagePath)) {
            if (FacadeUtils.createFolder(storagePath, errors) != SUCCESS) {
                return false;
            }
        }

        // If this is the first created project - create folder for all projects

        if (!FacadeUtils.folderExists(projectPath)) {
            if (FacadeUtils.createFolder(projectPath, errors) != SUCCESS) {
                return false;
            }
        }

        // Folder for the project

        if (!FacadeUtils.folderExists(thisProjectPath)) {
            if (FacadeUtils.createFolder(thisProjectPath, errors) != SUCCESS) {
                return false;
            }
        }

        return true;

    }

    public boolean updateProject(Project project, Long loggedUserId) {
        notNull(project);
        notNull(loggedUserId);

        if (projectService.update(project) == null) {
            return false;
        }

        String msg = "Project: " + project.getName() + " was updated.";

        notificationService.create(getOtherProjectWorkers(project, loggedUserId),
                NotificationType.PROJECT_DETAIL, msg, project.getId());

        return true;
    }

    public boolean removeProject(Long projectId, ValidationErrors errors, Long loggedUserId) {
        notNull(projectId);
        notNull(errors);
        notNull(loggedUserId);

        Project project = projectService.get(projectId);

        // Necessary to be able to send notification to all project members after delete

        List<User> users = getOtherProjectWorkers(project, loggedUserId);

        if (!projectService.remove(projectId)) {
            return false;
        }

        String msg = "Project: " + project.getName() + " was removed.";

        notificationService.create(users, NotificationType.PROJECT_DELETE, msg, project.getId());

        boolean result = FacadeUtils.recursiveDeleteFolder(storagePath +
                Attachment.PROJECT_FOLDER_PATH +
                projectId.toString(), errors) == SUCCESS;

        return result;

    }

    public boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors, Long loggedUserId) {
        notNull(objectId);
        notNull(newAdministratorId);
        notNull(permission);
        notNull(errors);
        notNull(loggedUserId);

        Project projectDB = projectService.get(objectId);
        User newAdmin = userService.get(newAdministratorId);

        if (projectDB == null || newAdmin == null) {
            return false;
        }

        // TODO: kontrola zda uz novyAdministrator neni k projektu prirazen

        if (projectAdministratorService.get(objectId, newAdministratorId) != null) {
            //TODO: exception - he is already admin
            return false;
        }

        boolean result = projectService.assignAdministrator(projectDB, newAdministratorId, permission);

        if (result) {

            String msg = newAdmin.getWholeName() + " was assigned as administrator with permission: " + permission + " to project: " + projectDB.getName() + ".";

            notificationService.create(getOtherProjectWorkers(projectDB, loggedUserId),
                    NotificationType.PROJECT_ADMINISTRATOR, msg, projectDB.getId());
        }

        return result;

    }

    public boolean isApproved(Long projectId) {
        throw new NotImplementedException("TODO");
    }

    /* -1 not success
    *   0 success
    *   1 overwritten
    *   */
    public int createAttachment(FileBean fileBean,
                                AttachmentType attachmentType,
                                Long projectId,
                                ValidationErrors errors,
                                Long loggedUserId) {
        notNull(fileBean);
        notNull(attachmentType);
        notNull(projectId);
        notNull(errors);
        notNull(loggedUserId);

        Project projectDB = projectService.get(projectId);

        if (projectDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.ProjectDoesntExist"));
            return -1;
        }

        if (!createFolderStructure(projectDB, errors)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.CantCreateFolderStructure"));
            return -1;
        }

        Attachment attachment = new Attachment();
        attachment.setFileName(fileBean.getFileName());
        attachment.setContentType(fileBean.getContentType());
        attachment.setSize(fileBean.getSize());
        attachment.setAttachmentType(attachmentType);
        attachment.setAbsolutePath(storagePath +
                Attachment.PROJECT_FOLDER_PATH +
                projectId.toString() +
                File.separator +
                fileBean.getFileName());

        File file = new File(attachment.getAbsolutePath());

        boolean overwrite = false;

        if (file.exists()) {
            overwrite = true;
        }

        try {

            fileBean.save(file);

        } catch (IOException e) {

            attachmentService.remove(attachment.getId());
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.IOException"));
            return -1;

        }

//      Create DB record only if file is new
        if (!overwrite) {
            attachment = attachmentService.create(projectId, attachment);

            if (attachment == null) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.FileUploadedButDatabaseRecordNotCreated"));
                return -1;
            }
        } else {
            attachmentService.update(attachment);
        }

        String msg = "New attachment was uploaded to project: " + projectDB.getName() + ".";

        notificationService.create(getOtherProjectWorkers(projectDB, loggedUserId),
                NotificationType.PROJECT_ATTACHMENT, msg, projectDB.getId());

        if (overwrite) {
            return 1;
        }
        return 0;
    }

    public StreamingResolution downloadFile(Long attachmentId) throws FileNotFoundException {
        notNull(attachmentId);

        Attachment attachment = attachmentService.get(attachmentId);
        if (attachment == null) {
            return null;
            // TODO: exception
        }
        FileInputStream fis = new FileInputStream(attachment.getAbsolutePath());
        return new StreamingResolution(attachment.getContentType(), fis).setFilename(attachment.getFileName());
    }

    public boolean deleteAttachment(Long attachmentId, ValidationErrors errors, Long loggedUserId) {
        notNull(attachmentId);
        notNull(errors);
        notNull(loggedUserId);

        Attachment attachment = attachmentService.get(attachmentId);
        if (attachment == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return false;
        }
        if (FacadeUtils.deleteFileAndParentFolder(attachment.getAbsolutePath(), errors) == SUCCESS) {

            Project project = attachment.getProject();

            String msg = "Attachment: " + attachment.getFileName() + " from project: " + project.getName() + " was deleted.";

            notificationService.create(getOtherProjectWorkers(project, loggedUserId),
                    NotificationType.PROJECT_ATTACHMENT, msg, project.getId());

            return attachmentService.remove(attachmentId);
        }

        return false;
    }


    public List<Attachment> getAttachments(Long projectId) {
        notNull(projectId);
        Project projectDB = projectService.get(projectId);
        //  Project projectDB = projectService.eagerGet(projectId, false, true, false);

        return projectDB.getAttachments();
    }

    public List<Project> all() {
        return projectService.all();
    }

    public Project get(Long id) {
        notNull(id);

        return projectService.get(id);
    }

    public boolean hasPermission(Permission permission, Long objectId, Long userId) {
        notNull(permission);
        notNull(objectId);
        notNull(userId);

        ProjectAdministrator pa = projectAdministratorService.get(objectId, userId);

        if (pa == null) {
            return false;
        }

        return pa.getPermission().include(permission);
    }

    public boolean changeAdministratorPermission(Long objectAdministrator,
                                                 Permission permission,
                                                 ValidationErrors errors,
                                                 Long loggedUserId) {
        notNull(objectAdministrator);
        notNull(permission);
        notNull(errors);
        notNull(loggedUserId);

        ProjectAdministrator pa = projectAdministratorService.get(objectAdministrator);
        if (pa == null) {
            return false;
            // TODO: exception
        }

        // TODO: There must solved situation of last administrator remove

        pa.setPermission(permission);
        boolean result = projectAdministratorService.update(pa) != null;
        if (result) {
            Project project = pa.getProject();

            User user = pa.getUser();

            String msg = "Permission of " + user.getWholeName() + " was changed to: " + permission;

            notificationService.create(getOtherProjectWorkers(project, loggedUserId),
                    NotificationType.PROJECT_ADMINISTRATOR, msg, project.getId());
        }
        return result;
    }

    public boolean removeAdministrator(Long objectAdministrator, ValidationErrors errors, Long loggedUserId) {
        notNull(objectAdministrator);
        notNull(errors);
        notNull(loggedUserId);

        ProjectAdministrator pa = projectAdministratorService.get(objectAdministrator);
        if (pa == null) {
            return false;
            // TODO: exception
        }

        // TODO: There must solved situation of last administrator remove

//        projectAdministratorService.remove(pa.getId());
//        userDB = userService.eagerGet(userDB.getId(), false, false, true);
//        if (userDB.getProjectAdministrators().size() < 1) {
//            // If userDB doesn't manage other Biobank than the deleted one -> remove its system role
//            userService.removeSystemRole(userDB.getId(), SystemRole.PROJECT_TEAM_MEMBER);
//        }

        // TODO: NOTIFICATION
//        Project project = pa.getProject();
//
//        notificationService.create(getOtherProjectWorkers(project, loggedUserId),
//                NotificationType.PROJECT_REMOVE_ADMINISTRATOR, pa.getUser().getWholeName(), project.getId());

        return true;
    }

    public List<Project> getProjects(Long userId) {
        notNull(userId);
        User userDB = userService.get(userId);
        //  User userDB = userService.eagerGet(userId, false, true, false, false, false);
        Set<ProjectAdministrator> paSet = userDB.getProjectAdministrators();
        List<Project> projects = new ArrayList<Project>();

        if (paSet == null) {
            return projects;
        }

        if (paSet.isEmpty()) {
            return projects;
        }

        for (ProjectAdministrator pa : paSet) {

            projects.add(pa.getProject());
        }

        return projects;
    }

    public List<Project> getProjects(Long userId, ProjectState projectState) {
        if (userId == null) {
            logger.debug("UserId can't be null");
            return null;
        }

        if (projectState == null) {
            logger.debug("projectState can't be null");
            return null;
        }

        return projectService.getAllByUserAndProjectState(projectState, userId);
    }

    public boolean hasBiobankExecutePermission(Long userId) {
        notNull(userId);
        return biobankAdministratorService.hasSameOrHigherPermission(userId, Permission.EXECUTOR);

    }

    public boolean markAsFinished(Long projectId, Long loggedUserId) {
        notNull(projectId);
        notNull(loggedUserId);

        boolean result = projectService.changeState(projectId, ProjectState.FINISHED) != null;
        if (result) {

            Project project = projectService.get(projectId);

            String msg = "State of project: " + project.getName() + " was changed to finished.";

            notificationService.create(getOtherProjectWorkers(project, loggedUserId),
                    NotificationType.PROJECT_DETAIL, msg, project.getId());
        }

        return result;
    }

    public ProjectAdministrator getProjectAdministrator(Long projectAdministratorId) {
        notNull(projectAdministratorId);
        return projectAdministratorService.get(projectAdministratorId);
    }

    public List<Biobank> getAllBiobanks() {
        return biobankService.all();
    }

    public List<SampleRequest> getProjectSampleRequests(Long projectId) {
        notNull(projectId);

        Project projectDB = projectService.get(projectId);
        //    Project projectDB = projectService.eagerGet(projectId, false, false, true);
        return projectDB.getSampleRequests();

    }

    public List<Project> allOrderedBy(String orderByParam, boolean desc) {
         return projectService.allOrderedBy(orderByParam, desc);
     }

    public List<Project> getMyProjectsSorted(Long userId, String orderByParam, boolean desc){
        return projectService.getMyProjectsSorted(userId, orderByParam, desc);
    }

    public List<Attachment> getSortedAttachments(Long projectId, String orderByParam, boolean desc){
        return attachmentService.getSortedAttachments(projectId, orderByParam, desc);
    }

}
