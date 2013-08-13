package bbmri.DAOimpl;

import bbmri.DAO.SampleQuestionDAO;
import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.SampleQuestion;
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
public class SampleQuestionDAOImpl implements SampleQuestionDAO {

    @PersistenceContext
    private EntityManager em;

    public void create(SampleQuestion sampleQuestion){
        DAOUtils.notNull(sampleQuestion);
        em.persist(sampleQuestion);
    }

    public void remove(SampleQuestion sampleQuestion){
        DAOUtils.notNull(sampleQuestion);
        em.remove(sampleQuestion);
    }

    public void update(SampleQuestion sampleQuestion){
        DAOUtils.notNull(sampleQuestion);
        em.merge(sampleQuestion);
    }

    public List<SampleQuestion> all(){
        Query query = em.createQuery("SELECT p FROM SampleQuestion p");
        return query.getResultList();
    }

    public SampleQuestion get(Long id){
        DAOUtils.notNull(id);
         return em.find(SampleQuestion.class, id);
    }

    public Integer count(){
        Query query = em.createQuery("SELECT COUNT (p) FROM SampleQuestion p");
        return Integer.parseInt(query.getSingleResult().toString());
    }

    public List<SampleQuestion> getSelected(String query) {
        DAOUtils.notNull(query);
        String preparedQuery = "SELECT p FROM SampleQuestion p " + query;
        Query queryDB = em.createQuery(preparedQuery);
        return queryDB.getResultList();
    }

}
