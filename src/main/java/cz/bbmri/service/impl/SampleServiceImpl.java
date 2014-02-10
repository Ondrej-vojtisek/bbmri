package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.PatientDao;
import cz.bbmri.dao.RequestDao;
import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.*;
import cz.bbmri.service.SampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /*
    public Sample create(Sample sample) {
        notNull(sample);
        sampleDao.create(sample);
        return sample;
    }
    */

    public Sample create(Sample sample, Long patientId) {
        notNull(sample);
        notNull(patientId);

        Patient patientDB = patientDao.get(patientId);
        if (patientDB == null) {
            return null;
            // TODO: exception
        }

        sampleDao.create(sample);

       // sample.setPatient(patientDB);
        sampleDao.update(sample);
//        patientDB.getSamples().add(sample);
        patientDao.update(patientDB);


        return sample;
    }

    public boolean remove(Long id) {

        notNull(id);
        Sample sampleDB = sampleDao.get(id);

        if (sampleDB == null) {
            return false;
        }

   /*     Patient patientDB = patientDao.get(sampleDB.getPatient().getId());
        if (patientDB != null) {
            patientDB.getSamples().remove(sampleDB);
            patientDao.update(patientDB);
            sampleDB.setPatient(null);
        }
        */

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
    public List<Sample> getSamplesByQuery(Sample sample) {
        notNull(sample);

        // TODO: there should be also link to biobank

        return sampleDao.getSelected(sample, null);
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
    public Sample eagerGet(Long id, boolean patient, boolean request) {
        notNull(id);
        Sample sampleDB = sampleDao.get(id);


          /*Not only comments - this force hibernate to load mentioned relationship from db. Otherwise it wont be accessible from presentational layer of application.*/

//        if (patient) {
//            logger.debug("" + sampleDB.getPatient());
//        }
        if (request) {
            logger.debug("" + sampleDB.getRequests());
        }

        return sampleDB;

    }

    @Transactional(readOnly = true)
    public List<Sample> allOrderedBy(String orderByParam, boolean desc){
        return sampleDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Sample> nOrderedBy(String orderByParam, boolean desc, int number){
        return sampleDao.nOrderedBy(orderByParam, desc, number);
    }

}
