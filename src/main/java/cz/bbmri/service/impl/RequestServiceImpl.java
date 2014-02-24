package cz.bbmri.service.impl;

import cz.bbmri.dao.RequestDao;
import cz.bbmri.dao.SampleDao;
import cz.bbmri.dao.SampleRequestDao;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.facade.exceptions.InsuficientAmountOfSamplesException;
import cz.bbmri.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("requestService")
public class RequestServiceImpl extends BasicServiceImpl implements RequestService {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private SampleRequestDao sampleRequestDao;

    public int createRequests(List<Long> sampleIds, Long sampleRequestId) throws InsuficientAmountOfSamplesException {
        if (sampleIds == null) {
            logger.debug("sampleIds can't be null");
            return -1;
        }

        if (sampleIds.isEmpty()) {
            logger.debug("sampleIds can't be empty");
            return -1;
        }

        if (sampleRequestId == null) {
            logger.debug("sampleRequestId can't be null");
            return -1;
        }

        SampleRequest sampleRequestDB = sampleRequestDao.get(sampleRequestId);

        if (sampleRequestDB == null) {
            logger.debug("sampleRequestDB can't be null");
            return -1;
        }

        int result = 0;

        for (Long sampleId : sampleIds) {
            Sample sampleDB = sampleDao.get(sampleId);
            if (sampleDB == null) {
                logger.debug("sampleDB can't be null - sampleId was: " + sampleId);
                continue;
            }

            // There must be more available samples than IMPLICIT_REQUESTED_SAMPLES

            if (sampleDB.getSampleNos().getAvailableSamplesNo() < Request.IMPLICIT_REQUESTED_SAMPLES) {

                logger.debug("Sample with sampleId: " + sampleId + " can't be requested because number of available " +
                        "samples is less then " + Request.IMPLICIT_REQUESTED_SAMPLES);

                throw new InsuficientAmountOfSamplesException("Sample with sampleId: " + sampleId +
                        " can't be requested because number of available " +
                        "samples is less then " + Request.IMPLICIT_REQUESTED_SAMPLES);
            }

            Request request = new Request();
            request.setSample(sampleDB);
            request.setNumOfRequested(Request.IMPLICIT_REQUESTED_SAMPLES);
            request.setSampleRequest(sampleRequestDB);
            requestDao.create(request);

            int availableSamples = sampleDB.getSampleNos().getAvailableSamplesNo();
            sampleDB.getSampleNos().setAvailableSamplesNo(
                    availableSamples - Request.IMPLICIT_REQUESTED_SAMPLES);
            sampleDao.update(sampleDB);
            result++;
        }

        return result;
    }

    public boolean remove(Long id) {
        notNull(id);
        Request requestDB = requestDao.get(id);
        if (requestDB == null) {
            logger.debug("requestDB can't be null");
            return false;
        }

        // Delete sample allocation - return to previous state before request

        Sample sampleDB = requestDB.getSample();
        int availableSamples = sampleDB.getSampleNos().getAvailableSamplesNo();
        sampleDB.getSampleNos().setAvailableSamplesNo(availableSamples + requestDB.getNumOfRequested());
        sampleDao.update(sampleDB);

        if (requestDB.getSample() != null) {
            requestDB.setSample(null);
        }

        if (requestDB.getSampleRequest() != null) {
            requestDB.setSampleRequest(null);
        }

        requestDao.remove(requestDB);
        return true;
    }

    public Request update(Request request) {
        notNull(request);
        Request requestDB = requestDao.get(request.getId());
        if (requestDB == null) {
            logger.debug("requestDB can't be null");
            return null;
        }
        /* nothing is changed */
        if (request.getNumOfRequested() == null) {
            return requestDB;
        }

        if (request.getNumOfRequested() < 0) {
            logger.debug("Number of requested can't be lower than zero");
            return null;
        }

        // New - old value

        int difference = request.getNumOfRequested() - requestDB.getNumOfRequested();

        if (difference == 0) {
            return requestDB;
        }


        Sample sampleDB = requestDB.getSample();

        // check if sampleNos is not null

        if (sampleDB.getSampleNos() == null) {
            logger.debug("sample.SampleNos is null");
            return null;
        }

        if (sampleDB.getSampleNos().getAvailableSamplesNo() == null) {
            logger.debug("sample.SampleNos.availableSamplesNo is null");
            return null;
        }

        int availableSamples = sampleDB.getSampleNos().getAvailableSamplesNo();

        // try to increase

        if (difference > 0) {

           /* request can be increased*/

            if (difference < availableSamples) {
                sampleDB.getSampleNos().setAvailableSamplesNo(
                        availableSamples - difference);
            } else {

                 /* request can't be arised */

                logger.debug("Number of requested can't be higher than available samples");
                return null;
            }

            // try to decrease

        } else {
            sampleDB.getSampleNos().setAvailableSamplesNo(

                    availableSamples + (requestDB.getNumOfRequested() - request.getNumOfRequested()));

            if (sampleDB.getSampleNos().getAvailableSamplesNo() > sampleDB.getSampleNos().getSamplesNo()) {

                /* Higher amount of available samples then total number*/

                logger.debug("This should never happen");

                return null;
            }
        }

        // save new number of available samples

        sampleDao.update(sampleDB);

        // save updates to the request

        requestDao.update(requestDB);

        return requestDB;
    }

    @Transactional(readOnly = true)
    public List<Request> all() {
        return requestDao.all();
    }

    @Transactional(readOnly = true)
    public Request get(Long id) {
        notNull(id);
        return requestDao.get(id);
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return requestDao.count();
    }

    @Transactional(readOnly = true)
    public List<Request> allOrderedBy(String orderByParam, boolean desc) {
        return requestDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Request> nOrderedBy(String orderByParam, boolean desc, int number) {
        return requestDao.nOrderedBy(orderByParam, desc, number);
    }
}
