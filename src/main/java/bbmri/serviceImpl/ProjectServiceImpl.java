package bbmri.serviceImpl;

import bbmri.dao.AttachmentDao;
import bbmri.dao.ProjectDao;
import bbmri.dao.RequestGroupDao;
import bbmri.dao.UserDao;
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

    public Project create(Project project, Long userId) {
        notNull(project);
        notNull(userId);

        User userDB = userDao.get(userId);
        if (userDB != null) {
            project.setProjectState(ProjectState.NEW);
            projectDao.create(project);
            assignUserToProject(userDB, project, true);
        }
        return project;
    }


    private void assignUserToProject(User userDB, Project projectDB, boolean beginning) {
        notNull(userDB);
        notNull(projectDB);

        userDB.getProjects().add(projectDB);
        userDao.update(userDB);

        if (beginning) {
            projectDB.getUsers().add(0, userDB);
        } else {
            projectDB.getUsers().add(userDB);
        }


        projectDao.update(projectDB);
    }

    private void removeUserFromProject(User userDB, Project projectDB) {
        notNull(userDB);
        notNull(projectDB);
        userDB.getProjects().remove(projectDB);
        userDao.update(userDB);
        projectDB.getUsers().remove(userDB);
        projectDao.update(projectDB);
    }

    public void remove(Long id) {
        notNull(id);

        Project projectDB = projectDao.get(id);
        if (projectDB != null) {
            List<User> users = projectDB.getUsers();
            if (users != null) {
                for (User user : users) {
                    user.getProjects().remove(projectDB);
                    userDao.update(user);
                }
            }

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
        List<Project> projects = projectDao.all();
        return projects;
    }

   /* public List<Project> getAllByUser(Long id) {
        User userDB = userDao.get(id);
        //  List<Project> projects = projectDao.getAllByUser(userDB);
        return userDB.getProjects();
    }  */

    /* TODO: Tohle neni pekne a jiste to pujde lepe. Jen je potreba prvne projit upravu modelu u RequestGroup a Request  */
    public List<Project> getAllByUserWithRequests(Long userId) {
        notNull(userId);

        User userDB = userDao.get(userId);
        //   List<Project> projects = projectDao.getAllByUser(userDB);
        List<Project> projects = userDB.getProjects();
        if (projects != null) {
            for (int i = 0; i < projects.size(); i++) {
                projects.get(i).setRequestGroups(requestGroupDao.getAllByProject(projects.get(i)));
            }
        }
        return projects;
    }

    public List<Project> getAllWhichUserAdministrate(Long userId) {
        notNull(userId);
        User userDB = userDao.get(userId);
        if (userDB == null) {
            return null;
            // TODO: exception
        }
        List<Project> projects = userDB.getProjects();

        List<Project> result = new ArrayList<Project>();
        if (projects != null) {
            for (Project project : projects) {
                if (project.getOwner().equals(userDB)) {
                    result.add(project);
                }
            }
        }
        return result;
    }


    public List<Project> getAllByProjectState(ProjectState projectState) {
        notNull(projectState);
        List<Project> projects = projectDao.getAllByProjectState(projectState);
        return projects;
    }
    /*
    public List<Project> getAllApprovedByUser(Long userId) {
        User userDB = userDao.get(userId);

        List<Project> projects = userDB.getProjects();

        List<Project> result = new ArrayList<Project>();
        if (projects != null) {
            for (Project project : projects) {
                if (project.getProjectState() == ProjectState.APPROVED ||
                        project.getProjectState() == ProjectState.STARTED) {
                    result.add(project);
                }
            }
        }
        return result;
    }
    */


    public User assignUser(Long userId, Long projectId) {
        notNull(userId);
        notNull(projectId);

        User userDB = userDao.get(userId);
        if (userDB == null) {
            return null;
            //TODO: exception
        }
        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            return null;
            //TODO: exception
        }
        if (!projectDB.getUsers().contains(userDB)) {
            assignUserToProject(userDB, projectDB, false);
        }
        return userDB;
    }

    public User removeUserFromProject(Long userId, Long projectId) {
        notNull(userId);
        notNull(projectId);

        User userDB = userDao.get(userId);
        if (userDB == null) {
            return null;
            //TODO: exception
        }
        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            return null;
            //TODO: exception
        }
        if (projectDB.getUsers().contains(userDB)) {
            removeUserFromProject(userDB, projectDB);
        }
        return userDB;
    }

  /*  public List<User> getAllAssignedUsers(Long projectId) {
        Project projectDB = projectDao.get(projectId);
        // List<User> users = projectDao.getAllUsersByProject(projectDB);
        return projectDB.getUsers();
    }  */

  /*  public void approve(Long id) {
        Project projectDB = projectDao.get(id);
        if (projectDB.getProjectState() == ProjectState.NEW) {
            projectDB.setProjectState(ProjectState.APPROVED);
            projectDao.update(projectDB);
        }

    } */

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

    public List<User> getAllNotAssignedUsers(Long projectId) {
        notNull(projectId);
        List<User> result = new ArrayList<User>();
        Project project = projectDao.get(projectId);
        List<User> allUsers = userDao.all();
        if (allUsers != null) {
            for (User user : allUsers) {
                if (!user.getProjects().contains(project)) {
                    result.add(user);
                }
            }
        }
        return result;
    }

    public Project changeOwnership(Long projectId, Long newOwnerId) {
        notNull(projectId);
        notNull(newOwnerId);

        Project projectDB = projectDao.get(projectId);

        if (projectDB == null) {
            return null;
            //TODO exception
        }

        User newOwner = userDao.get(newOwnerId);

        if (newOwner == null) {
            return null;
            //TODO exception
        }


        User oldOwner = userDao.get(projectDB.getOwner().getId());

        if (oldOwner == null) {
            return null;
            //TODO exception
        }

        if (projectDB.getUsers().contains(newOwner)) {
            //   removeUserFromProject(oldOwner, projectDB);
            assignUserToProject(newOwner, projectDB, true);
            //   assignUserToProject(oldOwner, projectDB, false);
        }
        return projectDB;
    }

    public Integer count() {
        return projectDao.count();
    }

}
