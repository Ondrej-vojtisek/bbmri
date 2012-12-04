package bbmri.DAOimpl;

import bbmri.DAO.BiobankDAO;
import bbmri.entities.Biobank;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
public class BiobankDAOImpl implements BiobankDAO {

    public void create(Biobank biobank, EntityManager em) {
        em.persist(biobank);
    }

    public void remove(Biobank biobank, EntityManager em) {
        em.remove(biobank);
    }

    public void update(Biobank biobank, EntityManager em) {
        em.merge(biobank);
    }

    public List<Biobank> getAll(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM Biobank p");
        return query.getResultList();
    }

    public Biobank get(Long id, EntityManager em) {
        return em.find(Biobank.class, id);
    }
}
