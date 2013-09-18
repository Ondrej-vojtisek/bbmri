package bbmri.daoImpl;

import bbmri.dao.SampleQuestionDao;
import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.SampleQuestion;
import org.springframework.stereotype.Repository;

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
public class SampleQuestionDaoImpl extends BasicDaoImpl<SampleQuestion> implements SampleQuestionDao {

    public List<SampleQuestion> getSelected(String query) {
        notNull(query);
        String preparedQuery = "SELECT p FROM SampleQuestion p " + query;
        Query queryDB = em.createQuery(preparedQuery);
        return queryDB.getResultList();
    }

    public List<SampleQuestion> getByBiobank(Biobank biobank) {
        Query query = em.createQuery("SELECT p FROM SampleQuestion p where p.biobank = :param and " +
                "p.processed = false");
        query.setParameter("param", biobank);
        return query.getResultList();
    }

    public List<SampleQuestion> getByBiobankAndProcessed(Biobank biobank, boolean processed) {
        Query query = em.createQuery("SELECT p FROM SampleQuestion p where p.biobank = :bioParam and " +
                "p.processed = : boolParam");
        query.setParameter("bioParam", biobank);
        query.setParameter("boolParam", processed);
        return query.getResultList();
    }

    public List<SampleQuestion> getByProject(Project project) {
        Query query = em.createQuery("SELECT p FROM SampleQuestion p where p.project = :param");
        query.setParameter("param", project);
        return query.getResultList();
    }

}
