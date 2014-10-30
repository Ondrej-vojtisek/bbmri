package cz.bbmri.service.impl;

import cz.bbmri.dao.RequestDao;
import cz.bbmri.dao.SampleDao;
import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.sample.Sample;
import cz.bbmri.service.RequestService;
import cz.bbmri.service.exceptions.InsuficientAmountOfSamplesException;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Transactional
@Service("requestService")
public class RequestServiceImpl extends BasicServiceImpl implements RequestService {

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    public boolean createRequests(List<Long> sampleIds, Long sampleQuestionId, ValidationErrors errors,
                                  List<Message> messages, Long loggedUserId) {
        notNull(errors);
        notNull(messages);

        if (isNull(sampleIds, "sampleIds", errors)) return false;
        if (isNull(sampleQuestionId, "sampleQuestionId", errors)) return false;

        if (sampleIds.isEmpty()) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.requestCreateFailed"));
            return false;
        }

        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(sampleQuestionId);
        if (isNull(sampleQuestionDB, "sampleQuestionDB", errors)) return false;
        // counter of succesfully added requests
        int numberOfAdded = 0;

        for (Long sampleId : sampleIds) {
            Sample sampleDB = sampleDao.get(sampleId);
            boolean result;
            try {
                result = createRequest(sampleDB, sampleQuestionDB);

            } catch (InsuficientAmountOfSamplesException ex) {
                logger.debug(ex.getMessage());
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.insufficientAvailableSamples"));
                continue;
            }

            if (result) {
                numberOfAdded++;
            } else {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.requestCreateFailed"));
                // TODO erase created requests
                return false;
            }
        }
        // no exception but no request created
        if (numberOfAdded == 0) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.noRequestWasCreated"));
            return true;
        }
        // Succ msg
        messages.add(new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.createRequestSuccess", numberOfAdded));

        // Archive
        archive("Samples requested for requisition (sampleQuestion) with id: " + sampleQuestionId, loggedUserId);

        return true;
    }

    private boolean createRequest(Sample sample, SampleQuestion sampleQuestionDB) throws InsuficientAmountOfSamplesException {
        // check
        if (isNull(sampleQuestionDB, "sampleQuestionDB", null)) return false;
        if (isNull(sample, "sample", null)) return false;
        // known number of samples
        if (isNull(sample.getSampleNos(), "sample.getSampleNos", null)) return false;

        // There must be more available samples than IMPLICIT_REQUESTED_SAMPLES
        if (sample.getSampleNos().getAvailableSamplesNo() < Request.IMPLICIT_REQUESTED_SAMPLES) {
            throw new InsuficientAmountOfSamplesException("Sample with sampleId: " + sample.getId() +
                    " can't be requested because number of available " +
                    "samples is less then " + Request.IMPLICIT_REQUESTED_SAMPLES);
        }

        // new Request
        Request request = new Request();
        request.setSample(sample);
        request.setNumOfRequested(Request.IMPLICIT_REQUESTED_SAMPLES);
        request.setWithdraw(sampleQuestionDB);


        // decrease
        if (!sample.getSampleNos().decreaseAmount(Request.IMPLICIT_REQUESTED_SAMPLES)) {
            throw new InsuficientAmountOfSamplesException("Sample with sampleId: " + sample.getId() +
                    " can't be requested because number of available " +
                    "samples is less then " + Request.IMPLICIT_REQUESTED_SAMPLES);
        }
        requestDao.create(request);

        sampleDao.update(sample);
        return true;
    }

    // auto triggered method
    public boolean remove(Long requestId) {
        notNull(requestId);
        Request requestDB = requestDao.get(requestId);
        notNull(requestDB);

        Sample sampleDB = requestDB.getSample();
        notNull(sampleDB);
        notNull(sampleDB.getSampleNos());

        if (!sampleDB.getSampleNos().increaseAmount(requestDB.getNumOfRequested())) {
            logger.error("Failed to remove request");
            return false;
        }
        // update number of samples
        sampleDao.update(sampleDB);

        requestDB.setSample(null);
        if (requestDB.getWithdraw() != null) {
            requestDB.setWithdraw(null);
        }

        requestDao.remove(requestDB);

        // Archive
        archiveSystem("Request with id: " + requestId + " was removed.");

        return true;
    }

    public boolean remove(Long requestId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(requestId, "requestId", errors)) return false;

        Request requestDB = requestDao.get(requestId);
        if (isNull(requestDB, "requestDB", errors)) return false;

        Sample sampleDB = requestDB.getSample();
        if (isNull(sampleDB, "sampleDB", errors)) return false;
        if (isNull(sampleDB.getSampleNos(), "sampleDB.getSampleNos", errors)) return false;

        if (!remove(requestId)) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.failedToRemoveRequest"));
            return false;
        }

        // Archive
        archive("Request with id: " + requestId + " was removed.", loggedUserId);

        return true;
    }

    public boolean changeRequestedAmount(Long requestId, boolean increase, int difference,
                                         ValidationErrors errors, Long loggedUserId) {
        notNull(errors);
        if (isNull(requestId, "requestId", errors)) return false;

        Request requestDB = get(requestId);
        if (isNull(requestDB, "requestDB", errors)) return false;

        if (difference < 0) {
            noEffect(errors);
            logger.debug("Number of requested can't be lower than zero");
            return false;
        }

        int newValue = requestDB.getNumOfRequested();

        Sample sampleDB = requestDB.getSample();
        // check of fields
        if (isNull(sampleDB, "sampleDB", errors)) return false;
        if (isNull(sampleDB.getSampleNos(), "sampleDB.getSampleNos", errors)) return false;

        int availableSamples = sampleDB.getSampleNos().getAvailableSamplesNo();

        // try to increase required samples
        if (increase) {
            newValue += difference;

            if (difference < availableSamples) {
                // there is enough available samples to fulfill request
                // lower number of available samples
                sampleDB.getSampleNos().decreaseAmount(difference);
            } else {
                // there is not enough available samples to fulfill request
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.insufficientAvailableSamples"));
                return false;
            }
        } else {
            // try to decrease of required samples
            newValue -= difference;
            // higher number of available samples - less samples is requested than before
            sampleDB.getSampleNos().increaseAmount(difference);

            if (sampleDB.getSampleNos().getAvailableSamplesNo() > sampleDB.getSampleNos().getSamplesNo()) {
                logger.error("Fatal error");
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.changeNumberFailed"));
                return false;
            }
        }
        // set new value to request
        requestDB.setNumOfRequested(newValue);
        // save updates to the request
        requestDao.update(requestDB);
        // save new number of available samples
        sampleDao.update(sampleDB);

        // Archive
        archive("Request with id " + requestId + " of requisition with id: "
                + requestDB.getWithdraw().getId() + " was changed. New number of required samples is: "
                + requestDB.getNumOfRequested(), loggedUserId);

        return true;
    }


    @Transactional(readOnly = true)
    public Request get(Long id) {
        notNull(id);
        return requestDao.get(id);
    }

}
