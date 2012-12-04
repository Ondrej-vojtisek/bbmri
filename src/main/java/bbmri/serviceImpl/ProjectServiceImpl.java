package bbmri.serviceImpl;

import bbmri.DAO.ProjectDAO;
import bbmri.DAO.UserDAO;
import bbmri.DAOimpl.ProjectDAOImpl;
import bbmri.DAOimpl.UserDAOImpl;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import bbmri.service.ProjectService;

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
public class ProjectServiceImpl implements ProjectService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    UserDAO userDAO;
    ProjectDAO projectDAO;

    private UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }

    private ProjectDAO getProjectDAO() {
        if (projectDAO == null) {
            projectDAO = new ProjectDAOImpl();
        }
        return projectDAO;
    }

    public Project create(Project project, User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project.setProjectState(ProjectState.NEW);
        getProjectDAO().create(project, em);
        User resDB = getUserDAO().get(user.getId(), em);
        getProjectDAO().assignResearcherToProject(resDB, project);
        em.getTransaction().commit();
        em.close();
        return project;
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project project = getProjectDAO().get(id, em);
        if (project != null) {
            getProjectDAO().remove(project, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public Project update(Project project) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = getProjectDAO().get(project.getId(), em);

        if (projectDB.getProjectState() != ProjectState.NEW &&
                project.getProjectState() != ProjectState.NEW &&
                project.getProjectState() != ProjectState.APPROVED) {
            projectDB.setProjectState(project.getProjectState());
        }
        projectDB.setDescription(project.getDescription());
        projectDB.setFundingOrganization(project.getFundingOrganization());
        projectDB.setName(project.getName());

        getProjectDAO().update(projectDB, em);
        em.getTransaction().commit();
        em.close();
        return project;
    }

    public List<Project> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = getProjectDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

    public List<Project> getAllByResearcher(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = getUserDAO().get(id, em);
        List<Project> projects = getProjectDAO().getAllByResearcher(userDB);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

    public List<Project> getAllWhichResearcherAdministrate(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = getUserDAO().get(id, em);
        List<Project> projects = getProjectDAO().getAllByResearcher(userDB);
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
        List<Project> projects = getProjectDAO().getAllByProjectState(projectState, em);
        em.getTransaction().commit();
        em.close();
        return projects;
    }

    public List<Project> getAllApprovedByResearcher(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Project> projects = getProjectDAO().getAllByResearcher(user);
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


    public User assignResearcher(Long userId, Long projectId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = getUserDAO().get(userId, em);
        Project projectDB = getProjectDAO().get(projectId, em);
        if (getProjectDAO().projectContainsResearcher(userDB, projectDB) == true) {
            em.close();
            return null;
        }
        getProjectDAO().assignResearcherToProject(userDB, projectDB);
        em.getTransaction().commit();
        em.close();
        return userDB;
    }

    public User removeResearcherFromProject(Long userId, Long projectId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = getUserDAO().get(userId, em);
        Project projectDB = getProjectDAO().get(projectId, em);
        if (getProjectDAO().projectContainsResearcher(userDB, projectDB) == false) {
            em.close();
            return null;
        }
        getProjectDAO().removeResearcherFromProject(userDB, projectDB);
        em.getTransaction().commit();
        em.close();
        return userDB;
    }

    public List<User> getAllAssignedResearchers(Long projectId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = getProjectDAO().get(projectId, em);
        List<User> users = getProjectDAO().getAllResearchersByProject(projectDB);
        em.getTransaction().commit();
        em.close();
        return users;
    }

    public void approve(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Project projectDB = getProjectDAO().get(id, em);
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
        project = getProjectDAO().get(id, em);
        em.getTransaction().commit();
        em.close();
        return project;
    }

    public List<User> getAllNotAssignedResearchers(Long id) {
        List<User> result = new ArrayList<User>();

        Project project;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        project = getProjectDAO().get(id, em);
        List<User> allUsers = getUserDAO().getAll(em);
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
        project = getProjectDAO().get(projectId, em);
        User user = getUserDAO().get(newOwnerId, em);

        User old = getUserDAO().get(project.getOwner().getId(), em);

        if (project.getUsers().contains(user)) {
            project.getUsers().remove(old);
            getProjectDAO().update(project, em);
            project.getUsers().remove(user);
            project.getUsers().add(0, user);
            project.getUsers().add(old);
            getProjectDAO().update(project, em);
        }
        em.getTransaction().commit();
        em.close();
        return project;
    }
}
