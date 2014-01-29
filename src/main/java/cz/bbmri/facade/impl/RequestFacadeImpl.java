package cz.bbmri.facade.impl;

import cz.bbmri.entities.Project;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.facade.RequestFacade;
import cz.bbmri.service.NotificationService;
import cz.bbmri.service.SampleQuestionService;
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
    private SampleQuestionService sampleQuestionService;

    @Autowired
    private NotificationService notificationService;

    public SampleQuestion getSampleQuestion(Long sampleQuestionId) {
        return sampleQuestionService.get(sampleQuestionId);
    }

    public boolean approveSampleQuestion(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {
        notNull(sampleQuestionId);
        notNull(loggedUserId);

        SampleQuestion sampleQuestionDB = sampleQuestionService.get(sampleQuestionId);

        if (sampleQuestionDB == null) {
            logger.debug("SampleQuestionDB can't be null");
            return false;
        }

        if (!sampleQuestionDB.getRequestState().equals(RequestState.NEW)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantApproveThisRequestState"));
            return false;
        }

        // here is no need to send notification - if approved that biobank administrator must prepare sample set
        // if sample set is choosen than change processed to true and send message to project team

        sampleQuestionDB.setRequestState(RequestState.APPROVED);

        return true;

    }

    public boolean denySampleQuestion(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId) {

        notNull(sampleQuestionId);
          notNull(loggedUserId);

          SampleQuestion sampleQuestionDB = sampleQuestionService.get(sampleQuestionId);

          if (sampleQuestionDB == null) {
              logger.debug("SampleQuestionDB can't be null");
              return false;
          }

          if (!sampleQuestionDB.getRequestState().equals(RequestState.NEW)) {
              errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.RequestFacadeImpl.cantApproveThisRequestState"));
              return false;
          }

          sampleQuestionDB.setRequestState(RequestState.DENIED);

          boolean result = sampleQuestionService.update(sampleQuestionDB) != null;
          if (result) {

              String msg = "Sample request with id: " + sampleQuestionDB.getId() +
                      " was denied";

              notificationService.create(getOtherProjectWorkers(sampleQuestionDB.getProject(), loggedUserId),
                      NotificationType.SAMPLE_QUESTION_DETAIL, msg, sampleQuestionDB.getId());
          }

          return result;
    }
}
