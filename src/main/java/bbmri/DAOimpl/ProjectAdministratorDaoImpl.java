package bbmri.daoImpl;

import bbmri.dao.ProjectAdministratorDao;
import bbmri.entities.Project;
import bbmri.entities.ProjectAdministrator;
import bbmri.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 27.9.13
 * Time: 9:43
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ProjectAdministratorDaoImpl extends BasicDaoImpl<ProjectAdministrator> implements ProjectAdministratorDao {

    public boolean contains(Project project, User user) {
        ProjectAdministrator pa = get(project, user);
        return pa != null;

    }

    public ProjectAdministrator get(Project project, User user) {
        Query query = em.createQuery("SELECT p FROM ProjectAdministrator p where " +
                "p.project = :projectParam " +
                "and p.user = :userParam ");
        query.setParameter("projectParam", project);
        query.setParameter("userParam", user);

        List<ProjectAdministrator> list = query.getResultList();
        if (list == null) {
            return null;
        }
        if (!list.isEmpty()) {
            return (ProjectAdministrator) query.getSingleResult();
        }
        return null;
    }

}
