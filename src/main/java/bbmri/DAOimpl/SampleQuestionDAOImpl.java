package bbmri.DAOimpl;

import bbmri.DAO.SampleQuestionDAO;
import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.SampleQuestion;
import bbmri.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class SampleQuestionDAOImpl extends DAOImpl<SampleQuestion> implements SampleQuestionDAO {

    public List<SampleQuestion> getSelected(String query) {
        notNull(query);
        String preparedQuery = "SELECT p FROM SampleQuestion p " + query;
        Query queryDB = em.createQuery(preparedQuery);
        return queryDB.getResultList();
    }

}
