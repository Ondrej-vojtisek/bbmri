package bbmri.DAOimpl;

import bbmri.DAO.RequestDAO;
import bbmri.entities.Request;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class RequestDAOImpl implements RequestDAO {

        @PersistenceContext
    private EntityManager em;

    public void create(Request request) {
        em.persist(request);
    }

    public void remove(Request request) {
        em.remove(request);
    }

    public void update(Request request) {
        em.merge(request);
    }

    public List<Request> getAll() {
        Query query = em.createQuery("SELECT p FROM Request p");
        return query.getResultList();
    }

    public Request get(Long id) {
        return em.find(Request.class, id);
    }

    public Integer getCount(){
        Query query = em.createQuery("SELECT COUNT (p) FROM Request p");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}
