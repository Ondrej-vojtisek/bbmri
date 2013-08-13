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
        DAOUtils.notNull(project);
        em.persist(project);
    }

    public void remove(Project project) {
        DAOUtils.notNull(project);
        em.remove(project);
    }

    public void update(Project project) {
        DAOUtils.notNull(project);
        em.merge(project);
    }

    public List<Project> all() {
        Query query = em.createQuery("SELECT p FROM Project p");
        return query.getResultList();
    }

    public List<Project> getAllByProjectState(ProjectState projectState) {
        DAOUtils.notNull(projectState);
        Query query = em.createQuery("SELECT p FROM Project p where p.projectState=projectState");
        return query.getResultList();
    }

    public Project get(Long id) {
        DAOUtils.notNull(id);
        return em.find(Project.class, id);
    }

    public List<Project> getAllByUser(User user) {
        DAOUtils.notNull(user);
        return user.getProjects();
    }

    public List<User> getAllUsersByProject(Project project) {
        DAOUtils.notNull(project);
        return project.getUsers();
    }

    public Integer count() {
        Query query = em.createQuery("SELECT COUNT (p) FROM Project p");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}
