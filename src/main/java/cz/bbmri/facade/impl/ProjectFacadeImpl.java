package cz.bbmri.facade.impl;

import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.AttachmentType;
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
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
@Controller("projectFacade")
public class ProjectFacadeImpl extends BasicFacade implements ProjectFacade {

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

    public boolean approveProject(Long projectId, Long loggedUserId, ValidationErrors errors) {
        notNull(projectId);
        notNull(loggedUserId);

        if (!projectService.approve(projectId, loggedUserId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.ApproveFailed"));
            return false;
        }
        return true;
    }

    public boolean denyProject(Long projectId, Long loggedUserId, ValidationErrors errors) {
        notNull(projectId);
        notNull(loggedUserId);

        if (!projectService.deny(projectId, loggedUserId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.DenyFailed"));
            return false;
        }
        return true;
    }

    public List<User> getProjectAdministrators(Long projectId) {
        throw new NotImplementedException("TODO");
    }

    //List<ProjectAdministrator> getProjectAdministrators(Long biobankId);
    public Project createProject(Project project,
                                 Long loggedUserId,
                                 String bbmriPath,
                                 ValidationErrors errors) {
        notNull(project);
        notNull(loggedUserId);
        notNull(errors);
        notNull(bbmriPath);

        project = projectService.create(project, loggedUserId);

        if (project != null) {

            // If this is the first created instance of Project and Biobank than create cz.bbmri general folder
            if (!createFolderStructure(bbmriPath, project, errors)) {
                projectService.remove(project.getId());
                return null;
            }
        }

        return project;

    }

    private boolean createFolderStructure(String bbmriPath, Project project, ValidationErrors errors) {

        String projectPath = bbmriPath + Attachment.PROJECT_FOLDER;
        String thisProjectPath = bbmriPath + Attachment.PROJECT_FOLDER_PATH + project.getId().toString();

        if (!FacadeUtils.folderExists(bbmriPath)) {
            if (FacadeUtils.createFolder(bbmriPath, errors) != SUCCESS) {
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

    public boolean updateProject(Project project) {
        notNull(project);
        return projectService.update(project) != null;

    }

    public boolean removeProject(Long projectId, String bbmriPath, ValidationErrors errors) {
        notNull(projectId);
        notNull(errors);
        notNull(bbmriPath);

        if (projectService.remove(projectId)) {
            return FacadeUtils.recursiveDeleteFolder(bbmriPath +
                    Attachment.PROJECT_FOLDER_PATH +
                    projectId.toString(), errors) == SUCCESS;
        }
        return false;
    }

    // TODO: přidat mezi parametry ValidationErrors
    public boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors) {
        notNull(objectId);
        notNull(newAdministratorId);
        notNull(permission);
        notNull(errors);

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

        projectService.assignAdministrator(projectDB, newAdministratorId, permission);
        return true;

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
                                String bbmriPath,
                                ValidationErrors errors) {
        notNull(fileBean);
        notNull(attachmentType);
        notNull(projectId);
        notNull(bbmriPath);
        notNull(errors);

        Project projectDB = projectService.get(projectId);

        if (projectDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.ProjectDoesntExist"));
            return -1;
        }

        if (!createFolderStructure(bbmriPath, projectDB, errors)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.CantCreateFolderStructure"));
            return -1;
        }

        Attachment attachment = new Attachment();
        attachment.setFileName(fileBean.getFileName());
        attachment.setContentType(fileBean.getContentType());
        attachment.setSize(fileBean.getSize());
        attachment.setAttachmentType(attachmentType);
        attachment.setAbsolutePath(bbmriPath +
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

    public boolean deleteAttachment(Long attachmentId, ValidationErrors errors) {
        notNull(attachmentId);
        notNull(errors);

        Attachment attachment = attachmentService.get(attachmentId);
        if (attachment == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return true;
        }
        if (FacadeUtils.deleteFileAndParentFolder(attachment.getAbsolutePath(), errors) == SUCCESS) {
            return attachmentService.remove(attachmentId);
        }

        return false;
    }


    public List<Attachment> getAttachments(Long projectId) {
        notNull(projectId);

        Project projectDB = projectService.eagerGet(projectId, false, false, true, false);

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

    // TODO: přidat mezi parametry ValidationErrors
    public boolean changeAdministratorPermission(Long objectAdministrator,
                                                 Permission permission,
                                                 ValidationErrors errors) {
        notNull(objectAdministrator);
        notNull(permission);
        notNull(errors);

        ProjectAdministrator pa = projectAdministratorService.get(objectAdministrator);
        if (pa == null) {
            return false;
            // TODO: exception
        }

        // TODO: There must solved situation of last administrator remove

        pa.setPermission(permission);
        return projectAdministratorService.update(pa) != null;
    }

    public boolean removeAdministrator(Long objectAdministrator, ValidationErrors errors) {
        notNull(objectAdministrator);
        notNull(errors);

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

        return true;
    }

    public List<Project> getProjects(Long userId) {
        notNull(userId);

        User userDB = userService.eagerGet(userId, false, true, false);
        List<ProjectAdministrator> paList = userDB.getProjectAdministrators();
        List<Project> projects = new ArrayList<Project>();

        for (ProjectAdministrator pa : paList) {
            projects.add(pa.getProject());
        }

        return projects;
    }

    public boolean hasBiobankExecutePermission(Long userId){
       notNull(userId);
       return biobankAdministratorService.hasSameOrHigherPermission(userId, Permission.EXECUTOR);

    }

    public boolean markAsFinished(Long projectId){
        notNull(projectId);
        return projectService.changeState(projectId, ProjectState.FINISHED) != null;
    }

    public ProjectAdministrator getProjectAdministrator(Long projectAdministratorId){
        notNull(projectAdministratorId);
        return projectAdministratorService.get(projectAdministratorId);
    }


}
