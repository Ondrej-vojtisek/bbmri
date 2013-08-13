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
public class ProjectDAOImpl extends DAOImpl<Project> implements ProjectDAO {

    public List<Project> getAllByProjectState(ProjectState projectState) {
        notNull(projectState);
        Query query = em.createQuery("SELECT p FROM Project p where p.projectState=projectState");
        return query.getResultList();
    }

    public List<Project> getAllByUser(User user) {
        notNull(user);
        return user.getProjects();
    }

    public List<User> getAllUsersByProject(Project project) {
        notNull(project);
        return project.getUsers();
    }
}
