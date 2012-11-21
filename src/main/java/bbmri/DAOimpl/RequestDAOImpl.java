package bbmri.DAOimpl;

import bbmri.DAO.RequestDAO;
import bbmri.entities.Request;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */
public class RequestDAOImpl implements RequestDAO {

     public void create(Request request, EntityManager em) {
        em.persist(request);
    }

    public void remove(Request request, EntityManager em) {
        em.remove(request);
    }

    public void update(Request request, EntityManager em) {
        em.merge(request);
    }

    public List<Request> getAll(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Request p");
        return query.getResultList();
    }

    public Request get(Long id, EntityManager em) {
        return em.find(Request.class, id);
    }

}
