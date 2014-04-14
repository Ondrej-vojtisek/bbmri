package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
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
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:22
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("sampleService")
public class SampleServiceImpl extends BasicServiceImpl implements SampleService {

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private ProjectDao projectDao;


    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public boolean create(Sample sample, Long moduleId) {
        notNull(sample);

        if (sample.getModule() == null) {
            if (moduleId == null) {
                logger.debug("Module must be defined during sample create");
                return false;
            }
            Module moduleDB = moduleDao.get(moduleId);
            if (moduleDB == null) {
                logger.debug("ModuleDB can't be null");
                return false;
            }
            sample.setModule(moduleDB);

            // Original number of samples - it won't be changed during any update
            sample.getSampleNos().setOriginalSamplesNo(sample.getSampleNos().getSamplesNo());
        }
        sampleDao.create(sample);

        return true;
    }


    public boolean create(Sample sample, Long moduleId, ValidationErrors errors) {
        notNull(sample);

        if (sample.getModule() == null) {
            if (moduleId == null) {
                logger.debug("Module must be defined during sample create");
                return false;
            }
            Module moduleDB = moduleDao.get(moduleId);
            if (moduleDB == null) {
                logger.debug("ModuleDB can't be null");
                return false;
            }
            sample.setModule(moduleDB);

            // Original number of samples - it won't be changed during any update
            sample.getSampleNos().setOriginalSamplesNo(sample.getSampleNos().getSamplesNo());
        }
        try {
            sampleDao.create(sample);
        } catch (DataAccessException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.SampleFacadeImpl.sampleCreateFailed"));
            return false;
        }

        return true;
    }

    public boolean remove(Long id) {

        notNull(id);
        Sample sampleDB = sampleDao.get(id);

        if (sampleDB == null) {
            return false;
        }

        List<Request> requests = sampleDB.getRequests();
        if (requests != null) {
            for (Request request : requests) {
                requestDao.remove(request);
            }
        }
        sampleDao.remove(sampleDB);

        return true;
    }

    public Sample update(Sample sample) {
        notNull(sample);
        sampleDao.update(sample);
        return sample;
    }

    @Transactional(readOnly = true)
    public List<Sample> all() {
        return sampleDao.all();
    }

    public Sample decreaseCount(Long sampleId, Integer requested) {
        notNull(sampleId);
        notNull(requested);

        Sample sampleDB = sampleDao.get(sampleId);
        if (sampleDB == null) {
            return null;
            // TODO: exception
        }

//        Integer available = sampleDB.getNumOfAvailable();
//        Integer numOfSamples = sampleDB.getNumOfSamples();
//
//        if ((available - requested) >= 0) {
//            sampleDB.setNumOfAvailable(available - requested);
//            sampleDB.setNumOfSamples(numOfSamples - requested);
//
//
//        } else {
//            //TODO: exception
//
//            return sampleDB;
//        }

        sampleDao.update(sampleDB);
        return sampleDB;
    }

    public Sample withdrawSample(Long sampleId, Integer requested) {
        notNull(sampleId);
        notNull(requested);

        Sample sample = sampleDao.get(sampleId);
//        Integer available = sample.getNumOfAvailable();
//        Integer numOfSamples = sample.getNumOfSamples();
//        if ((available - requested) >= 0) {
//            available -= requested;
//            numOfSamples -= requested;
//        } else if ((numOfSamples - requested) >= 0) {
//            available = 0;
//            numOfSamples -= requested;
//        } else {
//            // TODO: exception
//            return sample;
//        }
//        if (numOfSamples == 0) {
//            sampleDao.remove(sample);
//            return null;
//        }
//        sample.setNumOfAvailable(available);
//        sample.setNumOfSamples(numOfSamples);
//        sampleDao.update(sample);
        return sample;
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return sampleDao.count();
    }

    @Transactional(readOnly = true)
    public Sample get(Long id) {
        notNull(id);
        return sampleDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Sample> allOrderedBy(String orderByParam, boolean desc) {
        return sampleDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Sample> getSortedSamples(Long biobankId, String orderByParam, boolean desc) {
        if (biobankId == null) {
            logger.debug("biobankId is null");
            return null;
        }

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can´t be null");
            return null;
        }

        return sampleDao.getSorted(biobankDB, orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public Sample getByInstitutionalId(String id) {
        return sampleDao.getByInstitutionalId(id);
    }

    public List<Sample> findSamples(Sample sample, Long biobankId, Patient patient, boolean lts) {
        logger.debug("Facade findSamples sample: " + sample + " biobankId: " + biobankId + " Patient: " + patient
                + " LTS: " + lts);
        return sampleDao.getSelected(sample, biobankDao.get(biobankId), patient, lts);
    }

}
