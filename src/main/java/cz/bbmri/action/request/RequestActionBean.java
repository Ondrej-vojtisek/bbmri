package cz.bbmri.action.request;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.entities.*;
import cz.bbmri.facade.BiobankFacade;
import cz.bbmri.facade.RequestFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.1.14
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */

@HttpCache(allow = false)
@UrlBinding("/samplerequest/{$event}/{biobankId}/{sampleQuestionId}")
public class RequestActionBean extends PermissionActionBean {

    private static final int STEP_INCREASE_REQUEST_AMOUNT = 1;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private RequestFacade requestFacade;

    @SpringBean
    private BiobankFacade biobankFacade;

    /* SampleQuestion identifier */
    private Long sampleQuestionId;

    private SampleQuestion sampleQuestion;

    private Long requestId;

    public boolean getIsSampleRequest() {
        logger.debug("IsSampleRequest");

        if (getSampleQuestion() == null) {
            return false;
        }
        return sampleQuestion instanceof SampleRequest;
    }

    public boolean getIsSampleReservation() {
        logger.debug("IsSampleReservation");

        if (getSampleQuestion() == null) {
            return false;
        }
        return sampleQuestion instanceof SampleReservation;
    }

    public Long getSampleQuestionId() {
        return sampleQuestionId;
    }

    public void setSampleQuestionId(Long sampleQuestionId) {
        this.sampleQuestionId = sampleQuestionId;
    }

    public SampleQuestion getSampleQuestion() {
        if (sampleQuestion == null) {

            if (sampleQuestionId != null) {
                sampleQuestion = requestFacade.getSampleQuestion(sampleQuestionId);

                // is isntanceof SampleRequest
                if (isSampleRequest()) {

                    // Project is set
                    if (((SampleRequest) sampleQuestion).getProject() != null) {
                        setProjectId(((SampleRequest) sampleQuestion).getProject().getId());
                    }
                }

            }
        }
        return sampleQuestion;
    }

    private boolean isSampleRequest() {
        if (sampleQuestion == null) return false;

        if (!(sampleQuestion instanceof SampleRequest)) {
            return false;
        }

        return true;
    }


    public SampleRequest getSampleRequest() {
        if (getSampleQuestion() == null) {
            logger.debug("getSampleRequest - getSampleQuestion Null");
            return null;
        }

        if (!isSampleRequest()) {
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

    public Set<Request> getRequests() {
        if (getSampleQuestion() == null) {
            return null;
        }
        return sampleQuestion.getRequests();
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    private Biobank biobank;


    public Biobank getBiobank() {
        if (biobank == null) {
            if (biobankId != null) {
                biobank = biobankFacade.get(projectId);
            }
        }
        return biobank;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer", "project_team_member", "biobank_operator"})
    public Resolution detail() {
        logger.debug("ProjectId1: " + projectId);

        getSampleQuestion();

        logger.debug("ProjectId2: " + projectId);
        return new ForwardResolution(REQUEST_DETAIL);
    }


    /* Here can be self approve - withdraw */
    @DontValidate
    @HandlesEvent("approve")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionNew}"})
    public Resolution approve() {
        if (!requestFacade.approveSampleRequest(sampleQuestionId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class)
                    .addParameter("sampleQuestionId", sampleQuestionId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(RequestActionBean.class)
                .addParameter("sampleQuestionId", sampleQuestionId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("deny")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionNew}"})
    public Resolution deny() {
        if (!requestFacade.denySampleRequest(sampleQuestionId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class)
                    .addParameter("sampleQuestionId", sampleQuestionId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(RequestActionBean.class)
                .addParameter("sampleQuestionId", sampleQuestionId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("delete")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor and isSampleQuestionNew}"})
    public Resolution delete() {
        if (!requestFacade.deleteSampleQuestion(sampleQuestionId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class)
                    .addParameter("sampleQuestionId", sampleQuestionId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(ProjectActionBean.class, "sampleRequests")
                .addParameter("projectId", projectId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("createRequests")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution createRequests() {

        return new ForwardResolution(RequestActionBean.class)
                .addParameter("sampleQuestionId", sampleQuestionId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("decreaseAmount")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionApproved}"})
    public Resolution decreaseAmount() {

        logger.debug("requestId: " + requestId);

        if (!requestFacade.changeRequestedAmount(requestId, false, STEP_INCREASE_REQUEST_AMOUNT,
                getContext().getValidationErrors())) {


            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleQuestionId", sampleQuestionId);
        }
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleQuestionId", sampleQuestionId);
    }

    @DontValidate
    @HandlesEvent("increaseAmount")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionApproved}"})
    public Resolution increaseAmount() {

        logger.debug("requestId: " + requestId);

        if (!requestFacade.changeRequestedAmount(requestId, true, STEP_INCREASE_REQUEST_AMOUNT,
                getContext().getValidationErrors())) {

            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleQuestionId", sampleQuestionId);
        }
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleQuestionId", sampleQuestionId);
    }

    @DontValidate
    @HandlesEvent("removeRequest")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionApproved}"})
    public Resolution removeRequest() {
        if (!requestFacade.deleteRequest(requestId, getContext().getValidationErrors())) {

            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleQuestionId", sampleQuestionId);
        }
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleQuestionId", sampleQuestionId);
    }

    @DontValidate
    @HandlesEvent("changeStateToClosed")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionApproved}"})
    public Resolution changeStateToClosed() {

        if (!requestFacade.closeSampleRequest(sampleQuestionId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleQuestionId", sampleQuestionId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleQuestionId", sampleQuestionId)
                .addParameter("biobankId", biobankId);
    }


    @DontValidate
    @HandlesEvent("confirmChosenSet")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor and isSampleQuestionClosed}"})
    public Resolution confirmChosenSet() {
        if (!requestFacade.confirmChosenSet(sampleQuestionId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleQuestionId", sampleQuestionId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleQuestionId", sampleQuestionId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("denyChosenSet")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor and isSampleQuestionClosed"})
    public Resolution denyChosenSet() {

        if (!requestFacade.denyChosenSet(sampleQuestionId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleQuestionId", sampleQuestionId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleQuestionId", sampleQuestionId)
                .addParameter("biobankId", biobankId);
    }


    @DontValidate
    @HandlesEvent("setAsDelivered")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionAgreed}"})
    public Resolution setAsDelivered() {

        if (!requestFacade.setAsDelivered(sampleQuestionId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleQuestionId", sampleQuestionId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleQuestionId", sampleQuestionId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("exportSampleList")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor and isSampleQuestionAgreed}"})
    public Resolution exportSampleList() {

        logger.debug("BiobankId: " + biobankId);
        logger.debug("sampleQuestionId: " + sampleQuestionId);

        return new ForwardResolution(REQUEST_EXPORT)
                .addParameter("sampleQuestionId", sampleQuestionId)
                .addParameter("biobankId", biobankId);

    }

    @DontValidate
    @HandlesEvent("asignToProject")
    @RolesAllowed({"project_team_member if ${isSampleQuestionClosed}"})
    public Resolution asignToProject() {
        return new ForwardResolution(REQUEST_ASSIGN_RESERVATION_TO_PROJECT)
                .addParameter("sampleQuestionId", sampleQuestionId);
    }




}
