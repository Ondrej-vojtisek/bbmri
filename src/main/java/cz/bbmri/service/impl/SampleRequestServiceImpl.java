package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.NotificationDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.dao.SampleQuestionDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.service.SampleRequestService;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Transactional
@Service("sampleRequestService")
public class SampleRequestServiceImpl extends SampleQuestionServiceImpl implements SampleRequestService {

    @Autowired
    private SampleQuestionDao sampleQuestionDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private NotificationDao notificationDao;


    public boolean createSampleRequest(SampleRequest sampleRequest, Long projectId, Long biobankId,
                                       ValidationErrors errors) {

        notNull(errors);
        if (isNull(sampleRequest, "sampleRequest", errors)) return false;
        if (isNull(projectId, "projectId", errors)) return false;
        if (isNull(biobankId, "biobankId", errors)) return false;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", errors)) return false;

        Project projectDB = projectDao.get(projectId);
        if (isNull(projectDB, "projectDB", errors)) return false;

        sampleRequest.setRequestState(RequestState.NEW);
        sampleRequest.setLastModification(new Date());
        sampleRequest.setCreated(new Date());

        sampleRequest.setBiobank(biobankDB);
        sampleRequest.setProject(projectDB);
        try {
            sampleQuestionDao.create(sampleRequest);
        } catch (Exception ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.createSampleRequestFailed"));
        }

        return true;
    }

    @Transactional(readOnly = true)
    public List<SampleRequest> getSampleRequests(Long biobankId, RequestState requestState) {
        if (isNull(biobankId, "biobankId", null)) return null;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        // requestState CAN be null
        return sampleQuestionDao.getSampleRequests(biobankDB, requestState);
    }


    /* Action of project administrator - decision if the sample set is suitable or not
//    * */
    public boolean confirmChosenSet(Long sampleRequestId, ValidationErrors errors) {
        notNull(errors);

        if (isNull(sampleRequestId, "sampleQuestionId", null)) return false;

        SampleQuestion sampleRequestDB = get(sampleRequestId);
        if (isNull(sampleRequestDB, "sampleRequestDB", null)) return false;

        // only closed
        if (!sampleRequestDB.getRequestState().equals(RequestState.CLOSED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantConfirmThisRequestState"));
            return false;
        }

        sampleRequestDB.setRequestState(RequestState.AGREED);
        sampleRequestDB.setLastModification(new Date());
        sampleQuestionDao.update(sampleRequestDB);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.confirmed",
                sampleRequestId, RequestState.CLOSED);

        notificationDao.create(getOtherBiobankAdministrators(sampleRequestDB.getBiobank(), null),
                NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleRequestId);

        return true;

    }

    public SampleRequest get(Long id) {
        if (isNull(id, "id", null)) return null;
        SampleQuestion sampleQuestion = sampleQuestionDao.get(id);

        if (!(sampleQuestion instanceof SampleRequest)) return null;

        return (SampleRequest) sampleQuestionDao.get(id);
    }

    //    /* Sample set is prepared - e.g. samples were choosen and now the project worker must decide if it is suitable
    //    * sample set for hos research
    //    * */
    public boolean close(Long Id, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(Id, "Id", null)) return false;
        SampleRequest sampleRequestDB = get(Id);
        if (isNull(sampleRequestDB, "sampleRequestDB", null)) return false;

        // only approved
        if (!sampleRequestDB.getRequestState().equals(RequestState.APPROVED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantCloseThisRequestState"));
            return false;
        }

        sampleRequestDB.setRequestState(RequestState.CLOSED);
        sampleRequestDB.setLastModification(new Date());
        sampleQuestionDao.update(sampleRequestDB);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.changedState",
                sampleRequestDB.getId(), RequestState.CLOSED);

        notificationDao.create(getOtherProjectWorkers(sampleRequestDB.getProject(),
                loggedUserId),
                NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleRequestDB.getId());

        return true;

    }


    //    /* Action of project administrator - decision if the sample set is suitable or not
    //        * */
    public boolean denyChosenSet(Long sampleRequestId, ValidationErrors errors) {
        notNull(errors);

        if (isNull(sampleRequestId, "sampleRequestId", null)) return false;
        SampleRequest sampleRequestDB = get(sampleRequestId);
        if (isNull(sampleRequestDB, "sampleRequestDB", null)) return false;

        // Only closed
        if (!sampleRequestDB.getRequestState().equals(RequestState.CLOSED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantDenyThisRequestState"));
            return false;
        }

        sampleRequestDB.setRequestState(RequestState.APPROVED);
        sampleRequestDB.setLastModification(new Date());
        sampleQuestionDao.update(sampleRequestDB);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.denied",
                sampleRequestDB.getId(), RequestState.APPROVED);
        // send message to biobankAdministrators
        notificationDao.create(getOtherBiobankAdministrators(sampleRequestDB.getBiobank(), null),
                NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleRequestDB.getId());

        return true;

    }

    //    /* Action of project administrator - decision if the sample set is suitable or not
    //      * */
    public boolean setAsDelivered(Long sampleRequestId, ValidationErrors errors) {
        notNull(errors);

        if (isNull(sampleRequestId, "sampleRequestId", null)) return false;
        SampleRequest sampleRequestDB = get(sampleRequestId);
        if (isNull(sampleRequestDB, "sampleRequestDB", null)) return false;

        // Only closed
        if (!sampleRequestDB.getRequestState().equals(RequestState.CLOSED)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantSetAsDelivered"));
            return false;
        }

        sampleRequestDB.setRequestState(RequestState.DELIVERED);
        sampleRequestDB.setLastModification(new Date());
        sampleQuestionDao.update(sampleRequestDB);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.RequestFacadeImpl.changedState",
                sampleRequestId, RequestState.DELIVERED);

        notificationDao.create(getOtherProjectWorkers(sampleRequestDB.getProject(), null),
                NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleRequestId);
        return true;
    }

    public List<SampleRequest> getSortedSampleRequest(Long projectId, String orderByParam, boolean desc) {
        if (isNull(projectId, "projectId", null)) return null;
        Project projectDB = projectDao.get(projectId);
        if (isNull(projectDB, "projectDB", null)) return null;
        return sampleQuestionDao.getSampleRequestsSorted(projectDB, orderByParam, desc);
    }

}
