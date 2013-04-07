package bbmri.DAOimpl;

import bbmri.DAO.RequestGroupDAO;
import bbmri.entities.Project;
import bbmri.entities.RequestGroup;
import bbmri.entities.SampleQuestion;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 6.2.13
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RequestGroupDAOImpl implements RequestGroupDAO {

    @PersistenceContext
    private EntityManager em;

    public void create(RequestGroup requestGroup) {
        DAOUtils.notNull(requestGroup);
        em.persist(requestGroup);
    }

    public void remove(RequestGroup requestGroup) {
        DAOUtils.notNull(requestGroup);
        em.remove(requestGroup);
    }

    public void update(RequestGroup requestGroup) {
        DAOUtils.notNull(requestGroup);
        em.merge(requestGroup);
    }

    public List<RequestGroup> getAll() {
        Query query = em.createQuery("SELECT p FROM RequestGroup p");
        return query.getResultList();
    }

    public RequestGroup get(Long id) {
        DAOUtils.notNull(id);
        return em.find(RequestGroup.class, id);
    }

    public Integer getCount() {
        Query query = em.createQuery("SELECT COUNT (p) FROM RequestGroup p");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    public List<RequestGroup> getAllByProject(Project project) {
         DAOUtils.notNull(project);
         Query query = em.createQuery("SELECT p FROM RequestGroup p WHERE p.project = project");
         return query.getResultList();
     }

    public List<SampleQuestion> getSelected(String query) {
        DAOUtils.notNull(query);
        String preparedQuery = "SELECT p FROM SampleQuestion p " + query;
        Query queryDB = em.createQuery(preparedQuery);
        return queryDB.getResultList();
    }
}
