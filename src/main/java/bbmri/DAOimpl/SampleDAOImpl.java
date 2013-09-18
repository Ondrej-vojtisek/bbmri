package bbmri.daoImpl;

import bbmri.dao.SampleDao;
import bbmri.entities.Sample;
import org.springframework.stereotype.Repository;

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
public class SampleDaoImpl extends BasicDaoImpl<Sample> implements SampleDao {


    public List<Sample> getSelected(Sample question) {
        notNull(question);
        Query query = null;

        logger.debug("Get selected" );

        if (question.getBiobank() == null && question.getGrading() == null) {
            logger.debug("Biobank null grading null" );
            query = em.createQuery("SELECT p FROM Sample p where " +
                    "p.diagnosis like :diagnosisParam " +
                    "and p.morphology like :morphologyParam " +
                    "and p.pTNM like :pTNMParam " +
                    "and p.tissueType like :tissueTypeParam " +
                    "and p.TNM like :TNMParam ");
        }
        if (question.getBiobank() == null && question.getGrading() != null) {
            logger.debug("Biobank null grading not null" );
            query = em.createQuery("SELECT p FROM Sample p where " +
                    "p.diagnosis like :diagnosisParam " +
                    "and p.morphology like :morphologyParam " +
                    "and p.pTNM like :pTNMParam " +
                    "and p.tissueType like :tissueTypeParam " +
                    "and p.TNM like :TNMParam " +
                    "and p.grading = :gradingParam ");
        }

        if (question.getBiobank() != null && question.getGrading() == null) {
            logger.debug("Biobank not null grading null" );
            query = em.createQuery("SELECT p FROM Sample p where " +
                    "p.diagnosis like :diagnosisParam " +
                    "and p.biobank = :biobankParam " +
                    "and p.morphology like :morphologyParam " +
                    "and p.pTNM like :pTNMParam " +
                    "and p.tissueType like :tissueTypeParam " +
                    "and p.TNM like :TNMParam ");
        }

        if (question.getBiobank() != null && question.getGrading() != null) {
            logger.debug("Biobank not null grading not null" );
            query = em.createQuery("SELECT p FROM Sample p where " +
                    "p.diagnosis like :diagnosisParam " +
                    "and p.biobank = :biobankParam " +
                    "and p.morphology like :morphologyParam " +
                    "and p.pTNM like :pTNMParam " +
                    "and p.tissueType like :tissueTypeParam " +
                    "and p.TNM like :TNMParam " +
                    "and p.grading = :gradingParam ");
        }

        if(question.getBiobank() != null){
            query.setParameter("biobankParam", question.getBiobank());
        }

        if(question.getGrading() != null){
            query.setParameter("gradingParam", question.getGrading());
        }

        query.setParameter("diagnosisParam", (question.getDiagnosis() != null ? question.getDiagnosis() : "%"));
        query.setParameter("morphologyParam", (question.getMorphology() != null ? question.getMorphology() : "%"));
        query.setParameter("pTNMParam", (question.getpTNM() != null ? question.getpTNM() : "%"));
        query.setParameter("tissueTypeParam", (question.getTissueType() != null ? question.getTissueType() : "%"));
        query.setParameter("TNMParam", (question.getTNM() != null ? question.getTNM() : "%"));

        return query.getResultList();
    }

    /* With wildcards

    public List<Sample> getSelected2(Sample question) {
           Query query = null;
           if (question.getBiobank() == null) {
               query = em.createQuery("SELECT p FROM Sample p where p.diagnosis like :diagnosisParam " +
                       "and p.morphology like :morphologyParam " +
                       "and p.pTNM like :pTNMParam " +
                       "and p.tissueType like :tissueTypeParam " +
                       "and p.TNM like :TNMParam " +
                       "and p.grading = :gradingParam ");

           } else {
               query = em.createQuery("SELECT p FROM Sample p where p.biobank = :biobankParam " +
                       "and p.morphology like :morphologyParam " +
                       "and p.pTNM like :pTNMParam " +
                       "and p.tissueType like :tissueTypeParam " +
                       "and p.TNM like :TNMParam " +
                       "and p.grading = :gradingParam ");
               query.setParameter("biobankParam", question.getBiobank());
           }

           query.setParameter("diagnosisParam", (question.getDiagnosis() != null ? question.getDiagnosis() : "%"));
           query.setParameter("morphologyParam", (question.getMorphology() != null ? question.getMorphology() : "") + "%");
           query.setParameter("pTNMParam", (question.getMorphology() != null ? question.getpTNM() : "") + "%");
           query.setParameter("tissueTypeParam", (question.getMorphology() != null ? question.getTissueType() : "") + "%");
           query.setParameter("TNMParam", (question.getMorphology() != null ? question.getTNM() : "") + "%");
           query.setParameter("gradingParam", (question.getGrading() != null ? question.getGrading() : "*"));

           return query.getResultList();
       }
       */
}
