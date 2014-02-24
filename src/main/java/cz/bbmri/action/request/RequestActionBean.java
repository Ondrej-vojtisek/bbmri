package cz.bbmri.action.request;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.SampleRequest;
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
@UrlBinding("/samplerequest/{$event}/{biobankId}/{sampleRequestId}")
public class RequestActionBean extends PermissionActionBean {

    private static final int STEP_INCREASE_REQUEST_AMOUNT = 1;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private RequestFacade requestFacade;

    @SpringBean
    private BiobankFacade biobankFacade;

    /* SampleQuestion identifier */
    private Long sampleRequestId;

    private SampleRequest sampleRequest;

    private Long requestId;

    public Long getSampleRequestId() {
        return sampleRequestId;
    }


    public void setSampleRequestId(Long sampleRequestId) {
        this.sampleRequestId = sampleRequestId;
    }

    public SampleRequest getSampleRequest() {

        if (sampleRequest == null) {

            if (sampleRequestId != null) {
                sampleRequest = requestFacade.getSampleRequest(sampleRequestId);
                setProjectId(sampleRequest.getProject().getId());
            }
        }
        return sampleRequest;
    }

    public boolean getIsSampleRequestNew() {
        if (getSampleRequest() == null) {
            return false;
        }
        return getSampleRequest().isNew();
    }

    public boolean getIsSampleRequestApproved() {
        if (getSampleRequest() == null) {
            return false;
        }
        return getSampleRequest().isApproved();
    }

    public boolean getIsSampleRequestDenied() {
        if (getSampleRequest() == null) {
            return false;
        }
        return getSampleRequest().isDenied();
    }

    public boolean getIsSampleRequestClosed() {
        if (getSampleRequest() == null) {
            return false;
        }
        return getSampleRequest().isClosed();
    }

    private boolean isSampleRequestAgreed() {
        if (getSampleRequest() == null) {
            return false;
        }
        return getSampleRequest().isAgreed();
    }


    private boolean isSampleRequestDelivered() {
        if (getSampleRequest() == null) {
            return false;
        }
        return getSampleRequest().isDelivered();
    }

    public Set<Request> getRequests() {
        if (getSampleRequest() == null) {
            return null;
        }
        return sampleRequest.getRequests();
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
        getSampleRequest();
        return new ForwardResolution(REQUEST_DETAIL);
    }


    /* Here can be self approve - withdraw */
    @DontValidate
    @HandlesEvent("approve")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor && isSampleRequestNew}"})
    public Resolution approve() {
        if (!requestFacade.approveSampleRequest(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class)
                    .addParameter("sampleRequestId", sampleRequestId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(RequestActionBean.class)
                .addParameter("sampleRequestId", sampleRequestId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("deny")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor && isSampleRequestNew}"})
    public Resolution deny() {
        if (!requestFacade.denySampleRequest(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class)
                    .addParameter("sampleRequestId", sampleRequestId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(RequestActionBean.class)
                .addParameter("sampleRequestId", sampleRequestId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("delete")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor && isSampleRequestNew}"})
    public Resolution delete() {
        Long projectId = getSampleRequest().getProject().getId();
        if (!requestFacade.deleteSampleRequest(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class)
                    .addParameter("sampleRequestId", sampleRequestId)
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
                .addParameter("sampleRequestId", sampleRequestId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("decreaseAmount")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor && isSampleRequestApproved}"})
    public Resolution decreaseAmount() {

        logger.debug("requestId: " + requestId);

        if (!requestFacade.changeRequestedAmount(requestId, false, STEP_INCREASE_REQUEST_AMOUNT,
                getContext().getValidationErrors())) {


            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleRequestId", sampleRequestId);
        }
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleRequestId", sampleRequestId);
    }

    @DontValidate
    @HandlesEvent("increaseAmount")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor && isSampleRequestApproved}"})
    public Resolution increaseAmount() {

        logger.debug("requestId: " + requestId);

        if (!requestFacade.changeRequestedAmount(requestId, true, STEP_INCREASE_REQUEST_AMOUNT,
                getContext().getValidationErrors())) {

            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleRequestId", sampleRequestId);
        }
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleRequestId", sampleRequestId);
    }

    @DontValidate
    @HandlesEvent("removeRequest")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor && isSampleRequestApproved}"})
    public Resolution removeRequest() {
        if (!requestFacade.deleteRequest(requestId, getContext().getValidationErrors())) {

            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleRequestId", sampleRequestId);
        }
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleRequestId", sampleRequestId);
    }

    @DontValidate
    @HandlesEvent("changeStateToClosed")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor && isSampleRequestApproved}"})
    public Resolution changeStateToClosed() {

        if (!requestFacade.closeSampleRequest(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleRequestId", sampleRequestId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleRequestId", sampleRequestId)
                .addParameter("biobankId", biobankId);
    }


    @DontValidate
    @HandlesEvent("confirmChosenSet")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor && isSampleRequestClosed}"})
    public Resolution confirmChosenSet() {
        if (!requestFacade.confirmChosenSet(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleRequestId", sampleRequestId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleRequestId", sampleRequestId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("denyChosenSet")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor && isSampleRequestClosed}"})
    public Resolution denyChosenSet() {

        if (!requestFacade.denyChosenSet(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleRequestId", sampleRequestId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleRequestId", sampleRequestId)
                .addParameter("biobankId", biobankId);
    }


    @DontValidate
    @HandlesEvent("setAsDelivered")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor && isSampleRequestAgreed}"})
    public Resolution setAsDelivered() {

        if (!requestFacade.setAsDelivered(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleRequestId", sampleRequestId)
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleRequestId", sampleRequestId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("exportSampleList")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor && isSampleRequestAgreed}"})
    public Resolution exportSampleList() {

        logger.debug("BiobankId: " + biobankId);
        logger.debug("sampleRequestId: " + sampleRequestId);

        return new ForwardResolution("/webpages/request/export.jsp")
                .addParameter("sampleRequestId", sampleRequestId)
                .addParameter("biobankId", biobankId);

    }


}
