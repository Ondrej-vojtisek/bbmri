package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.service.SampleQuestionService;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("sampleRequestService")
public class SampleQuestionServiceImpl extends BasicServiceImpl implements SampleQuestionService {

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RequestDao requestDao;

    @Autowired
    private SampleDao sampleDao;

    @Autowired
    private NotificationDao notificationDao;


        public boolean createSampleRequest(SampleRequest sampleRequest, Long projectId, Long biobankId,
                                           ValidationErrors errors) {

            if (create(sampleRequest, biobankId, projectId) == null) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.createSampleRequestFailed"));
                return false;
            }
            return true;
        }


        public boolean createSampleQuestion(SampleQuestion sampleQuestion, Long biobankId,
                                            ValidationErrors errors) {

            if (create(sampleQuestion, biobankId) == null) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.createSampleReservationFailed"));
                return false;
            }
            return true;
        }

    public SampleRequest create(SampleRequest sampleRequest, Long biobankId, Long projectId) {
        notNull(sampleRequest);
        notNull(biobankId);
        notNull(projectId);

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("biobankDB can't be null");
            return null;
        }
        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            logger.debug("projectDB can't be null");
            return null;
        }
        sampleRequest.setRequestState(RequestState.NEW);
        sampleRequest.setCreated(new Date());

        sampleRequest.setBiobank(biobankDB);
        sampleRequest.setProject(projectDB);
        sampleQuestionDao.update(sampleRequest);
        return sampleRequest;
    }

    public SampleQuestion create(SampleQuestion sampleQuestion, Long biobankId) {
        notNull(sampleQuestion);
        notNull(biobankId);

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("biobankDB can't be null");
            return null;
        }

        sampleQuestion.setRequestState(RequestState.NEW);
        sampleQuestion.setCreated(new Date());
        sampleQuestion.setBiobank(biobankDB);
        sampleQuestionDao.create(sampleQuestion);

        return sampleQuestion;
    }

    public boolean remove(Long id) {
        notNull(id);
        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(id);
        if (sampleQuestionDB == null) {
            return false;
        }

        if (sampleQuestionDB.getBiobank() != null) {
            sampleQuestionDB.setBiobank(null);
        }

        if (sampleQuestionDB instanceof SampleRequest) {
            if (((SampleRequest) sampleQuestionDB).getProject() != null) {
                ((SampleRequest) sampleQuestionDB).setProject(null);
            }
        }

        if (sampleQuestionDB instanceof SampleReservation) {
            if (((SampleReservation) sampleQuestionDB).getUser() != null) {
                ((SampleReservation) sampleQuestionDB).setUser(null);
            }
        }

        sampleQuestionDao.remove(sampleQuestionDB);
        return true;
    }

    public SampleQuestion update(SampleQuestion sampleQuestion) {
        if (sampleQuestion == null) {
            logger.debug("SampleQuestion can't be null");
            return null;
        }
        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(sampleQuestion.getId());

        if (sampleQuestionDB == null) {
            logger.debug("SampleQuestionDB can't be null");
            return null;
        }

        boolean changed = false;

        if (sampleQuestion.getRequestState() != null) {

            // set new state
            changed = true;
            sampleQuestionDB.setRequestState(sampleQuestion.getRequestState());

            // completition of reservation triggers validity for sample reservation

            if (sampleQuestion.getRequestState() == RequestState.CLOSED
                    && sampleQuestionDB instanceof SampleReservation) {
                setValidity(sampleQuestionDB);
            }
        }

        // only if sampleQuestion was changed
        if (changed) {
            sampleQuestionDB.setLastModification(new Date());
            sampleQuestionDao.update(sampleQuestionDB);
        }
        return sampleQuestion;
    }

    private void setValidity(SampleQuestion sampleQuestion) {
        if (sampleQuestion == null) return;

        if (!(sampleQuestion instanceof SampleReservation)) return;

        Date today = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        // add one month
        cal.add(Calendar.MONTH, 1);

        // Validity one month
        ((SampleReservation) sampleQuestion).setValidity(cal.getTime());
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> all() {
        return sampleQuestionDao.all();
    }

    @Transactional(readOnly = true)
    public SampleQuestion get(Long id) {
        return sampleQuestionDao.get(id);
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return sampleQuestionDao.count();
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> allOrderedBy(String orderByParam, boolean desc) {
        return sampleQuestionDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> getSampleRequests(Long biobankId, RequestState requestState) {
        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can't be null");
            return null;
        }

        // requestState CAN be null

        return sampleQuestionDao.getSampleRequests(biobankDB, requestState);
    }

    @Transactional(readOnly = true)
    public List<SampleQuestion> getSampleReservations(Long biobankId, RequestState requestState) {
        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can't be null");
            return null;
        }

        if (requestState == null) {
            logger.debug("RequestState can't be null");
            return null;
        }

        return sampleQuestionDao.getSampleReservations(biobankDB, requestState);
    }

    public boolean assignReservationToProject(Long sampleQuestionId, Long projectId) {
        if (sampleQuestionId == null) {
            logger.debug("question can't be null");
            return false;
        }

        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(sampleQuestionId);

        if (sampleQuestionDB == null) {
            logger.debug("sampleQuestionDB can't be null");
            return false;
        }

        Project projectDB = projectDao.get(projectId);

        if (projectDB == null) {
            logger.debug("Project can't be null");
            return false;
        }

        SampleRequest sampleRequest = new SampleRequest();

        // Set values from sampleReservation

        sampleRequest.setBiobank(sampleQuestionDB.getBiobank());
        sampleRequest.setCreated(sampleQuestionDB.getCreated());
        sampleRequest.setSpecification(sampleQuestionDB.getSpecification());
        sampleRequest.setRequestState(sampleQuestionDB.getRequestState());

        // Actual date
        sampleRequest.setLastModification(new Date());

        // Association to project
        sampleRequest.setProject(projectDB);

        sampleQuestionDao.create(sampleRequest);

        for (Request request : sampleQuestionDB.getRequests()) {
            request.setSampleQuestion(sampleRequest);
            requestDao.update(request);
        }

        // Delete sampleReservation

        sampleQuestionDB.setBiobank(null);
        sampleQuestionDao.remove(sampleQuestionDB);

        return true;
    }

    public List<SampleReservation> getSampleReservationsOrderedByDate() {
        return sampleQuestionDao.getSampleReservationsOrderedByDate();
    }


    public List<SampleQuestion> getSortedSampleQuestions(Long biobankId, String orderByParam, boolean desc) {
        if (biobankId == null) {
            logger.debug("biobankId is null");
            return null;
        }

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can´t be null");
            return null;
        }

        return sampleQuestionDao.getSortedSampleQuestions(biobankDB, orderByParam, desc);
    }


    public List<SampleRequest> getSortedSampleRequests(Long projectId, String orderByParam, boolean desc) {
        if (projectId == null) {
            logger.debug("projectId is null");
            return null;
        }

        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            logger.debug("projectDB can´t be null");
            return null;
        }

        return sampleQuestionDao.getSampleRequestsSorted(projectDB, orderByParam, desc);
    }

    public List<SampleReservation> getSortedSampleReservations(Long userId, String orderByParam, boolean desc) {
        if (userId == null) {
            logger.debug("userId is null");
            return null;
        }

        User userDB = userDao.get(userId);
        if (userDB == null) {
            logger.debug("userDB can´t be null");
            return null;
        }

        return sampleQuestionDao.getSampleReservationsSorted(userDB, orderByParam, desc);
    }

    public List<SampleReservation> getSampleReservationsBySample(Long sampleId, String orderByParam, boolean desc) {
        if (sampleId == null) {
            logger.debug("sampleId is null");
            return null;
        }

        Sample sampleDB = sampleDao.get(sampleId);
        if (sampleDB == null) {
            logger.debug("sampleDB can´t be null");
            return null;
        }

        return sampleQuestionDao.getSampleReservationsBySample(sampleDB, orderByParam, desc);
    }

        public boolean denySampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {

            notNull(sampleQuestionId);
            notNull(loggedUserId);

            SampleQuestion sampleQuestionDB = get(sampleQuestionId);

            if (sampleQuestionDB == null) {
                logger.debug("SampleRequestDB can't be null");
                return false;
            }

            if (!sampleQuestionDB.getRequestState().equals(RequestState.NEW)) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
                return false;
            }

            sampleQuestionDB.setRequestState(RequestState.DENIED);
            update(sampleQuestionDB);

            boolean result = update(sampleQuestionDB) != null;
            if (result) {

                LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.changedState",
                              sampleQuestionDB.getId(), RequestState.DENIED);

                // Notification for all users involved in project

                if (sampleQuestionDB instanceof SampleRequest) {
                    notificationDao.create(getOtherProjectWorkers(((SampleRequest) sampleQuestionDB).getProject(), loggedUserId),
                            NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
                }

                // Notification for the user who etnered the reservation

                else if (sampleQuestionDB instanceof SampleReservation) {
                    notificationDao.create(userDao.get(loggedUserId), NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
                }
            }

            return result;
        }

     /* Action of project administrator - decision if the sample set is suitable or not
//    * */
    public boolean confirmChosenSet(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {
        notNull(sampleQuestionId);
        notNull(loggedUserId);

        SampleQuestion sampleQuestionDB = get(sampleQuestionId);

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

        User loggedUser = userDao.get(loggedUserId);

        if (loggedUser == null) {
            logger.debug("LoggedUser is null");
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        sampleQuestionDB.setRequestState(RequestState.AGREED);
        boolean result = update(sampleQuestionDB) != null;
        if (result) {

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.confirmed",
                                                       sampleQuestionDB.getId(), RequestState.CLOSED);

            notificationDao.create(getOtherBiobankAdministrators(sampleQuestionDB.getBiobank(), null),
                    NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
        }

        return result;

    }

        public boolean approveSampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {
            notNull(sampleQuestionId);
            notNull(loggedUserId);

            SampleQuestion sampleQuestionDB = get(sampleQuestionId);

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
            update(sampleQuestionDB);

            return true;

        }

    //    /* Sample set is prepared - e.g. samples were choosen and now the project worker must decide if it is suitable
    //    * sample set for hos research
    //    * */
        public boolean closeSampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {
            notNull(sampleQuestionId);
            notNull(loggedUserId);

            SampleQuestion sampleQuestionDB = get(sampleQuestionId);

            if (sampleQuestionDB == null) {
                logger.debug("SampleRequestDB can't be null");
                return false;
            }

            if (!sampleQuestionDB.getRequestState().equals(RequestState.APPROVED)) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantCloseThisRequestState"));
                return false;
            }

            sampleQuestionDB.setRequestState(RequestState.CLOSED);

            if (update(sampleQuestionDB) == null) {
                return false;
            }

            if (sampleQuestionDB instanceof SampleRequest) {

                LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.changedState",
                                        sampleQuestionDB.getId(), RequestState.CLOSED);

                notificationDao.create(getOtherProjectWorkers(((SampleRequest) sampleQuestionDB).getProject(),
                        loggedUserId),
                        NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
            }

            if (sampleQuestionDB instanceof SampleReservation) {

                LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.reservationChangedState",
                                                 sampleQuestionDB.getId(), RequestState.CLOSED);

                notificationDao.create(userDao.get(loggedUserId), NotificationType.SAMPLE_REQUEST_DETAIL, locMsg,
                        sampleQuestionDB.getId());
            }

            return true;

        }


    //    /* Action of project administrator - decision if the sample set is suitable or not
    //        * */
        public boolean denyChosenSet(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {

            SampleQuestion sampleQuestionDB = get(sampleQuestionId);

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

            User loggedUser = userDao.get(loggedUserId);

            if (loggedUser == null) {
                logger.debug("LoggedUser is null");
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
                return false;
            }

            sampleQuestionDB.setRequestState(RequestState.APPROVED);
            boolean result = update(sampleQuestionDB) != null;
            if (result) {

                LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.denied",
                                                           sampleQuestionDB.getId(), RequestState.CLOSED);

                notificationDao.create(getOtherBiobankAdministrators(sampleQuestionDB.getBiobank(), null),
                        NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
            }

            return result;

        }

    //    /* Action of project administrator - decision if the sample set is suitable or not
    //      * */
        public boolean setAsDelivered(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {

            SampleQuestion sampleQuestionDB = get(sampleQuestionId);

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

            User loggedUser = userDao.get(loggedUserId);

            if (loggedUser == null) {
                logger.debug("LoggedUser is null");
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantSetAsDelivered"));
                return false;
            }

            sampleQuestionDB.setRequestState(RequestState.DELIVERED);
            boolean result = update(sampleQuestionDB) != null;
            if (result) {

                LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.changedState",
                                        sampleQuestionDB.getId(), RequestState.DELIVERED);

                notificationDao.create(getOtherProjectWorkers(((SampleRequest) sampleQuestionDB).getProject(), null),
                        NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleQuestionDB.getId());
            }

            return result;

        }

        public boolean assignReservationToProject(Long sampleQuestionId, Long projectId, ValidationErrors errors) {


            if (!assignReservationToProject(sampleQuestionId, projectId)) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.assignReservationToProjectFailed"));
                return false;
            }

            return true;
        }

        public boolean deleteSampleQuestion(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {

            notNull(sampleQuestionId);
            notNull(loggedUserId);

            SampleQuestion sampleQuestionDB = get(sampleQuestionId);

            if (!remove(sampleQuestionId)) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDelete"));
                return false;
            }

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.deleted",
                                     sampleQuestionDB.getId());


            if (sampleQuestionDB instanceof SampleRequest) {
                notificationDao.create(getOtherProjectWorkers(((SampleRequest) sampleQuestionDB).getProject(), loggedUserId),
                        NotificationType.PROJECT_DETAIL, locMsg, ((SampleRequest) sampleQuestionDB).getProject().getId());

            }

            // Reservation is my own - there is no need to send notification

            return true;
        }

        public List<SampleRequest> getSortedSampleRequest(Long projectId, String orderByParam, boolean desc){
            return getSortedSampleRequests(projectId, orderByParam, desc);
        }



}
