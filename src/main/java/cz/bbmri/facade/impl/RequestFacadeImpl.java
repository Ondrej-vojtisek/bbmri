package cz.bbmri.facade.impl;

import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.facade.RequestFacade;
import cz.bbmri.service.NotificationService;
import cz.bbmri.service.SampleRequestService;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

        boolean result = sampleRequestService.update(sampleRequestDB) != null;
        if (result) {

            String msg = "Sample request with id: " + sampleRequestDB.getId() +
                    " was denied";

            notificationService.create(getOtherProjectWorkers(sampleRequestDB.getProject(), loggedUserId),
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
}
