package cz.bbmri.service.impl;

import cz.bbmri.dao.NotificationDao;
import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.service.SampleQuestionService;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("sampleQuestionService")
class SampleQuestionServiceImpl extends BasicServiceImpl implements SampleQuestionService {

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotificationDao notificationDao;

    public boolean remove(Long id, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);
        if (isNull(id, "id", null)) return false;
        if (isNull(loggedUserId, "loggedUserId", null)) return false;

        SampleQuestion sampleQuestionDB = sampleQuestionDao.get(id);
        if (isNull(sampleQuestionDB, "sampleQuestionDB", null)) return false;

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

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.deleted",
                sampleQuestionDB.getId());


        // No need to inform user about delete of his sampleReservation  - only sampleRequest
        if (sampleQuestionDB instanceof SampleRequest) {
            notificationDao.create(getOtherProjectWorkers(((SampleRequest) sampleQuestionDB).getProject(), loggedUserId),
                    NotificationType.PROJECT_DETAIL, locMsg, ((SampleRequest) sampleQuestionDB).getProject().getId());

        }

        sampleQuestionDao.remove(sampleQuestionDB);
        return true;
    }

    public SampleQuestion get(Long sampleQuestionId) {
        if (isNull(sampleQuestionId, "sampleQuestionId", null)) return null;
        return sampleQuestionDao.get(sampleQuestionId);
    }

    public boolean approve(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(sampleQuestionId, "sampleQuestionId", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        SampleQuestion sampleQuestionDB = get(sampleQuestionId);

        if (isNull(sampleQuestionDB, "sampleQuestionDB", errors)) return false;

        // only new request can be denied
        if (!sampleQuestionDB.getRequestState().equals(RequestState.NEW)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        // here is no need to send notification - if approved that biobank administrator must prepare sample set
        // if sample set is choosen than change processed to true and send message to project team

        sampleQuestionDB.setRequestState(RequestState.APPROVED);
        sampleQuestionDB.setLastModification(new Date());
        sampleQuestionDao.update(sampleQuestionDB);

        return true;

    }

    public boolean deny(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(sampleQuestionId, "sampleQuestionId", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        SampleQuestion sampleQuestionDB = get(sampleQuestionId);

        if (isNull(sampleQuestionDB, "sampleQuestionDB", errors)) return false;

        // only new request can be denied
        if (!sampleQuestionDB.getRequestState().equals(RequestState.NEW)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        sampleQuestionDB.setRequestState(RequestState.DENIED);
        sampleQuestionDB.setLastModification(new Date());
        sampleQuestionDao.update(sampleQuestionDB);


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

        return true;
    }

}
