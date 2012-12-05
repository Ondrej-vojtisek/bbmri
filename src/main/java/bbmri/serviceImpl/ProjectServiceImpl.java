package bbmri.serviceImpl;

import bbmri.DAO.ProjectDAO;
import bbmri.DAO.UserDAO;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import bbmri.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProjectDAO projectDAO;

    public Project create(Project project, User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project.setProjectState(ProjectState.NEW);
        projectDAO.create(project, em);
        User resDB = userDAO.get(user.getId(), em);
        projectDAO.assignUserToProject(resDB, project);
        em.getTransaction().commit();
        em.close();
        return project;
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project project = projectDAO.get(id, em);
        if (project != null) {
            projectDAO.remove(project, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Project update(Project project) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = projectDAO.get(project.getId(), em);

        if (projectDB.getProjectState() != ProjectState.NEW &&
                project.getProjectState() != ProjectState.NEW &&
                project.getProjectState() != ProjectState.APPROVED) {
            projectDB.setProjectState(project.getProjectState());
        }
        projectDB.setDescription(project.getDescription());
        projectDB.setFundingOrganization(project.getFundingOrganization());
        projectDB.setName(project.getName());

        projectDAO.update(projectDB, em);
        em.getTransaction().commit();
        em.close();
        return project;
    }

    public List<Project> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = projectDAO.getAll(em);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

    public List<Project> getAllByUser(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = userDAO.get(id, em);
        List<Project> projects = projectDAO.getAllByUser(userDB);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

    public List<Project> getAllWhichUserAdministrate(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = userDAO.get(id, em);
        List<Project> projects = projectDAO.getAllByUser(userDB);
        em.getTransaction().commit();
        em.close();

        List<Project> result = new ArrayList<Project>();
        if (projects != null) {
            for (Project project : projects) {
                if (project.getUsers().get(0).equals(userDB)) {
                    result.add(project);
                }
            }
        }
        return result;
    }


    public List<Project> getAllByProjectState(ProjectState projectState) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = projectDAO.getAllByProjectState(projectState, em);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

    public List<Project> getAllApprovedByUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = projectDAO.getAllByUser(user);
        em.close();
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


    public User assignUser(Long userId, Long projectId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = userDAO.get(userId, em);
        Project projectDB = projectDAO.get(projectId, em);
        if (projectDAO.projectContainsUser(userDB, projectDB) == true) {
            em.close();
            return null;
        }
        projectDAO.assignUserToProject(userDB, projectDB);
        em.getTransaction().commit();
        em.close();
        return userDB;
    }

    public User removeUserFromProject(Long userId, Long projectId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = userDAO.get(userId, em);
        Project projectDB = projectDAO.get(projectId, em);
        if (projectDAO.projectContainsUser(userDB, projectDB) == false) {
            em.close();
            return null;
        }
        projectDAO.removeUserFromProject(userDB, projectDB);
        em.getTransaction().commit();
        em.close();
        return userDB;
    }

    public List<User> getAllAssignedUsers(Long projectId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = projectDAO.get(projectId, em);
        List<User> users = projectDAO.getAllUsersByProject(projectDB);
        em.getTransaction().commit();
        em.close();
        return users;
    }

    public void approve(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = projectDAO.get(id, em);
        if (projectDB.getProjectState() == ProjectState.NEW) {
            projectDB.setProjectState(ProjectState.APPROVED);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Project getById(Long id) {
        Project project;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project = projectDAO.get(id, em);
        em.getTransaction().commit();
        em.close();
        return project;
    }

    public List<User> getAllNotAssignedUsers(Long id) {
        List<User> result = new ArrayList<User>();

        Project project;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project = projectDAO.get(id, em);
        List<User> allUsers = userDAO.getAll(em);
        em.getTransaction().commit();
        em.close();
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

        Project project;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project = projectDAO.get(projectId, em);
        User user = userDAO.get(newOwnerId, em);

        User old = userDAO.get(project.getOwner().getId(), em);

        if (project.getUsers().contains(user)) {
            project.getUsers().remove(old);
            projectDAO.update(project, em);
            project.getUsers().remove(user);
            project.getUsers().add(0, user);
            project.getUsers().add(old);
            projectDAO.update(project, em);
        }
        em.getTransaction().commit();
        em.close();
        return project;
    }
}
