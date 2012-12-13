package bbmri.DAOimpl;

import bbmri.DAO.ProjectDAO;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

    @PersistenceContext
    private EntityManager em;

    public void create(Project project) {
        em.persist(project);
    }

    public void remove(Project project) {
        em.remove(project);
    }

    public void update(Project project) {
        em.merge(project);
    }

    public List<Project> getAll() {
        Query query = em.createQuery("SELECT p FROM Project p");
        return query.getResultList();
    }

    public List<Project> getAllByProjectState(ProjectState projectState) {
        Query query = em.createQuery("SELECT p FROM Project p where p.projectState=projectState");
        return query.getResultList();
    }

    public Project get(Long id) {
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

    public Integer getCount(){
        Query query = em.createQuery("SELECT COUNT (p) FROM Project p");
        return Integer.parseInt(query.getSingleResult().toString());
    }
}
