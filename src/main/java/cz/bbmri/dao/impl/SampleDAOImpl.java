package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.SampleDAO;
import cz.bbmri.entity.*;
import cz.bbmri.entity.enumeration.Status;
import cz.bbmri.io.InstanceImportResult;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("sampleDAO")
@Transactional
public class SampleDAOImpl extends GenericDAOImpl<Sample> implements SampleDAO {

    public Sample get(Long id) {
        return (Sample) getCurrentSession().get(Sample.class, id);
    }

    public Sample getByInstitutionalId(String id) {

        Criterion criterionInstitutionalId = Restrictions.eq(Sample.PROP_INSTITUTIONAL_ID, id);

        // Retrieve a list of existing users matching the criterion above the list retrieval
        List<Sample> list = getCurrentSession().createCriteria(Sample.class)
                .add(criterionInstitutionalId)
                .setMaxResults(1).list();

        // Retrieve iterator from the list
        Iterator iterator = list.iterator();

        // Prepare the variable
        Sample sample = null;

        // If user loaded
        if (iterator.hasNext()) {

            // Store the user instance
            sample = (Sample) iterator.next();
        }

        return sample;

    }

    public List<Sample> getAllByBiobank(Biobank biobank) {

        List<Sample> samples = getCurrentSession().createCriteria(Sample.class)
                .createAlias("patient", "patient")
                .add(Restrictions.eq("patient.biobank", biobank)).list();

        if (samples.isEmpty()) {
            return null;
        }

        return samples;
    }

    public InstanceImportResult updateWithResult(Sample sample) {
        notNull(sample);

        Sample sampleDB = get(sample.getId());

        notNull(sampleDB);

        InstanceImportResult instanceImportResult = new InstanceImportResult(Sample.class.toString());
        // initialize context id
        instanceImportResult.setIdentifier(sample.getId());

        boolean isChanged = false;

        // Material type
        if (sample.getMaterialType() != null) {

            if (!sampleDB.getMaterialType().equals(sample.getMaterialType())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_MATERIAL_TYPE, sampleDB.getMaterialType(), sample.getMaterialType());
                // Change
                sampleDB.setMaterialType(sample.getMaterialType());
                isChanged = true;
            }
        }

