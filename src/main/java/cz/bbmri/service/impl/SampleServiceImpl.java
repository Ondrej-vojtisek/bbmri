package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ModuleDao;
import cz.bbmri.dao.RequestDao;
import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Module;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.enumeration.Status;
import cz.bbmri.entities.sample.*;
import cz.bbmri.io.InstanceImportResult;
import cz.bbmri.service.SampleService;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Transactional
@Service("sampleService")
public class SampleServiceImpl extends BasicServiceImpl implements SampleService {

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private BiobankDao biobankDao;


    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    // Method for automated sample created (triggered event)
    public Sample create(Sample sample, Long moduleId) {
        notNull(sample);
        notNull(moduleId);

        if (sample.getModule() == null) {
            Module moduleDB = moduleDao.get(moduleId);
            notNull(moduleDB);
            sample.setModule(moduleDB);

            // DiagnosisMaterial doesn't register number of samples
            if(sample.getSampleNos() != null){
                if(sample.getSampleNos().getSamplesNo() != null){
                    sample.getSampleNos().setOriginalSamplesNo(sample.getSampleNos().getSamplesNo());
                }
            }
        }
        try {
            sampleDao.create(sample);
        } catch (DataAccessException ex) {
            logger.debug("Sample create failed");
            return null;
        }

        return sample;
    }


    public boolean create(Sample sample, Long moduleId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(moduleId, "moduleId", errors)) return false;
        if (isNull(sample, "sample", errors)) return false;

        sample = create(sample, moduleId);

        if (sample == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.SampleFacadeImpl.sampleCreateFailed"));
        }

        // Archive
        archive("Sample " + sample.getId()
                + " was created.", loggedUserId);

