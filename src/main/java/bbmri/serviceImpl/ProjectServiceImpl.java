package bbmri.serviceImpl;

import bbmri.dao.*;
import bbmri.entities.*;
import bbmri.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
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
    private BiobankDao biobankDao;

    @Autowired
    private ProjectAdministratorDao projectAdministratorDao;

    public Project create(Project project, Long userId) {
        notNull(project);
        notNull(userId);

        User userDB = userDao.get(userId);
        if (userDB != null) {
            project.setProjectState(ProjectState.NEW);
            projectDao.create(project);
            assignUserToProject(userDB, project, Permission.MANAGER);
            projectDao.update(project);
        }
        return project;
    }

    public void remove(Long id) {
        notNull(id);

        Project projectDB = projectDao.get(id);
        if (projectDB != null) {

            User judge = projectDB.getJudgedByUser();
            if (judge != null) {
                judge.getJudgedProjects().remove(projectDB);
                userDao.update(judge);
            }   //  projectDB.setJudgedByUser();

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

            List<ProjectAdministrator> projectAdministrators = projectDB.getProjectAdministrators();
            if (projectAdministrators != null) {
                for (ProjectAdministrator pa : projectAdministrators) {
                    pa.setUser(null);
                    pa.setProject(null);
                    projectAdministratorDao.remove(pa);
                }
            }

            projectDao.remove(projectDB);
        }
    }

    public Project update(Project project) {
        notNull(project);

        Project projectDB = projectDao.get(project.getId());
        if (projectDB == null) {
            // TODO: exception
            return null;
        }

        /* This method should not be used to approve or deny project. It should be used as a way to edit project by its administrators. So it can't be changed to DENY, NEW or APPROVED*/
        if (projectDB.getProjectState() != ProjectState.NEW
                && projectDB.getProjectState() != ProjectState.DENIED
                && (project.getProjectState() == ProjectState.STARTED
                || project.getProjectState() == ProjectState.FINISHED
                || project.getProjectState() == ProjectState.CANCELED)) {
            projectDB.setProjectState(project.getProjectState());
        }
        if (project.getAnnotation() != null) {
            projectDB.setAnnotation(project.getAnnotation());
        }

        if (project.getName() != null) {
            projectDB.setName(project.getName());
        }
     /*
        I am not sure if these attributes can be changed during project lifetime.

        if (project.getApprovalDate() != null) {
            projectDB.setApprovalDate(project.getApprovalDate());
        }

        if (project.getApprovalStorage() != null) {
            projectDB.setApprovalStorage(project.getApprovalStorage());
        }

        if (project.getApprovedBy() != null) {
            projectDB.setApprovedBy(project.getApprovedBy());
        }

        if (project.getFundingOrganization() != null) {
            projectDB.setFundingOrganization(project.getFundingOrganization());
        }

        if (project.getHomeInstitution() != null) {
            projectDB.setHomeInstitution(project.getHomeInstitution());
        }

        if (project.getMainInvestigator() != null) {
            projectDB.setMainInvestigator(project.getMainInvestigator());
        }
        */

        projectDao.update(projectDB);
        return project;
    }

    public List<Project> all() {
        return projectDao.all();
    }



    public List<Project> getEagerByUserWithRequests(Long userId) {
        notNull(userId);
        User userDB = userDao.get(userId);
        List<ProjectAdministrator> paList = userDB.getProjectAdministrators();
        List<Project> projects = new ArrayList<Project>();
        for(ProjectAdministrator pa : paList){
            projects.add(pa.getProject());
        }

        return projects;
    }

    public List<Project> getAllByProjectState(ProjectState projectState) {
        notNull(projectState);
        return projectDao.getAllByProjectState(projectState);
    }

    public void approve(Long projectId, Long userId) {
        notNull(projectId);
        notNull(userId);

        Project projectDB = projectDao.get(projectId);

        if (projectDB == null) {
            return;
            //TODO: exception
        }
        User userDB = userDao.get(userId);
        if (userDB == null) {
            return;
            //TODO: exception
        }

        if (projectDB.getProjectState() == ProjectState.NEW) {
            projectDB.setProjectState(ProjectState.APPROVED);
            projectDB.setJudgedByUser(userDB);
            projectDao.update(projectDB);
            userDB.getJudgedProjects().add(projectDB);
            userDao.update(userDB);
        }
        //TODO: exception
    }

    public void deny(Long projectId, Long userId) {
        notNull(projectId);
        notNull(userId);

        Project projectDB = projectDao.get(projectId);

        if (projectDB == null) {
            return;
            //TODO: exception
        }
        User userDB = userDao.get(userId);
        if (userDB == null) {
            return;
            //TODO: exception
        }

        if (projectDB.getProjectState() == ProjectState.NEW) {
            projectDB.setProjectState(ProjectState.DENIED);
            projectDB.setJudgedByUser(userDB);
            projectDao.update(projectDB);
            userDB.getJudgedProjects().add(projectDB);
            userDao.update(userDB);
        }
        //TODO: exception
    }

    public Project get(Long id) {
        notNull(id);
        return projectDao.get(id);
    }

    public Integer count() {
        return projectDao.count();
    }

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

    public void assignUserToProject(User userDB, Project projectDB, Permission permission) {
        notNull(userDB);
        notNull(projectDB);
        notNull(permission);

        ProjectAdministrator pa = new ProjectAdministrator();
        pa.setPermission(permission);
        pa.setProject(projectDB);
        pa.setUser(userDB);

        projectAdministratorDao.create(pa);

        projectDB.getProjectAdministrators().add(pa);
        projectDao.update(projectDB);

        userDB.getProjectAdministrators().add(pa);
        userDao.update(userDB);
    }

    public void removeUserFromProject(User userDB, Project projectDB) {
        notNull(userDB);
        notNull(projectDB);

        for (ProjectAdministrator pa : userDB.getProjectAdministrators()) {
            if (pa.getProject().equals(projectDB)) {
                pa.setUser(null);
                pa.setProject(null);
                projectAdministratorDao.remove(pa);
            }
        }
    }

    public void removeAdministrator(Long projectId, Long loggedUserId, Long userId){
        notNull(userId);
        notNull(projectId);
        notNull(loggedUserId);

        User userDB = userDao.get(userId);
        User loggedUser = userDao.get(loggedUserId);
        Project projectDB = projectDao.get(projectId);


        if(userDB == null  || loggedUser == null || projectDB == null){
            return;
            // TODO: exception
        }
        ProjectAdministrator pa = projectAdministratorDao.get(projectDB, userDB);

        if(pa == null){
                   return;
        // TODO: exception - You can't remove user which is not administrator
        }

        ProjectAdministrator loggedPa = projectAdministratorDao.get(projectDB, loggedUser);

        if(loggedPa == null){
            return;
            // TODO: exception - You are not administrator of this project
        }

        if(!loggedPa.getPermission().equals(Permission.MANAGER)){
            return;
            // TODO: exception - You don't have sufficient rights
        }

        if(userDB.equals(loggedUser) && projectManagerCount(projectDB) == 1){
            return;
            // TODO: exception - You are last admin so you can't lower your permissions
        }

        removeUserFromProject(userDB, projectDB);
    }

    public void changeAdministratorPermission(Long projectId, Long loggedUserId, Long userId, Permission permission){
            notNull(userId);
            notNull(projectId);
            notNull(loggedUserId);
            notNull(permission);

            User userDB = userDao.get(userId);
            User loggedUser = userDao.get(loggedUserId);
            Project projectDB = projectDao.get(projectId);


            if(userDB == null  || loggedUser == null || projectDB == null){
                return;
                // TODO: exception
            }

            ProjectAdministrator loggedPa = projectAdministratorDao.get(projectDB, loggedUser);

            if(loggedPa == null){
                return;
                // TODO: exception - You are not administrator of this project
            }

            if(!loggedPa.getPermission().equals(Permission.MANAGER)){
                return;
                // TODO: exception - You don't have sufficient rights
            }

            if(userDB.equals(loggedUser) && projectManagerCount(projectDB) == 1){
                return;
                // TODO: exception - You are last admin so you can't lower your permissions
            }

        ProjectAdministrator pa = projectAdministratorDao.get(projectDB, userDB);

        if(pa == null){
            assignUserToProject(userDB, projectDB, permission);
        }else{
            pa.setPermission(permission);
            projectAdministratorDao.update(pa);
        }

    }


    private int projectManagerCount(Project project){
            notNull(project);
            int count = 0;
            for(ProjectAdministrator pa : project.getProjectAdministrators()){
                if(pa.getPermission().equals(Permission.MANAGER)){
                    count++;
                }
            }
            return count;
        }
}
