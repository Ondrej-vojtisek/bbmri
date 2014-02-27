package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.sample.Tissue;
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


//    select b.fname, b.lname from Users b JOIN b.groups c where c.groupName = :groupName

//    SELECT o
//    FROM OrderEntity o
//    WHERE TYPE(o) <> RecurringOrderEntity
//      AND o.cancellationDate is null
//      AND o.maxOccurrences = o.occurrence

    @SuppressWarnings("unchecked")
    public List<Sample> getSelected(Sample question, Biobank biobank, Patient patient, boolean lts) {
        notNull(question);
        Query query = null;

        query = em.createQuery("SELECT sample FROM Sample sample WHERE " +
                "sample.module.patient.biobank = :biobank " +
//                "AND Type(sample.module) IN (ModuleLTS) " ) ; // +
                "AND (sample.module.patient.birthMonth = :birthMonth  OR :birthMonth IS NULL ) " +
                "AND (sample.module.patient.birthYear = :birthYear OR :birthYear IS NULL ) " +
                "AND sample.module.patient.consent = :consent " +
                "AND sample.module.patient.sex = :sex " +
                "AND sample.materialType.type LIKE :materialType " +
                "AND sample.retrieved = :retrieved " +
                "AND sample.sampleIdentification.number LIKE :numberParam " +
                "AND sample.sampleIdentification.year LIKE :year " +
                "AND sample.sampleIdentification.sampleId LIKE :sampleId ");


        query.setParameter("biobank", biobank);
      //  query.setParameter("module", (lts ? ModuleLTS.class : ModuleSTS.class));
        query.setParameter("birthMonth", (patient.getBirthMonth() != null ? patient.getBirthMonth() : null));
        query.setParameter("birthYear", (patient.getBirthYear() != null ? patient.getBirthYear() : null));
        query.setParameter("consent", patient.isConsent());
        query.setParameter("sex", (patient.getSex() != null ? patient.getSex() : "*"));
        query.setParameter("materialType", (question.getMaterialType() != null ?
                question.getMaterialType().getType() : "%"));
        query.setParameter("retrieved", (question.getRetrieved() != null ? question.getRetrieved() : "*"));

        if (question.getSampleIdentification() == null) {
            query.setParameter("numberParam", "%");
            query.setParameter("year", "%");
            query.setParameter("sampleId", "%");
        } else {
            query.setParameter("numberParam", (question.getSampleIdentification().getNumber() != null ?
                    question.getSampleIdentification().getNumber() : "%"));
            query.setParameter("year", (question.getSampleIdentification().getYear() != null ?
                           question.getSampleIdentification().getYear() : "%"));
            query.setParameter("sampleId", (question.getSampleIdentification().getSampleId() != null ?
                       question.getSampleIdentification().getSampleId() : "%"));
        }

        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Sample> getSelected(Sample question, Biobank biobank) {
//        notNull(question);
//        Query query = null;
//
//        if (biobank == null) {
//            //logger.debug("Biobank null grading not null" );
//            query = em.createQuery("SELECT p FROM Sample p WHERE " +
//                    "p.diagnosis LIKE :diagnosisParam " +
//                    "AND p.morphology LIKE :morphologyParam " +
//                    "AND p.pTNM LIKE :pTNMParam " +
//                    "AND p.tissueType LIKE :tissueTypeParam " +
//                    "AND p.TNM LIKE :TNMParam ");
//        } else {
//            query = em.createQuery("SELECT p FROM Sample p WHERE " +
//                    "p.diagnosis LIKE :diagnosisParam " +
//                    "AND p.patient.biobank = :biobankParam " +
//                    "AND p.morphology LIKE :morphologyParam " +
//                    "AND p.pTNM LIKE :pTNMParam " +
//                    "AND p.tissueType LIKE :tissueTypeParam " +
//                    "AND p.TNM LIKE :TNMParam ");
//        }
//
//        if (biobank != null) {
//            query.setParameter("biobankParam", biobank);
//        }
//
//        query.setParameter("diagnosisParam", (question.getDiagnosis() != null ? question.getDiagnosis() : "%"));
//        query.setParameter("morphologyParam", (question.getMorphology() != null ? question.getMorphology() : "%"));
//        query.setParameter("pTNMParam", (question.getpTNM() != null ? question.getpTNM() : "%"));
//        query.setParameter("tissueTypeParam", (question.getTissueType() != null ? question.getTissueType() : "%"));
//        query.setParameter("TNMParam", (question.getTNM() != null ? question.getTNM() : "%"));

//        return query.getResultList();

        return null;
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

    public void create(Tissue tissue) {
        notNull(tissue);
        em.persist(tissue);
    }
}
