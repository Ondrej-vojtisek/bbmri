package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.SampleQuestion;
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

    @SuppressWarnings("unchecked")
    public List<SampleQuestion> getByBiobankAndProcessed(Biobank biobank, boolean processed) {
        Query query = em.createQuery("SELECT p FROM SampleQuestion p where p.biobank = :bioParam and " +
                "p.processed = :boolParam");
        query.setParameter("bioParam", biobank);
        query.setParameter("boolParam", processed);
        return query.getResultList();
    }

}