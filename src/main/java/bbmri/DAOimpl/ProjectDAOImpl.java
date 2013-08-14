package bbmri.daoImpl;

import bbmri.dao.ProjectDao;
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
public class ProjectDaoImpl extends BasicDaoImpl<Project> implements ProjectDao {

    public List<Project> getAllByProjectState(ProjectState projectState) {
        notNull(projectState);
        Query query = em.createQuery("SELECT p FROM Project p where p.projectState = :param");
        query.setParameter("param", projectState);
        return query.getResultList();
    }
}
