package bbmri.DAOimpl;

import bbmri.DAO.SampleDAO;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.entities.User;
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
public class SampleDAOImpl extends DAOImpl<Sample> implements SampleDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Sample> getSelected(String query) {
        DAOUtils.notNull(query);
        String preparedQuery = "SELECT p FROM Sample p " + query;
        Query queryDB = em.createQuery(preparedQuery);
        return queryDB.getResultList();
    }
}
