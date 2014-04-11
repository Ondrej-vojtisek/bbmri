package cz.bbmri.facade.impl;

import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.facade.RequestFacade;
import cz.bbmri.facade.exceptions.InsuficientAmountOfSamplesException;
import cz.bbmri.service.NotificationService;
import cz.bbmri.service.RequestService;
import cz.bbmri.service.SampleQuestionService;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private SampleQuestionService sampleQuestionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    public SampleQuestion getSampleQuestion(Long sampleQuestionId) {
        return sampleQuestionService.get(sampleQuestionId);
    }

    public boolean approveSampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {
        notNull(sampleQuestionId);
        notNull(loggedUserId);

        SampleQuestion sampleQuestionDB = sampleQuestionService.get(sampleQuestionId);

        if (sampleQuestionDB == null) {
            logger.debug("SampleRequestDB can't be null");
            return false;
        }

        if (!sampleQuestionDB.getRequestState().equals(RequestState.NEW)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantApproveThisRequestState"));
            return false;
        }

        // here is no need to send notification - if approved that biobank administrator must prepare sample set
        // if sample set is choosen than change processed to true and send message to project team

        sampleQuestionDB.setRequestState(RequestState.APPROVED);
        sampleQuestionService.update(sampleQuestionDB);

        return true;

    }

    public boolean denySampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {

        notNull(sampleQuestionId);
        notNull(loggedUserId);

        SampleQuestion sampleQuestionDB = sampleQuestionService.get(sampleQuestionId);

        if (sampleQuestionDB == null) {
            logger.debug("SampleRequestDB can't be null");
            return false;
        }

        if (!sampleQuestionDB.getRequestState().equals(RequestState.NEW)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        sampleQuestionDB.setRequestState(RequestState.DENIED);
        sampleQuestionService.update(sampleQuestionDB);

        boolean result = sampleQuestionService.update(sampleQuestionDB) != null;
        if (result) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.changedState",
                          sampleQuestionDB.getId(), RequestState.DENIED);

            // Notification for all users involved in project

            if (sampleQuestionDB instanceof SampleRequest) {
                notificationService.create(getOtherProjectWorkers(((SampleRequest) sampleQuestionDB).getProject(), loggedUserId),
                        NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
            }

            // Notification for the user who etnered the reservation

            else if (sampleQuestionDB instanceof SampleReservation) {
                notificationService.create(loggedUserId, NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
            }
        }

        return result;
    }


    /* Sample set is prepared - e.g. samples were choosen and now the project worker must decide if it is suitable
    * sample set for hos research
    * */
    public boolean closeSampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {
        notNull(sampleQuestionId);
        notNull(loggedUserId);

        SampleQuestion sampleQuestionDB = sampleQuestionService.get(sampleQuestionId);

        if (sampleQuestionDB == null) {
            logger.debug("SampleRequestDB can't be null");
            return false;
        }

        if (!sampleQuestionDB.getRequestState().equals(RequestState.APPROVED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantCloseThisRequestState"));
            return false;
        }

        sampleQuestionDB.setRequestState(RequestState.CLOSED);

        if (sampleQuestionService.update(sampleQuestionDB) == null) {
            return false;
        }

        if (sampleQuestionDB instanceof SampleRequest) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.changedState",
                                    sampleQuestionDB.getId(), RequestState.CLOSED);

            notificationService.create(getOtherProjectWorkers(((SampleRequest) sampleQuestionDB).getProject(),
                    loggedUserId),
                    NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
        }

        if (sampleQuestionDB instanceof SampleReservation) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.reservationChangedState",
                                             sampleQuestionDB.getId(), RequestState.CLOSED);

            notificationService.create(loggedUserId, NotificationType.SAMPLE_REQUEST_DETAIL, locMsg,
                    sampleQuestionDB.getId());
        }

        return true;

    }

    /* Action of project administrator - decision if the sample set is suitable or not
    * */
    public boolean confirmChosenSet(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {
        notNull(sampleQuestionId);
        notNull(loggedUserId);

        SampleQuestion sampleQuestionDB = sampleQuestionService.get(sampleQuestionId);

        if (sampleQuestionDB == null) {
            logger.debug("SampleRequestDB can't be null");
            return false;
        }

        if (!sampleQuestionDB.getRequestState().equals(RequestState.CLOSED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantConfirmThisRequestState"));
            return false;
        }

        // Not instanceof SampleRequest

        if (!(sampleQuestionDB instanceof SampleRequest)) {
            logger.debug("SampleQuestionDB must be instance of SampleRequest");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        User loggedUser = userService.get(loggedUserId);

        if (loggedUser == null) {
            logger.debug("LoggedUser is null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        sampleQuestionDB.setRequestState(RequestState.AGREED);
        boolean result = sampleQuestionService.update(sampleQuestionDB) != null;
        if (result) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.confirmed",
                                                       sampleQuestionDB.getId(), RequestState.CLOSED);

            notificationService.create(getOtherBiobankAdministrators(sampleQuestionDB.getBiobank(), null),
                    NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
        }

        return result;

    }

    /* Action of project administrator - decision if the sample set is suitable or not
        * */
    public boolean denyChosenSet(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {

        SampleQuestion sampleQuestionDB = sampleQuestionService.get(sampleQuestionId);

        if (sampleQuestionDB == null) {
            logger.debug("SampleQuestionDB can't be null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        if (!sampleQuestionDB.getRequestState().equals(RequestState.CLOSED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        // Not instanceof SampleRequest

        if (!(sampleQuestionDB instanceof SampleRequest)) {
            logger.debug("SampleQuestionDB must be instance of SampleRequest");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        User loggedUser = userService.get(loggedUserId);

        if (loggedUser == null) {
            logger.debug("LoggedUser is null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        sampleQuestionDB.setRequestState(RequestState.APPROVED);
        boolean result = sampleQuestionService.update(sampleQuestionDB) != null;
        if (result) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.denied",
                                                       sampleQuestionDB.getId(), RequestState.CLOSED);

            notificationService.create(getOtherBiobankAdministrators(sampleQuestionDB.getBiobank(), null),
                    NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
        }

        return result;

    }

    /* Action of project administrator - decision if the sample set is suitable or not
      * */
    public boolean setAsDelivered(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {

        SampleQuestion sampleQuestionDB = sampleQuestionService.get(sampleQuestionId);

        if (sampleQuestionDB == null) {
            logger.debug("SampleRequestDB can't be null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantSetAsDelivered"));
            return false;
        }

        if (!sampleQuestionDB.getRequestState().equals(RequestState.CLOSED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantSetAsDelivered"));
            return false;
        }

        // Not instanceof SampleRequest

        if (!(sampleQuestionDB instanceof SampleRequest)) {
            logger.debug("SampleQuestionDB must be instance of SampleRequest");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        User loggedUser = userService.get(loggedUserId);

        if (loggedUser == null) {
            logger.debug("LoggedUser is null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantSetAsDelivered"));
            return false;
        }

        sampleQuestionDB.setRequestState(RequestState.DELIVERED);
        boolean result = sampleQuestionService.update(sampleQuestionDB) != null;
        if (result) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.changedState",
                                    sampleQuestionDB.getId(), RequestState.DELIVERED);

            notificationService.create(getOtherProjectWorkers(((SampleRequest) sampleQuestionDB).getProject(), null),
                    NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
        }

        return result;

    }


    public boolean deleteSampleQuestion(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {

        notNull(sampleQuestionId);
        notNull(loggedUserId);

        SampleQuestion sampleQuestionDB = sampleQuestionService.get(sampleQuestionId);

        if (!sampleQuestionService.remove(sampleQuestionId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDelete"));
            return false;
        }

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.deleted",
                                 sampleQuestionDB.getId());


        if (sampleQuestionDB instanceof SampleRequest) {
            notificationService.create(getOtherProjectWorkers(((SampleRequest) sampleQuestionDB).getProject(), loggedUserId),
                    NotificationType.PROJECT_DETAIL, locMsg, ((SampleRequest) sampleQuestionDB).getProject().getId());

        }

        // Reservation is my own - there is no need to send notification

        return true;
    }

    public List<SampleQuestion> getNewSampleRequests(Long biobankId) {
        return sampleQuestionService.getSampleRequests(biobankId, RequestState.NEW);
    }

    public boolean createRequests(List<Long> sampleIds, Long sampleQuestionId, ValidationErrors errors, List<Message> messages) {

        int result = -1;
        try {
            result = requestService.createRequests(sampleIds, sampleQuestionId);
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

    public List<SampleReservation> getSampleReservations(Long userId) {
        notNull(userId);

        User userDB = userService.get(userId);
        //   User userDB = userService.eagerGet(userId, false, false, false, false, true);

        return userDB.getSampleReservations();
    }

    public boolean createSampleRequest(SampleRequest sampleRequest, Long projectId, Long biobankId,
                                       ValidationErrors errors) {

        if (sampleQuestionService.create(sampleRequest, biobankId, projectId) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.createSampleRequestFailed"));
            return false;
        }
        return true;
    }

    public boolean createSampleQuestion(SampleQuestion sampleQuestion, Long biobankId,
                                        ValidationErrors errors) {

        if (sampleQuestionService.create(sampleQuestion, biobankId) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.createSampleReservationFailed"));
            return false;
        }
        return true;
    }

    public boolean assignReservationToProject(Long sampleQuestionId, Long projectId, ValidationErrors errors) {


        if (!sampleQuestionService.assignReservationToProject(sampleQuestionId, projectId)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.assignReservationToProjectFailed"));
            return false;
        }

        return true;
    }


    public List<SampleQuestion> getSortedSampleQuestions(Long biobankId, String orderByParam, boolean desc) {
        if (biobankId == null) {
            logger.debug("BiobankId can't be null");
            return null;
        }
        return sampleQuestionService.getSortedSampleQuestions(biobankId, orderByParam, desc);
    }

    public List<SampleRequest> getSortedSampleRequest(Long projectId, String orderByParam, boolean desc){
        return sampleQuestionService.getSortedSampleRequests(projectId, orderByParam, desc);
    }

    public List<SampleReservation> getSortedSampleReservation(Long userId, String orderByParam, boolean desc){
        return sampleQuestionService.getSortedSampleReservations(userId, orderByParam, desc);
    }
}
