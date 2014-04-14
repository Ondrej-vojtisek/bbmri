package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.Tissue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:17
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class SampleDaoImpl extends BasicDaoImpl<Sample, Long> implements SampleDao {


    public List<Sample> getSorted(Biobank biobank, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY sample." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        typedQuery = em.createQuery("SELECT sample FROM Sample sample WHERE " +
                "sample.module.patient.biobank = :biobank " +
                orderParam, Sample.class);
        typedQuery.setParameter("biobank", biobank);
        return typedQuery.getResultList();
    }

    public List<Sample> getSelected(Sample question, Biobank biobank, Patient patient,
                                    boolean lts) {
        notNull(question);

        typedQuery = em.createQuery("SELECT sample FROM Sample sample WHERE " +
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
                "AND sample.sampleIdentification.sampleId LIKE :sampleId ", Sample.class);


        typedQuery.setParameter("biobank", biobank);
        //  query.setParameter("module", (lts ? ModuleLTS.class : ModuleSTS.class));
        typedQuery.setParameter("birthMonth", (patient.getBirthMonth() != null ? patient.getBirthMonth() : null));
        typedQuery.setParameter("birthYear", (patient.getBirthYear() != null ? patient.getBirthYear() : null));
        typedQuery.setParameter("consent", patient.isConsent());
        typedQuery.setParameter("sex", (patient.getSex() != null ? patient.getSex() : "*"));
        typedQuery.setParameter("materialType", (question.getMaterialType() != null ?
                question.getMaterialType().getType() : "%"));
        typedQuery.setParameter("retrieved", (question.getRetrieved() != null ? question.getRetrieved() : "*"));

        if (question.getSampleIdentification() == null) {
            typedQuery.setParameter("numberParam", "%");
            typedQuery.setParameter("year", "%");
            typedQuery.setParameter("sampleId", "%");
        } else {
            typedQuery.setParameter("numberParam", (question.getSampleIdentification().getNumber() != null ?
                    question.getSampleIdentification().getNumber() : "%"));
            typedQuery.setParameter("year", (question.getSampleIdentification().getYear() != null ?
                    question.getSampleIdentification().getYear() : "%"));
            typedQuery.setParameter("sampleId", (question.getSampleIdentification().getSampleId() != null ?
                    question.getSampleIdentification().getSampleId() : "%"));
        }

        return typedQuery.getResultList();
    }

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


    public Sample getByInstitutionalId(String id) {
        notNull(id);
        typedQuery = em.createQuery("SELECT p FROM Sample p WHERE p.sampleIdentification.sampleId = :idParam", Sample.class);
        typedQuery.setParameter("idParam", id);

        return getSingleResult();
    }
}