        return true;
    }

    public boolean remove(Long id) {
        if (isNull(id, "id", null)) return false;
        Sample sampleDB = sampleDao.get(id);
        if (isNull(sampleDB, "sampleDB", null)) return false;

        List<Request> requests = sampleDB.getRequests();
        if (requests != null) {
            for (Request request : requests) {
                requestDao.remove(request);
            }
        }
        sampleDao.remove(sampleDB);

        return true;
    }

    @Transactional(readOnly = true)
    public List<Sample> all() {
        return sampleDao.all();
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return sampleDao.count();
    }

    @Transactional(readOnly = true)
    public Sample get(Long id) {
        if (isNull(id, "id", null)) return null;
        return sampleDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Sample> allOrderedBy(String orderByParam, boolean desc) {
        return sampleDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Sample> getSortedSamples(Long biobankId, String orderByParam, boolean desc) {
        if (isNull(biobankId, "biobankId", null)) return null;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        return sampleDao.getSorted(biobankDB, orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public Sample getByInstitutionalId(String id) {
        if (isNull(id, "id", null)) return null;
        return sampleDao.getByInstitutionalId(id);
    }

    @Transactional(readOnly = true)
    public List<Sample> findSamples(Sample sample, Long biobankId, Patient patient, boolean lts) {
        if (isNull(biobankId, "biobankId", null)) return null;
        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        return sampleDao.getSelected(sample, biobankDB, patient, lts);
    }

    public Sample update(Sample sample) {
        if (isNull(sample, "sample", null)) return null;

        Sample sampleDB = sampleDao.get(sample.getId());
        if (isNull(sampleDB, "sampleDB ", null)) return null;

        if (!sampleDB.getSampleIdentification().equals(sample.getSampleIdentification())) {
            logger.error("Sample update - sampleIdentifier doesn't match");
            return null;
        }

        if (sample.getSampleNos() != null) {
            sampleDB.setSampleNos(sample.getSampleNos());
        }

        if (sample.getMaterialType() != null) {
            sampleDB.setMaterialType(sample.getMaterialType());
        }

        sampleDao.update(sampleDB);

        // other parameters wont be changed during lifetime of sample

        return sampleDB;
    }

    private InstanceImportResult updateWithResult(Sample sample) {
        if (isNull(sample, "sample", null)) return null;

        Sample sampleDB = sampleDao.get(sample.getId());

        if (isNull(sampleDB, "sampleDB", null)) return null;

        InstanceImportResult instanceImportResult = new InstanceImportResult(Sample.class.toString());
        // initialize context id
        instanceImportResult.setIdentifier(sample.getId());

        boolean isChanged = false;

        // Material type
        if (sample.getMaterialType() != null) {
            if (!sampleDB.getMaterialType().equals(sample.getMaterialType())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_MATERIALTYPE, sampleDB.getMaterialType(), sample.getMaterialType());
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
        if (sample.getTakingDate() != null) {
            if (!sampleDB.getTakingDate().equals(sample.getTakingDate())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_TAKINGDATE, sampleDB.getTakingDate(), sample.getTakingDate());
                // Change
                sampleDB.setTakingDate(sample.getTakingDate());
                isChanged = true;
            }
        }

        // Taking date
        if (sample.getSampleNos() != null) {
            if (!sampleDB.getSampleNos().equals(sample.getSampleNos())) {
                // Not equals
                // Report
                instanceImportResult.addChange(Sample.PROP_SAMPLENOS, sampleDB.getSampleNos(), sample.getSampleNos());
                // Change
                sampleDB.getSampleNos().setAvailableSamplesNo(sample.getSampleNos().getAvailableSamplesNo());
                sampleDB.getSampleNos().setSamplesNo(sample.getSampleNos().getSamplesNo());
                // Original amount must stay unchanged
                isChanged = true;
            }
        }

        if (sample instanceof Tissue) {
            Tissue tissue = (Tissue) sample;
            Tissue tissueDB = (Tissue) sampleDB;

            // TNM
            if (tissue.getTnm() != null) {
                if (!tissueDB.getTnm().equals(tissue.getTnm())) {
                    // Not equals
                    // Report
                    instanceImportResult.addChange(Tissue.PROP_TNM, tissueDB.getTnm(), tissue.getTnm());
                    // Change
                    tissueDB.setTnm(tissue.getTnm());
                    // Original amount must stay unchanged
                    isChanged = true;
                }
            }

            // PTNM
            if (tissue.getPtnm() != null) {
                if (!tissueDB.getPtnm().equals(tissue.getPtnm())) {
                    // Not equals
                    // Report
                    instanceImportResult.addChange(Tissue.PROP_PTNM, tissueDB.getPtnm(), tissue.getPtnm());
                    // Change
                    tissueDB.setPtnm(tissue.getPtnm());
                    // Original amount must stay unchanged
                    isChanged = true;
                }
            }

            // FREEZE TIME
            if (tissue.getFreezeDate() != null) {
                if (!tissueDB.getFreezeDate().equals(tissue.getFreezeDate())) {
                    // Not equals
                    // Report
                    instanceImportResult.addChange(Tissue.PROP_FREEZEDATE, tissueDB.getFreezeDate(), tissue.getFreezeDate());
                    // Change
                    tissueDB.setFreezeDate(tissue.getFreezeDate());
                    // Original amount must stay unchanged
                    isChanged = true;
                }
            }
        }

        if (sample instanceof DiagnosisMaterial) {
            DiagnosisMaterial diagnosisMaterial = (DiagnosisMaterial) sample;
            DiagnosisMaterial diagnosisMaterialDB = (DiagnosisMaterial) sample;

            // FREEZE TIME
            if (diagnosisMaterial.getDiagnosis() != null) {
                if (!diagnosisMaterialDB.getDiagnosis().equals(diagnosisMaterial.getDiagnosis())) {
                    // Not equals
                    // Report
                    instanceImportResult.addChange(DiagnosisMaterial.PROP_DIAGNOSIS, diagnosisMaterialDB.getDiagnosis(), diagnosisMaterial.getDiagnosis());
                    // Change
                    diagnosisMaterialDB.setDiagnosis(diagnosisMaterial.getDiagnosis());
                    // Original amount must stay unchanged
                    isChanged = true;
                }
            }


        }

        // Set status
        instanceImportResult.setStatus(isChanged ? Status.CHANGED_CURRENT : Status.UNCHANGED_CURRENT);

        sampleDao.update(sampleDB);
        return instanceImportResult;
    }

    public InstanceImportResult importInstance(Sample sample, Long moduleId) {
        notNull(sample);
        notNull(moduleId);

        InstanceImportResult result = new InstanceImportResult(Sample.class.toString());

        if (sample.getSampleIdentification() == null) {
            result.setStatus(Status.ERROR);
            return result;
        }

        if (sample.getSampleIdentification().getSampleId() == null) {
            result.setStatus(Status.ERROR);
            return result;
        }

        Sample sampleDB = getByInstitutionalId(sample.getSampleIdentification().getSampleId());

        // is sample in DB ?
        if (sampleDB == null) {
            // No .. lets create it
            Sample newSample = create(sample, moduleId);

            // Unable to create new sample
            if (newSample == null) {
                result.addChange(Sample.PROP_SAMPLEIDENTIFICATION, null, sample.getSampleIdentification().getSampleId());
                result.setStatus(Status.ERROR);
                return result;
            } else {
                // new sample was successfully added
                result.setIdentifier(sample.getId());
                result.setStatus(Status.ADDED_NEW);
                return result;
            }

        } else {
            // Sample is already in DB

            sample.setId(sampleDB.getId());

            result = updateWithResult(sample);

            // If something went wrong
            if (result == null) {
                result = new InstanceImportResult(Sample.class.toString());
                result.setIdentifier(sample.getId());
                result.setStatus(Status.ERROR);
            }

        }
        return result;
    }

}
