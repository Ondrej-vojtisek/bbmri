package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ModuleDao;
import cz.bbmri.dao.RequestDao;
import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Module;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.sample.Sample;
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
    public boolean create(Sample sample, Long moduleId) {
        notNull(sample);
        notNull(moduleId);

        if (sample.getModule() == null) {
            Module moduleDB = moduleDao.get(moduleId);
            notNull(moduleDB);
            sample.setModule(moduleDB);

            // Original number of samples - it won't be changed during any update
            sample.getSampleNos().setOriginalSamplesNo(sample.getSampleNos().getSamplesNo());
        }
        try {
            sampleDao.create(sample);
        } catch (DataAccessException ex) {
            logger.debug("Sample create failed");
            return false;
        }

        return true;
    }


    public boolean create(Sample sample, Long moduleId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(moduleId, "moduleId", errors)) return false;
        if (isNull(sample, "sample", errors)) return false;

        if(!create(sample, moduleId)){
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

        sampleDao.update(sampleDB);

        // other parameters wont be changed during lifetime of sample

        return sampleDB;
    }

}
