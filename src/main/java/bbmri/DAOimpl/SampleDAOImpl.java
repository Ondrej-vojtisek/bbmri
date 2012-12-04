package bbmri.DAOimpl;

import bbmri.DAO.SampleDAO;
import bbmri.entities.Sample;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:17
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class SampleDAOImpl implements SampleDAO {

    public void create(Sample sample, EntityManager em) {
        em.persist(sample);
    }

    public void remove(Sample sample, EntityManager em) {
        em.remove(sample);
    }

    public void update(Sample sample, EntityManager em) {
        em.merge(sample);
    }

    public Sample get(Long id, EntityManager em) {
        return em.find(Sample.class, id);
    }

    public List<Sample> getAll(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Sample p");
        return query.getResultList();
    }

    public List<Sample> getSelected(EntityManager em, String query) {

        String preparedQuery = "SELECT p FROM Sample p " + query;
        System.out.println(preparedQuery);
        Query queryDB = em.createQuery(preparedQuery);
        return queryDB.getResultList();
    }

}
