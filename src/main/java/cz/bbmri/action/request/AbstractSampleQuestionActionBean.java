package cz.bbmri.action.request;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.service.SampleQuestionService;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class AbstractSampleQuestionActionBean extends PermissionActionBean {

    @SpringBean
    private SampleQuestionService sampleQuestionService;

    private SampleQuestion sampleQuestion;

    /* SampleQuestion identifier */
    private Long sampleQuestionId;

    public Long getSampleQuestionId() {
        return sampleQuestionId;
    }

    public void setSampleQuestionId(Long sampleQuestionId) {
        this.sampleQuestionId = sampleQuestionId;
    }


    public boolean getIsSampleRequest() {

        if (getSampleQuestion() == null) {
            return false;
        }
        return sampleQuestion instanceof SampleRequest;
    }

    public boolean getIsSampleReservation() {

        if (getSampleQuestion() == null) {
            return false;
        }
        return sampleQuestion instanceof SampleReservation;
    }

    public SampleQuestion getSampleQuestion() {
        if (sampleQuestion == null) {

            if (sampleQuestionId != null) {
                sampleQuestion = sampleQuestionService.get(sampleQuestionId);

            }
        }
        return sampleQuestion;
    }

    public SampleRequest getSampleRequest() {
        if (getSampleQuestion() == null) {
            logger.debug("getSampleRequest - getSampleQuestion Null");
            return null;
        }

        if (!getIsSampleRequest()) {
            logger.debug("getSampleRequest - wrong type");
            return null;
        }

        return (SampleRequest) sampleQuestion;
    }

    public boolean getIsSampleQuestionNew() {
        if (getSampleQuestion() == null) {
            return false;
        }

        return getSampleQuestion().isNew();
    }

    public boolean getIsSampleQuestionApproved() {
        logger.debug("SampleQuestion_ Approved");
        if (getSampleQuestion() == null) {
            logger.debug("SampleQuestion_ " + null);
            return false;
        }
        logger.debug("SampleQuestion_ " + sampleQuestion.isApproved());
        return sampleQuestion.isApproved();
    }

    public boolean getIsSampleQuestionDenied() {
        logger.debug("SampleQuestion_ Denied");
        if (getSampleQuestion() == null) {
            return false;
        }
        logger.debug("SampleQuestion_ " + sampleQuestion.isDenied());
        return sampleQuestion.isDenied();
    }

    public boolean getIsSampleQuestionClosed() {
        logger.debug("SampleQuestion_ Closed");
        if (getSampleQuestion() == null) {
            return false;
        }
        return getSampleQuestion().isClosed();
    }

    public boolean getIsSampleQuestionAgreed() {
        logger.debug("SampleQuestion_ Agreed");

        if (getSampleQuestion() == null) {
            return false;
        }
        logger.debug("SampleQuestion_ " + sampleQuestion.isAgreed());
        return sampleQuestion.isAgreed();
    }


    public boolean getIsSampleQuestionDelivered() {
        if (getSampleQuestion() == null) {
            return false;
        }
        return sampleQuestion.isDelivered();
    }

}
