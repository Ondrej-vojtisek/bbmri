package bbmri.DAOimpl;

import bbmri.DAO.BiobankDAO;
import bbmri.entities.Biobank;
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
public class BiobankDAOImpl implements BiobankDAO {

    @PersistenceContext
    private EntityManager em;

    public void create(Biobank biobank) {
        DAOUtils.notNull(biobank);
        em.persist(biobank);
    }

    public void remove(Biobank biobank) {
        DAOUtils.notNull(biobank);
        em.remove(biobank);
    }

    public void update(Biobank biobank) {
        DAOUtils.notNull(biobank);
        em.merge(biobank);
    }

    public List<Biobank> getAll() {
        Query query = em.createQuery("SELECT p FROM Biobank p");
        return query.getResultList();
    }

    public Biobank get(Long id) {
        DAOUtils.notNull(id);
        return em.find(Biobank.class, id);
    }

    public Integer getCount() {
        Query query = em.createQuery("SELECT COUNT (p) FROM Biobank p");
        return Integer.parseInt(query.getSingleResult().toString());
    }
}
