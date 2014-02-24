package cz.bbmri.facade.impl;

import cz.bbmri.entities.Request;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.facade.RequestFacade;
import cz.bbmri.facade.exceptions.InsuficientAmountOfSamplesException;
import cz.bbmri.service.NotificationService;
import cz.bbmri.service.RequestService;
import cz.bbmri.service.SampleRequestService;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.1.14
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
@Controller("requestFacade")
public class RequestFacadeImpl extends BasicFacade implements RequestFacade {

    @Autowired
    private SampleRequestService sampleRequestService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    public SampleRequest getSampleRequest(Long sampleRequestId) {
        return sampleRequestService.get(sampleRequestId);
    }

    public boolean approveSampleRequest(Long sampleRequestId, ValidationErrors errors, Long loggedUserId) {
        notNull(sampleRequestId);
        notNull(loggedUserId);

        SampleRequest sampleRequestDB = sampleRequestService.get(sampleRequestId);

        if (sampleRequestDB == null) {
            logger.debug("SampleRequestDB can't be null");
            return false;
        }

        if (!sampleRequestDB.getRequestState().equals(RequestState.NEW)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantApproveThisRequestState"));
            return false;
        }

        // here is no need to send notification - if approved that biobank administrator must prepare sample set
        // if sample set is choosen than change processed to true and send message to project team

        sampleRequestDB.setRequestState(RequestState.APPROVED);
        sampleRequestService.update(sampleRequestDB);

        return true;

    }

    public boolean denySampleRequest(Long sampleRequestId, ValidationErrors errors, Long loggedUserId) {

        notNull(sampleRequestId);
        notNull(loggedUserId);

        SampleRequest sampleRequestDB = sampleRequestService.get(sampleRequestId);

        if (sampleRequestDB == null) {
            logger.debug("SampleRequestDB can't be null");
            return false;
        }

        if (!sampleRequestDB.getRequestState().equals(RequestState.NEW)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantApproveThisRequestState"));
            return false;
        }

        sampleRequestDB.setRequestState(RequestState.DENIED);
        sampleRequestService.update(sampleRequestDB);

        boolean result = sampleRequestService.update(sampleRequestDB) != null;
        if (result) {

            String msg = "Sample request with id: " + sampleRequestDB.getId() +
                    " was denied";

            notificationService.create(getOtherProjectWorkers(sampleRequestDB.getProject(), loggedUserId),
                    NotificationType.SAMPLE_REQUEST_DETAIL, msg, sampleRequestDB.getId());
        }

        return result;
    }