        // Retrieved
        if (sample.getRetrieved() != null) {
            if (!sampleDB.getRetrieved().equals(sample.getRetrieved())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_RETRIEVED, sampleDB.getRetrieved(), sample.getRetrieved());
                // Change
                sampleDB.setRetrieved(sample.getRetrieved());
                isChanged = true;
            }
        }

        // Taking date
        if (sample.getTakingDateTime() != null) {

            // the format how is date stored is affected by DB, better to compare time
            if (sampleDB.getTakingDateTime().getTime() != (sample.getTakingDateTime().getTime())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_TAKING_DATE, sampleDB.getTakingDateTime(), sample.getTakingDateTime());
                // Change
                sampleDB.setTakingDateTime(sample.getTakingDateTime());
                isChanged = true;
            }
        }

        // Quantity
        if (sample.getQuantity() != null) {
            if (!sampleDB.getQuantity().equals(sample.getQuantity())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_QUANTITY, sampleDB.getQuantity(), sample.getQuantity());
                // Change
                sampleDB.getQuantity().setAvailable(sample.getQuantity().getAvailable());
                sampleDB.getQuantity().setTotal(sample.getQuantity().getTotal());
                // Original amount must stay unchanged
                isChanged = true;
            }
        }


        // TNM
        if (sample.getTnm() != null) {
            if (sampleDB.getTnm() == null) {
                instanceImportResult.addChange(Sample.PROP_TNM, sampleDB.getTnm(), sample.getTnm());
                // Change
                sampleDB.setTnm(sample.getTnm());
                // Original amount must stay unchanged
                isChanged = true;
            } else if (!sampleDB.getTnm().equals(sample.getTnm())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_TNM, sampleDB.getTnm(), sample.getTnm());
                // Change
                sampleDB.setTnm(sample.getTnm());
                // Original amount must stay unchanged
                isChanged = true;
            }
        }

        // PTNM
        if (sample.getPtnm() != null) {
            if (sampleDB.getPtnm() == null) {
                instanceImportResult.addChange(Sample.PROP_PTNM, sampleDB.getPtnm(), sample.getPtnm());
                // Change
                sampleDB.setPtnm(sample.getPtnm());
                // Original amount must stay unchanged
                isChanged = true;
            } else if (!sampleDB.getPtnm().equals(sample.getPtnm())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_PTNM, sampleDB.getPtnm(), sample.getPtnm());
                // Change
                sampleDB.setPtnm(sample.getPtnm());
                // Original amount must stay unchanged
                isChanged = true;
            }
        }

        // Morphology
        if (sample.getMorphology() != null) {
            if (sampleDB.getMorphology() == null) {
                instanceImportResult.addChange(Sample.PROP_MORPHOLOGY, sampleDB.getMorphology(), sample.getMorphology());
                // Change
                sampleDB.setMorphology(sample.getMorphology());
                // Original amount must stay unchanged
                isChanged = true;
            } else if (!sampleDB.getMorphology().equals(sample.getMorphology())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_MORPHOLOGY, sampleDB.getMorphology(), sample.getMorphology());
                // Change
                sampleDB.setMorphology(sample.getMorphology());
                // Original amount must stay unchanged
                isChanged = true;
            }
        }

        // FREEZE TIME

        if (sample.getFreezeDateTime() != null) {

            if (sampleDB.getFreezeDateTime() == null) {
                instanceImportResult.addChange(Sample.PROP_FREEZE_DATE, sampleDB.getFreezeDateTime(), sample.getFreezeDateTime());
                // Change
                sampleDB.setFreezeDateTime(sample.getFreezeDateTime());
                // Original amount must stay unchanged
                isChanged = true;
                // the format how is date stored is affected by DB, better to compare time
            } else if (sampleDB.getFreezeDateTime().getTime() != sample.getFreezeDateTime().getTime()) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_FREEZE_DATE, sampleDB.getFreezeDateTime(), sample.getFreezeDateTime());
                // Change
                sampleDB.setFreezeDateTime(sample.getFreezeDateTime());
                // Original amount must stay unchanged
                isChanged = true;
            }
        }

        // BIOPTICAL REPORT
        if (sample.getBiopticalReport() != null) {

            if (sampleDB.getBiopticalReport() == null) {
                instanceImportResult.addChange(Sample.PROP_BIOPTICAL_REPORT, sampleDB.getBiopticalReport(), sample.getBiopticalReport());
                // Change
                sampleDB.setBiopticalReport(sample.getBiopticalReport());
                // Original amount must stay unchanged
                isChanged = true;
            } else if (!sampleDB.getBiopticalReport().equals(sample.getBiopticalReport())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_BIOPTICAL_REPORT, sampleDB.getBiopticalReport(), sample.getBiopticalReport());
                // Change
                sampleDB.setBiopticalReport(sample.getBiopticalReport());
                // Original amount must stay unchanged
                isChanged = true;
            }
        }

        // STORAGE METHOLOGY
        if (sample.getStorageMethodology() != null) {

            if (sampleDB.getStorageMethodology() == null) {
                instanceImportResult.addChange(Sample.PROP_STORAGE_METHODOLOGY, sampleDB.getStorageMethodology(), sample.getStorageMethodology());
                // Change
                sampleDB.setStorageMethodology(sample.getStorageMethodology());
                // Original amount must stay unchanged
                isChanged = true;
            } else if (!sampleDB.getStorageMethodology().equals(sample.getStorageMethodology())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_STORAGE_METHODOLOGY, sampleDB.getStorageMethodology(), sample.getStorageMethodology());
                // Change
                sampleDB.setStorageMethodology(sample.getStorageMethodology());
                // Original amount must stay unchanged
                isChanged = true;
            }
        }


        // DIAGNOSIS
        if (sample.getDiagnosis() != null) {
            if (sampleDB.getDiagnosis() == null) {
                instanceImportResult.addChange(Sample.PROP_DIAGNOSIS, sampleDB.getDiagnosis(), sample.getDiagnosis());
                // Change
                sampleDB.setDiagnosis(sample.getDiagnosis());
                // Original amount must stay unchanged
                isChanged = true;
            } else if (!sampleDB.getDiagnosis().equals(sample.getDiagnosis())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_DIAGNOSIS, sampleDB.getDiagnosis(), sample.getDiagnosis());
                // Change
                sampleDB.setDiagnosis(sample.getDiagnosis());
                // Original amount must stay unchanged
                isChanged = true;
            }
        }

        // Set status
        instanceImportResult.setStatus(isChanged ? Status.CHANGED_CURRENT : Status.UNCHANGED_CURRENT);

        save(sampleDB);
        return instanceImportResult;
    }

    public List<Sample> find(Biobank biobank,
                             Boolean sts,
                             Retrieved retrieved,
                             Short available,
                             Short total,
                             MaterialType materialType) {

        List<Sample> samples = getCurrentSession().createCriteria(Sample.class)
                .createAlias("patient", "patient")
                .add(Restrictions.eq("patient.biobank", biobank))
                .add(Restrictions.eq("sample.retrieved", retrieved)).setMaxResults(MAX_SEARCH_RESULTS).list();

        if (samples.isEmpty()) {
            return null;
        }

        return samples;
    }

}
