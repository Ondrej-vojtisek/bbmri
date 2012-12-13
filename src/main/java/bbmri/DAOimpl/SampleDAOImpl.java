package bbmri.DAOimpl;

import bbmri.DAO.SampleDAO;
import bbmri.entities.Sample;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager em;

    public void create(Sample sample) {
        em.persist(sample);
    }

    public void remove(Sample sample) {
        em.remove(sample);
    }

    public void update(Sample sample) {
        em.merge(sample);
    }

    public Sample get(Long id) {
        return em.find(Sample.class, id);
    }

    public List<Sample> getAll() {
        Query query = em.createQuery("SELECT p FROM Sample p");
        return query.getResultList();
    }

    public List<Sample> getSelected(String query) {

        String preparedQuery = "SELECT p FROM Sample p " + query;
        System.out.println(preparedQuery);
        Query queryDB = em.createQuery(preparedQuery);
        return queryDB.getResultList();
    }

    public Integer getCount(){
        Query query = em.createQuery("SELECT COUNT (p) FROM Sample p");
        return Integer.parseInt(query.getSingleResult().toString());
    }

}
