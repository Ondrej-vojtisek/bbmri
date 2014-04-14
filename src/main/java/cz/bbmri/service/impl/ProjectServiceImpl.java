package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.ProjectService;
import cz.bbmri.service.exceptions.LastManagerException;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("projectService")
public class ProjectServiceImpl extends BasicServiceImpl implements ProjectService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private ProjectAdministratorDao projectAdministratorDao;

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private NotificationDao notificationDao;

    public Project create(Project project, Long userId) {
        notNull(project);
        notNull(userId);

        User userDB = userDao.get(userId);

        if (userDB == null) {
            logger.debug("Object retrieved from database is null");
            return null;

        }

        project.setProjectState(ProjectState.NEW);
        project.setCreated(new Date());
        projectDao.create(project);
        assignAdministrator(project, userId, Permission.MANAGER);
        projectDao.update(project);

        return project;
    }

    public boolean remove(Long id) {
        notNull(id);

        Project projectDB = projectDao.get(id);

        if (projectDB == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }

        User judge = projectDB.getJudgedByUser();
        if (judge != null) {

            judge.getJudgedProjects().remove(projectDB);
            userDao.update(judge);
        }

        List<SampleRequest> sampleRequests = projectDB.getSampleRequests();
        if (sampleRequests != null) {

            for (SampleRequest sampleRequest : sampleRequests) {
                sampleQuestionDao.remove(sampleRequest);
            }
        }

        List<Attachment> attachments = projectDB.getAttachments();
        if (attachments != null) {
            for (Attachment attachment : attachments) {
                attachmentDao.remove(attachment);
            }
        }

        Set<ProjectAdministrator> projectAdministrators = projectDB.getProjectAdministrators();
        if (projectAdministrators != null) {

            logger.debug("projectAttachments");

            for (ProjectAdministrator pa : projectAdministrators) {

                User userDB = pa.getUser();
                if (userDB.getProjectAdministrators().size() == 1 &&
                        userDB.getSystemRoles().contains(SystemRole.PROJECT_TEAM_MEMBER)) {

                    userDB.getSystemRoles().remove(SystemRole.PROJECT_TEAM_MEMBER);
                    userDao.update(userDB);
                }


                pa.setUser(null);
                pa.setProject(null);
                projectAdministratorDao.remove(pa);
            }

            projectDao.remove(projectDB);

        }
        return true;
    }

    public Project changeState(Long projectId, ProjectState projectState) {
        notNull(projectId);
        notNull(projectState);

        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            logger.debug("Object retrieved from database is null");
            return null;
        }

        if (projectState.equals(ProjectState.APPROVED) ||
                projectState.equals(ProjectState.DENIED)) {
            logger.debug("This method purpose is not to approve or deny project");
            return null;
        }

        projectDB.setProjectState(projectState);
        projectDao.update(projectDB);
        return projectDB;
    }

    public Project update(Project project) {
        notNull(project);

        Project projectDB = projectDao.get(project.getId());
        if (projectDB == null) {
            logger.debug("Object retrieved from database is null");
            return null;
        }

        if (project.getAnnotation() != null) {
            projectDB.setAnnotation(project.getAnnotation());
        }

        if (project.getName() != null) {
            projectDB.setName(project.getName());
        }

        if (project.getPrincipalInvestigator() != null) {
            projectDB.setPrincipalInvestigator(project.getPrincipalInvestigator());
        }

     /*
        I am not sure if other attributes can be changed during project lifetime.
        */

        projectDao.update(projectDB);
        return project;
    }

    @Transactional(readOnly = true)
    public List<Project> all() {
        return projectDao.all();
    }

    @Transactional(readOnly = true)
    public List<Project> getEagerByUserWithRequests(Long userId) {
        notNull(userId);

        User userDB = userDao.get(userId);
        Set<ProjectAdministrator> paSet = userDB.getProjectAdministrators();
        List<Project> projects = new ArrayList<Project>();
        for (ProjectAdministrator pa : paSet) {
            projects.add(pa.getProject());
        }

        return projects;
    }

    @Transactional(readOnly = true)
    public List<Project> getAllByProjectState(ProjectState projectState) {
        notNull(projectState);

        return projectDao.getAllByProjectState(projectState);
    }

    @Transactional(readOnly = true)
    public List<Project> getAllByUserAndProjectState(ProjectState projectState, Long userId) {
        if (projectState == null) {
            logger.debug("ProjectState is null");
            return null;
        }

        if (userId == null) {
            logger.debug("UserId is null");
            return null;
        }

        User userDB = userDao.get(userId);

        if (userId == null) {
            logger.debug("UserDB is null");
            return null;
        }

        return projectDao.getAllByUserAndProjectState(userDB, projectState);
    }

    public boolean approve(Long projectId, Long userId) {
        notNull(projectId);
        notNull(userId);

        Project projectDB = projectDao.get(projectId);

        if (projectDB == null) {
            logger.debug("Object retrieved from database is null - projectDB");
            return false;
        }
        User userDB = userDao.get(userId);
        if (userDB == null) {
            logger.debug("Object retrieved from database is null - userBD");
            return false;
        }

        if (!projectDB.getProjectState().equals(ProjectState.NEW)) {
            logger.debug("Project is not NEW so it can't be approved");
            return false;
        }

        projectDB.setProjectState(ProjectState.APPROVED);
        projectDB.setJudgedByUser(userDB);
        projectDao.update(projectDB);
        userDB.getJudgedProjects().add(projectDB);
        userDao.update(userDB);
        return true;
    }

    public boolean deny(Long projectId, Long userId) {
        notNull(projectId);
        notNull(userId);

        Project projectDB = projectDao.get(projectId);

        if (projectDB == null) {
            logger.debug("Object retrieved from database is null - projectBD");
            return false;
        }
        User userDB = userDao.get(userId);
        if (userDB == null) {
            logger.debug("Object retrieved from database is null - userBD");
            return false;
        }

        if (!projectDB.getProjectState().equals(ProjectState.NEW)) {
            logger.debug("Project is not NEW so it can't be denied");
            return false;
        }

        projectDB.setProjectState(ProjectState.DENIED);
        projectDB.setJudgedByUser(userDB);
        projectDao.update(projectDB);
        userDB.getJudgedProjects().add(projectDB);
        userDao.update(userDB);
        return true;
    }

    @Transactional(readOnly = true)
    public Project get(Long id) {
        notNull(id);
        return projectDao.get(id);
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return projectDao.count();
    }


    public boolean removeAdministrator(ProjectAdministrator objectAdministrator) throws LastManagerException {
        notNull(objectAdministrator);

        User userDB = objectAdministrator.getUser();
        Project projectDB = objectAdministrator.getProject();

        if (userDB == null || projectDB == null) {
            logger.debug("Object retrieved from database is null - userBD or projectDB");
            return false;
        }

              /* Situation when we want to remove last manager. */
        if (isLastManager(objectAdministrator)) {
            throw new LastManagerException("User: " + userDB.getWholeName()
                    + " is the only administrator with MANAGER permission associated to biobank: "
                    + projectDB.getName() + ". He can't be removed!");
        }

        if (userDB.getProjectAdministrators().size() == 1 &&
                userDB.getSystemRoles().contains(SystemRole.PROJECT_TEAM_MEMBER)) {
            userDB.getSystemRoles().remove(SystemRole.PROJECT_TEAM_MEMBER);
            userDao.update(userDB);
        }

        projectAdministratorDao.remove(objectAdministrator);
        return true;
    }

    @Transactional(readOnly = true)
    public boolean isLastManager(ProjectAdministrator objectAdministrator) {
        if (!objectAdministrator.getPermission().equals(Permission.MANAGER)) {
            return false;
        }

        if (projectAdministratorDao.get(objectAdministrator.getProject(), Permission.MANAGER).size() > 1) {
            return false;
        }

        return true;
    }

    public boolean changeAdministratorPermission(ProjectAdministrator objectAdministrator, Permission permission) throws LastManagerException {
        notNull(objectAdministrator);
        notNull(permission);

        /* Situation when we want to remove last manager. */

        if (!permission.equals(Permission.MANAGER) && isLastManager(objectAdministrator)) {
            throw new LastManagerException("User: " + objectAdministrator.getUser().getWholeName()
                    + " is the only administrator with MANAGER permission associated to project: "
                    + objectAdministrator.getProject().getName() + ". He can't be removed!");
        }

        objectAdministrator.setPermission(permission);
        projectAdministratorDao.update(objectAdministrator);
        return true;
    }

    public boolean assignAdministrator(Project object, Long userId, Permission permission) {
        notNull(object);
        notNull(userId);
        notNull(permission);

        User userDB = userDao.get(userId);
        if (userDB == null) {
            logger.debug("Object retrieved from database is null - userBD");
            return false;
        }

        ProjectAdministrator pa = new ProjectAdministrator();
        pa.setPermission(permission);
        pa.setProject(object);
        pa.setUser(userDB);

        projectAdministratorDao.create(pa);

        if (!userDB.getSystemRoles().contains(SystemRole.PROJECT_TEAM_MEMBER)) {
            userDB.getSystemRoles().add(SystemRole.PROJECT_TEAM_MEMBER);
        }

        userDao.update(userDB);
        return true;
    }

    @Transactional(readOnly = true)
    public List<Project> allOrderedBy(String orderByParam, boolean desc) {
        return projectDao.allOrderedBy(orderByParam, desc);
    }


    public List<Project> getMyProjectsSorted(Long userId, String orderByParam, boolean desc) {
        if (userId == null) {
            logger.debug("userId is null");
            return null;
        }

        User userDB = userDao.get(userId);
        if (userDB == null) {
            logger.debug("UserDB can´t be null");
            return null;
        }

        return projectDao.getMyProjectsSorted(userDB, orderByParam, desc);
    }

    public List<Project> getProjectsBySample(Long sampleId, String orderByParam, boolean desc) {
        if (sampleId == null) {
            logger.debug("sampleId is null");
            return null;
        }

        Sample sampleDB = sampleDao.get(sampleId);
        if (sampleDB == null) {
            logger.debug("SampleDB can´t be null");
            return null;
        }

        return projectDao.getProjectsBySample(sampleDB, orderByParam, desc);
    }

    public boolean approveProject(Long projectId, Long loggedUserId, ValidationErrors errors) {
        notNull(projectId);
        notNull(loggedUserId);

        if (!approve(projectId, loggedUserId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.ApproveFailed"));
            return false;
        }
        // Project can't be null - otherwise projectService.approve would have failed
        Project project = get(projectId);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.changedState",
                project.getName(), ProjectState.APPROVED);

        notificationDao.create(getProjectAdministratorsUsers(projectId),
                NotificationType.PROJECT_DETAIL, locMsg, project.getId());

        return true;
    }

    public boolean denyProject(Long projectId, Long loggedUserId, ValidationErrors errors) {
        notNull(projectId);
        notNull(loggedUserId);

        if (!deny(projectId, loggedUserId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.DenyFailed"));
            return false;
        }

        Project project = get(projectId);
        if (project != null) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.changedState",
                    project.getName(), ProjectState.DENIED);

            notificationDao.create(getProjectAdministratorsUsers(projectId),
                    NotificationType.PROJECT_DETAIL, locMsg, project.getId());
        }

        return true;
    }

    public Project createProject(Project project,
                                 Long loggedUserId,
                                 ValidationErrors errors) {
        notNull(project);
        notNull(loggedUserId);
        notNull(errors);

        project = create(project, loggedUserId);

        if (project != null) {

            // If this is the first created instance of Project and Biobank than create cz.bbmri general folder
            if (!createFolderStructure(project, errors)) {
                remove(project.getId());
                return null;
            }
        }

        return project;

    }

    public boolean updateProject(Project project, Long loggedUserId) {
        notNull(project);
        notNull(loggedUserId);

        if (update(project) == null) {
            return false;
        }

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.projectUpdated", project.getName());

        notificationDao.create(getOtherProjectWorkers(project, loggedUserId),
                NotificationType.PROJECT_DETAIL, locMsg, project.getId());

        return true;
    }

    public boolean removeProject(Long projectId, ValidationErrors errors, Long loggedUserId) {
        notNull(projectId);
        notNull(errors);
        notNull(loggedUserId);

        Project project = get(projectId);

        // Necessary to be able to send notification to all project members after delete

        List<User> users = getOtherProjectWorkers(project, loggedUserId);

        if (!remove(projectId)) {
            return false;
        }

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.projectRemoved",
                project.getName());

        notificationDao.create(users, NotificationType.PROJECT_DELETE, locMsg, project.getId());

        boolean result = ServiceUtils.recursiveDeleteFolder(
                storagePath +
                project.getProjectFolderPath()
                , errors) == Constant.SUCCESS;

        return result;

    }

    public boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors, Long loggedUserId) {
        notNull(objectId);
        notNull(newAdministratorId);
        notNull(permission);
        notNull(errors);
        notNull(loggedUserId);

        Project projectDB = get(objectId);
        User newAdmin = userDao.get(newAdministratorId);

        if (projectDB == null || newAdmin == null) {
            return false;
        }

        // TODO: kontrola zda uz novyAdministrator neni k projektu prirazen

        if (projectAdministratorDao.get(projectDao.get(objectId), userDao.get(newAdministratorId)) != null) {
            //TODO: exception - he is already admin
            return false;
        }

        boolean result = assignAdministrator(projectDB, newAdministratorId, permission);

        User userDB = userDao.get(newAdministratorId);

        if (result) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.assignedAdministrator",
                    projectDB.getName(), userDB.getWholeName(), permission);

            notificationDao.create(getOtherProjectWorkers(projectDB, loggedUserId),
                    NotificationType.PROJECT_ADMINISTRATOR, locMsg, projectDB.getId());
        }

        return result;

    }


    public boolean hasPermission(Permission permission, Long objectId, Long userId) {
        notNull(permission);
        notNull(objectId);
        notNull(userId);

        ProjectAdministrator pa = projectAdministratorDao.get(projectDao.get(objectId), userDao.get(userId));

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

        ProjectAdministrator pa = projectAdministratorDao.get(objectAdministrator);
        if (pa == null) {
            return false;
            // TODO: exception
        }

        // TODO: There must solved situation of last administrator remove

        pa.setPermission(permission);
        projectAdministratorDao.update(pa);
        Project project = pa.getProject();

        User user = pa.getUser();

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.permissionChanged",
                project.getName(), user.getWholeName(), permission);

        notificationDao.create(getOtherProjectWorkers(project, loggedUserId),
                NotificationType.PROJECT_ADMINISTRATOR, locMsg, project.getId());
        return true;
    }

    public boolean removeAdministrator(Long objectAdministrator, ValidationErrors errors, Long loggedUserId) {
        notNull(objectAdministrator);
        notNull(errors);
        notNull(loggedUserId);

        ProjectAdministrator pa = projectAdministratorDao.get(objectAdministrator);
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
        //        notificationDao.create(getOtherProjectWorkers(project, loggedUserId),
        //                NotificationType.PROJECT_REMOVE_ADMINISTRATOR, pa.getUser().getWholeName(), project.getId());

        return true;
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

        return getAllByUserAndProjectState(projectState, userId);
    }

    public boolean markAsFinished(Long projectId, Long loggedUserId) {
        notNull(projectId);
        notNull(loggedUserId);

        boolean result = changeState(projectId, ProjectState.FINISHED) != null;
        if (result) {

            Project project = get(projectId);

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.changedState",
                    project.getName(), ProjectState.FINISHED);

            notificationDao.create(getOtherProjectWorkers(project, loggedUserId),
                    NotificationType.PROJECT_DETAIL, locMsg, project.getId());
        }

        return result;
    }

    public ProjectAdministrator getProjectAdministrator(Long projectAdministratorId) {
        notNull(projectAdministratorId);
        return projectAdministratorDao.get(projectAdministratorId);
    }

}
