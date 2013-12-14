package bbmri.service.impl;

import bbmri.dao.*;
import bbmri.entities.*;
import bbmri.entities.enumeration.Permission;
import bbmri.entities.enumeration.ProjectState;
import bbmri.entities.enumeration.SystemRole;
import bbmri.service.ProjectService;
import bbmri.service.exceptions.LastManagerException;
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
    private RequestGroupDao requestGroupDao;

    @Autowired
    private ProjectAdministratorDao projectAdministratorDao;

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

        List<RequestGroup> requestGroups = projectDB.getRequestGroups();
        if (requestGroups != null) {
            for (RequestGroup requestGroup : requestGroups) {
                requestGroupDao.remove(requestGroup);
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

        if(projectState.equals(ProjectState.APPROVED) ||
                projectState.equals(ProjectState.DENIED)){
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

        if (project.getMainInvestigator() != null) {
            projectDB.setMainInvestigator(project.getMainInvestigator());
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
        List<ProjectAdministrator> paList = userDB.getProjectAdministrators();
        List<Project> projects = new ArrayList<Project>();
        for (ProjectAdministrator pa : paList) {
            projects.add(pa.getProject());
        }

        return projects;
    }

    @Transactional(readOnly = true)
    public List<Project> getAllByProjectState(ProjectState projectState) {
        notNull(projectState);

        return projectDao.getAllByProjectState(projectState);
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

    @Transactional(readOnly = true)
    public Project eagerGet(Long id, boolean users, boolean requestGroups, boolean attachments, boolean sampleQuestions) {
        notNull(id);
        Project projectDB = projectDao.get(id);

           /* Not only comments - this force hibernate to load mentioned relationship from db. Otherwise it wont be accessible from presentational layer of application.*/

        if (users) {
            logger.debug("" + projectDB.getProjectAdministrators());
        }

        if (requestGroups) {
            logger.debug("" + projectDB.getRequestGroups());
        }

        if (attachments) {
            logger.debug("" + projectDB.getAttachments());
        }

        if (sampleQuestions) {
            logger.debug("" + projectDB.getSampleQuestions());
        }
        return projectDB;

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

}