    /* Sample set is prepared - e.g. samples were choosen and now the project worker must decide if it is suitable
    * sample set for hos research
    * */
    public boolean closeSampleRequest(Long sampleRequestId, ValidationErrors errors, Long loggedUserId) {
        notNull(sampleRequestId);
        notNull(loggedUserId);

        SampleRequest sampleRequestDB = sampleRequestService.get(sampleRequestId);

        if (sampleRequestDB == null) {
            logger.debug("SampleRequestDB can't be null");
            return false;
        }

        if (!sampleRequestDB.getRequestState().equals(RequestState.APPROVED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantCloseThisRequestState"));
            return false;
        }

        sampleRequestDB.setRequestState(RequestState.CLOSED);
        boolean result = sampleRequestService.update(sampleRequestDB) != null;
        if (result) {

            String msg = "Sample request with id: " + sampleRequestDB.getId() +
                    " is closed. Check if sample set suits your requirements. ";

            notificationService.create(getOtherProjectWorkers(sampleRequestDB.getProject(), loggedUserId),
                    NotificationType.SAMPLE_REQUEST_DETAIL, msg, sampleRequestDB.getId());
        }

        return result;

    }

    /* Action of project administrator - decision if the sample set is suitable or not
    * */
    public boolean confirmChosenSet(Long sampleRequestId, ValidationErrors errors, Long loggedUserId) {
        notNull(sampleRequestId);
        notNull(loggedUserId);

        SampleRequest sampleRequestDB = sampleRequestService.get(sampleRequestId);

        if (sampleRequestDB == null) {
            logger.debug("SampleRequestDB can't be null");
            return false;
        }

        if (!sampleRequestDB.getRequestState().equals(RequestState.CLOSED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantConfirmThisRequestState"));
            return false;
        }

        User loggedUser = userService.get(loggedUserId);

        if (loggedUser == null) {
            logger.debug("LoggedUser is null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        sampleRequestDB.setRequestState(RequestState.AGREED);
        boolean result = sampleRequestService.update(sampleRequestDB) != null;
        if (result) {

            String msg = "Sample request with id: " + sampleRequestDB.getId() +
                    " was agreed by project administrator " + loggedUser.getWholeName() + ". Please prepare set of samples. ";

            notificationService.create(getOtherBiobankAdministrators(sampleRequestDB.getBiobank(), null),
                    NotificationType.SAMPLE_REQUEST_DETAIL, msg, sampleRequestDB.getId());
        }

        return result;

    }

    /* Action of project administrator - decision if the sample set is suitable or not
        * */
    public boolean denyChosenSet(Long sampleRequestId, ValidationErrors errors, Long loggedUserId) {

        SampleRequest sampleRequestDB = sampleRequestService.get(sampleRequestId);

        if (sampleRequestDB == null) {
            logger.debug("SampleRequestDB can't be null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        if (!sampleRequestDB.getRequestState().equals(RequestState.CLOSED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        User loggedUser = userService.get(loggedUserId);

        if (loggedUser == null) {
            logger.debug("LoggedUser is null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        sampleRequestDB.setRequestState(RequestState.APPROVED);
        boolean result = sampleRequestService.update(sampleRequestDB) != null;
        if (result) {

            String msg = "Sample request with id: " + sampleRequestDB.getId() +
                    " wasn't confirmed by project administrator " + loggedUser.getWholeName() + ". Please contant him:" +
                    " on " + loggedUser.getEmail();

            notificationService.create(getOtherBiobankAdministrators(sampleRequestDB.getBiobank(), null),
                    NotificationType.SAMPLE_REQUEST_DETAIL, msg, sampleRequestDB.getId());
        }

        return result;

    }

    /* Action of project administrator - decision if the sample set is suitable or not
      * */
    public boolean setAsDelivered(Long sampleRequestId, ValidationErrors errors, Long loggedUserId) {

        SampleRequest sampleRequestDB = sampleRequestService.get(sampleRequestId);

        if (sampleRequestDB == null) {
            logger.debug("SampleRequestDB can't be null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantSetAsDelivered"));
            return false;
        }

        if (!sampleRequestDB.getRequestState().equals(RequestState.CLOSED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantSetAsDelivered"));
            return false;
        }

        User loggedUser = userService.get(loggedUserId);

        if (loggedUser == null) {
            logger.debug("LoggedUser is null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantSetAsDelivered"));
            return false;
        }

        sampleRequestDB.setRequestState(RequestState.DELIVERED);
        boolean result = sampleRequestService.update(sampleRequestDB) != null;
        if (result) {

            String msg = "Sample request with id: " + sampleRequestDB.getId() +
                    " was delivered by: " + loggedUser.getWholeName();

            notificationService.create(getOtherProjectWorkers(sampleRequestDB.getProject(), null),
                    NotificationType.SAMPLE_REQUEST_DETAIL, msg, sampleRequestDB.getId());
        }

        return result;

    }


    public boolean deleteSampleRequest(Long sampleRequestId, ValidationErrors errors, Long loggedUserId) {

        notNull(sampleRequestId);
        notNull(loggedUserId);

        SampleRequest sampleRequestDB = sampleRequestService.get(sampleRequestId);

        if (!sampleRequestService.remove(sampleRequestId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDelete"));
            return false;
        }

        String msg = "Sample request with id: " + sampleRequestDB.getId() +
                " was deleted";

        notificationService.create(getOtherProjectWorkers(sampleRequestDB.getProject(), loggedUserId),
                NotificationType.PROJECT_DETAIL, msg, sampleRequestDB.getProject().getId());

        return true;
    }

    public List<SampleRequest> getNewSampleRequests(Long biobankId) {
        return sampleRequestService.getByBiobankAndState(biobankId, RequestState.NEW);
    }

    public boolean createRequests(List<Long> sampleIds, Long sampleRequestId, ValidationErrors errors, List<Message> messages) {
        logger.debug("Facade - createRequests");
        logger.debug("SampleIds: " + sampleIds);
        logger.debug("SampleRequestId: " + sampleRequestId);

        int result = -1;

        try {
            result = requestService.createRequests(sampleIds, sampleRequestId);
        } catch (InsuficientAmountOfSamplesException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.insufficientAvailableSamples"));
            return false;
        }

        if (result < 0) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.requestCreateFailed"));
            return false;
        }
        if (result == 0) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.noRequestWasCreated"));
            return true;
        }
        messages.add(new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.createRequestSuccess", result));
        return true;
    }

    public boolean changeRequestedAmount(Long requestId, boolean increase, int difference, ValidationErrors errors) {
        Request requestDB = requestService.get(requestId);
        if (requestDB == null) {
            logger.debug("RequestDB can't be null");
            return false;
        }

        int newValue = requestDB.getNumOfRequested();
        if (increase) {
            newValue += difference;

        } else {
            newValue -= difference;
        }

        requestDB.setNumOfRequested(newValue);

        if (requestService.update(requestDB) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.changeNumberFailed"));
            return false;
        }
        return true;
    }

    public boolean deleteRequest(Long requestId, ValidationErrors errors) {
        if (!requestService.remove(requestId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.failedToRemoveRequest"));
            return false;
        }
        return true;
    }


}
