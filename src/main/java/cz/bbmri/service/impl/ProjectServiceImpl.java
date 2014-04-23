package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.ProjectService;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
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

    public boolean create(Project project, ValidationErrors errors) {
        notNull(errors);

        if (isNull(project, "project", errors)) return false;

        project.setProjectState(ProjectState.NEW);
        project.setCreated(new Date());
        projectDao.create(project);

        int result = ServiceUtils.createFolders(errors,
                storagePath, // base folder
                storagePath + Project.PROJECT_FOLDER, // Projects folder
                storagePath + project.getProjectFolderPath() // Folder for the project
        );
        if (result != Constant.SUCCESS) {
            projectDao.remove(project);
            return false;
        }

        return true;
    }

    public boolean remove(Long projectId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(projectId, "projectId", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        Project project = get(projectId);
        if (isNull(project, "project", errors)) return false;

        // Necessary to be able to send notification to all project members after delete
        List<User> users = getOtherProjectWorkers(project, loggedUserId);

        if (project.getJudgedByUser() != null) {
            project.setJudgedByUser(null);
        }

        if (project.getSampleRequests() != null) {
            for (SampleRequest sampleRequest : project.getSampleRequests()) {
                sampleQuestionDao.remove(sampleRequest);
            }
        }

        if (project.getAttachments() != null) {
            for (Attachment attachment : project.getAttachments()) {
                attachmentDao.remove(attachment);
            }
        }

        if (project.getProjectAdministrators() != null) {
            for (ProjectAdministrator pa : project.getProjectAdministrators()) {

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
        }

        projectDao.remove(project);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.projectRemoved",
                project.getName());

        notificationDao.create(users, NotificationType.PROJECT_DELETE, locMsg, project.getId());

        return ServiceUtils.recursiveDeleteFolder(
                storagePath +
                        project.getProjectFolderPath()
                , errors) == Constant.SUCCESS;

    }

    public boolean update(Project project, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(project, "project", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        Project projectDB = projectDao.get(project.getId());
        if (isNull(projectDB, "projectDB", errors)) return false;

        if (project.getAnnotation() != null) {
            projectDB.setAnnotation(project.getAnnotation());
        }

        if (project.getName() != null) {
            projectDB.setName(project.getName());
        }

        if (project.getPrincipalInvestigator() != null) {
            projectDB.setPrincipalInvestigator(project.getPrincipalInvestigator());
        }

        projectDao.update(projectDB);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.projectUpdated", project.getName());

        notificationDao.create(getOtherProjectWorkers(project, loggedUserId),
                NotificationType.PROJECT_DETAIL, locMsg, project.getId());

        return true;
    }

    @Transactional(readOnly = true)
    public List<Project> all() {
        return projectDao.all();
    }

    public boolean approve(Long projectId, Long loggedUserId, ValidationErrors errors) {
        notNull(errors);

        if (isNull(projectId, "projectId", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        Project projectDB = projectDao.get(projectId);
        if (isNull(projectDB, "projectDB", errors)) return false;

        if (!projectDB.getProjectState().equals(ProjectState.NEW)) {
            // TODO info pro uzivatele
            logger.debug("Project is not NEW so it can't be approved");
            return false;
        }

        User userDB = userDao.get(loggedUserId);
        if (isNull(userDB, "userDB", errors)) return false;

        projectDB.setProjectState(ProjectState.APPROVED);
        projectDB.setJudgedByUser(userDB);
        try {
            projectDao.update(projectDB);
        } catch (Exception ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.ApproveFailed"));
            return false;
        }

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.changedState",
                projectDB.getName(), ProjectState.APPROVED);

        notificationDao.create(getProjectAdministratorsUsers(projectId),
                NotificationType.PROJECT_DETAIL, locMsg, projectDB.getId());

        return true;
    }

    public boolean deny(Long projectId, Long loggedUserId, ValidationErrors errors) {
        notNull(errors);

        if (isNull(projectId, "projectId", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        Project projectDB = projectDao.get(projectId);
        if (isNull(projectDB, "projectDB", errors)) return false;

        if (!projectDB.getProjectState().equals(ProjectState.NEW)) {
            // TODO: note for user
            return false;
        }

        User userDB = userDao.get(loggedUserId);
        if (isNull(userDB, "userDB", errors)) return false;

        projectDB.setProjectState(ProjectState.DENIED);
        projectDB.setJudgedByUser(userDB);
        try {
            projectDao.update(projectDB);
        } catch (Exception ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.DenyFailed"));
            return false;
        }

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.changedState",
                projectDB.getName(), ProjectState.DENIED);

        notificationDao.create(getProjectAdministratorsUsers(projectId),
                NotificationType.PROJECT_DETAIL, locMsg, projectDB.getId());

        return true;
    }

    @Transactional(readOnly = true)
    public Project get(Long id) {
        if (isNull(id, "id", null)) return null;
        return projectDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Project> allOrderedBy(String orderByParam, boolean desc) {
        if (isNull(orderByParam, "orderByParam", null)) return null;
        if (isNull(desc, "desc", null)) return null;

        return projectDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Project> getProjectsSortedByUser(Long userId, String orderByParam, boolean desc) {
        if (isNull(userId, "userId", null)) return null;
        if (isNull(orderByParam, "orderByParam", null)) return null;
        if (isNull(desc, "desc", null)) return null;

        User userDB = userDao.get(userId);
        if (isNull(userDB, "userDB", null)) return null;

        return projectDao.getMyProjectsSorted(userDB, orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Project> getProjectsBySample(Long sampleId, String orderByParam, boolean desc) {
        if (isNull(sampleId, "sampleId", null)) return null;
        if (isNull(orderByParam, "orderByParam", null)) return null;
        if (isNull(desc, "desc", null)) return null;

        Sample sampleDB = sampleDao.get(sampleId);
        if (isNull(sampleDB, "sampleDB", null)) return null;

        return projectDao.getProjectsBySample(sampleDB, orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Project> allByProjectStateAndUser(Long userId, ProjectState projectState) {
        if (isNull(userId, "userId", null)) return null;
        if (isNull(projectState, "projectState", null)) return null;

        return projectDao.getAllByUserAndProjectState(userDao.get(userId), projectState);
    }

    public boolean finish(Long projectId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);
        if (isNull(projectId, "projectId", null)) return false;
        if (isNull(loggedUserId, "loggedUserId", null)) return false;

        Project projectDB = projectDao.get(projectId);
        if (isNull(projectDB, "projectDB", null)) return false;

        if (projectDB.getProjectState().equals(ProjectState.FINISHED)) {
            noEffect(errors);
            return false;
        }

        if (projectDB.getProjectState().equals(ProjectState.APPROVED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.onlyApprovedProjectMayBeFinished"));
            return false;
        }

        projectDB.setProjectState(ProjectState.FINISHED);
        projectDao.update(projectDB);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.changedState",
                projectDB.getName(), ProjectState.FINISHED);

        notificationDao.create(getOtherProjectWorkers(projectDB, loggedUserId),
                NotificationType.PROJECT_DETAIL, locMsg, projectDB.getId());

        return true;
    }

}
