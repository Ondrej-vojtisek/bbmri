package cz.bbmri.dao.impl;

import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.sample.Sample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation for interface handling instances of Sample. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class SampleDaoImpl extends BasicDaoImpl<Sample> implements SampleDao {


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
                "AND sample.sampleIdentification.number = :numberParam OR :numberParam IS NULL " +
                "AND sample.sampleIdentification.year = :year OR :year IS NULL " +
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
            typedQuery.setParameter("numberParam", (question.getSampleIdentification().getNumber()
                                != null ? question.getSampleIdentification().getNumber() : null));
            typedQuery.setParameter("year", (question.getSampleIdentification().getYear()
                    != null ? question.getSampleIdentification().getYear() : null));
            typedQuery.setParameter("sampleId", (question.getSampleIdentification().getSampleId() != null ?
                    question.getSampleIdentification().getSampleId() : "%"));
        }

        return typedQuery.getResultList();
    }

    public Sample getByInstitutionalId(String id) {
        notNull(id);
        typedQuery = em.createQuery("SELECT p FROM Sample p WHERE p.sampleIdentification.sampleId = :idParam", Sample.class);
        typedQuery.setParameter("idParam", id);

        return getSingleResult();
    }
}
