package bbmri.DAOimpl;

import bbmri.DAO.ProjectDAO;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class ProjectDAOImpl implements ProjectDAO {

    public void create(Project project, EntityManager em) {
        em.persist(project);
    }

    public void remove(Project project, EntityManager em) {
        em.remove(project);
    }

    public void update(Project project, EntityManager em) {
        em.merge(project);
    }

    public List<Project> getAll(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Project p");
        return query.getResultList();
    }

    public List<Project> getAllByProjectState(ProjectState projectState, EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Project p where p.projectState=projectState");
        return query.getResultList();
    }

    public Project get(Long id, EntityManager em) {
        return em.find(Project.class, id);
    }

    public List<Project> getAllByUser(User user) {
        return user.getProjects();
    }

    public List<User> getAllUsersByProject(Project project) {
        return project.getUsers();
    }

    public void assignUserToProject(User user, Project project) {
        project.getUsers().add(user);
    }

    public void removeUserFromProject(User user, Project project) {
        project.getUsers().remove(user);
    }

    public boolean projectContainsUser(User user, Project project) {
        return project.getUsers().contains(user);
    }
}
