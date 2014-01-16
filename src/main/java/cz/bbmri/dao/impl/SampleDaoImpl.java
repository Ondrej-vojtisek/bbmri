package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Sample;
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


    @SuppressWarnings("unchecked")
    public List<Sample> getSelected(Sample question, Biobank biobank) {
        notNull(question);
        Query query = null;

        if (biobank == null) {
            //logger.debug("Biobank null grading not null" );
            query = em.createQuery("SELECT p FROM Sample p WHERE " +
                    "p.diagnosis LIKE :diagnosisParam " +
                    "AND p.morphology LIKE :morphologyParam " +
                    "AND p.pTNM LIKE :pTNMParam " +
                    "AND p.tissueType LIKE :tissueTypeParam " +
                    "AND p.TNM LIKE :TNMParam ");
        } else {
            query = em.createQuery("SELECT p FROM Sample p WHERE " +
                    "p.diagnosis LIKE :diagnosisParam " +
                    "AND p.patient.biobank = :biobankParam " +
                    "AND p.morphology LIKE :morphologyParam " +
                    "AND p.pTNM LIKE :pTNMParam " +
                    "AND p.tissueType LIKE :tissueTypeParam " +
                    "AND p.TNM LIKE :TNMParam ");
        }

        if (biobank != null) {
            query.setParameter("biobankParam", biobank);
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
               query = em.createQuery("SELECT p FROM Sample p WHERE p.diagnosis LIKE :diagnosisParam " +
                       "AND p.morphology LIKE :morphologyParam " +
                       "AND p.pTNM LIKE :pTNMParam " +
                       "AND p.tissueType LIKE :tissueTypeParam " +
                       "AND p.TNM LIKE :TNMParam " +
                       "AND p.grading = :gradingParam ");

           } else {
               query = em.createQuery("SELECT p FROM Sample p WHERE p.biobank = :biobankParam " +
                       "AND p.morphology LIKE :morphologyParam " +
                       "AND p.pTNM LIKE :pTNMParam " +
                       "AND p.tissueType LIKE :tissueTypeParam " +
                       "AND p.TNM LIKE :TNMParam " +
                       "AND p.grading = :gradingParam ");
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
