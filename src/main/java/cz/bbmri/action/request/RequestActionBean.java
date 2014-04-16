package cz.bbmri.action.request;

import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.action.project.ProjectRequestActionBean;
import cz.bbmri.action.reservation.ReservationActionBean;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.service.ProjectService;
import cz.bbmri.service.RequestService;
import cz.bbmri.service.SampleRequestService;
import cz.bbmri.service.SampleReservationService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.1.14
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */

@UrlBinding("/samplerequest/{$event}/{biobankId}/{sampleQuestionId}")
public class RequestActionBean extends AbstractSampleQuestionActionBean {

    private static final int STEP_INCREASE_REQUEST_AMOUNT = 1;

    @SpringBean
    private RequestService requestService;

    @SpringBean
    private SampleRequestService sampleRequestService;

    @SpringBean
    private SampleReservationService sampleReservationService;

    @SpringBean
    private ProjectService projectService;

    private Long requestId;

    public RequestActionBean() {
        setComponentManager(new ComponentManager());
    }

    public static Breadcrumb getSampleRequestBreadcrumb(boolean active, Long sampleQuestionId) {
        return new Breadcrumb(RequestActionBean.class.getName(),
                "detail", false, "cz.bbmri.entities.SampleRequest.sampleRequest", active,
                "sampleQuestionId", sampleQuestionId);
    }

    public static Breadcrumb getSampleReservationBreadcrumb(boolean active, Long sampleQuestionId) {
        return new Breadcrumb(RequestActionBean.class.getName(),
                "detail", false, "cz.bbmri.entities.SampleReservation.sampleReservation", active,
                "sampleQuestionId", sampleQuestionId);
    }

    private static Breadcrumb getAssignToProjectBreadcrumb(boolean active, Long sampleQuestionId) {
        return new Breadcrumb(RequestActionBean.class.getName(),
                "assignToProjectResolution", false, "cz.bbmri.action.request.RequestActionBean.assignToProject", active,
                "sampleQuestionId", sampleQuestionId);
    }

    public List<Project> getMyApprovedProjects() {
        return projectService.allByProjectStateAndUser(getContext().getMyId(), ProjectState.APPROVED);
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer", "project_team_member", "biobank_operator"})
    public Resolution detail() {

        if (getIsSampleRequest()) {
            getBreadcrumbs().add(ProjectActionBean.getProjectsBreadcrumb(false));
            getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(false, getSampleRequest().getProject().getId()));
            getBreadcrumbs().add(ProjectRequestActionBean.getBreadcrumb(false, getSampleRequest().getProject().getId()));
            getBreadcrumbs().add(RequestActionBean.getSampleRequestBreadcrumb(true, getSampleQuestionId()));

        } else if (getIsSampleReservation()) {
            getBreadcrumbs().add(ReservationActionBean.getBreadcrumb(false));
            getBreadcrumbs().add(RequestActionBean.getSampleReservationBreadcrumb(true, getSampleQuestionId()));
        }

        return new ForwardResolution(REQUEST_DETAIL);
    }


    /* Here can be self approve - withdraw */
    @DontValidate
    @HandlesEvent("approve")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionNew}"})
    public Resolution approve() {
        if (!sampleRequestService.approve(getSampleQuestionId(), getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class)
                    .addParameter("sampleQuestionId", getSampleQuestionId())
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(RequestActionBean.class)
                .addParameter("sampleQuestionId", getSampleQuestionId())
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("deny")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionNew}"})
    public Resolution deny() {
        if (!sampleRequestService.deny(getSampleQuestionId(),
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class)
                    .addParameter("sampleQuestionId", getSampleQuestionId())
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(RequestActionBean.class)
                .addParameter("sampleQuestionId", getSampleQuestionId())
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("delete")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor and isSampleQuestionNew}"})
    public Resolution delete() {

        // TODO for each delete all requests - necessary to return samples to previous state

        if (!sampleRequestService.remove(getSampleQuestionId(),
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class)
                    .addParameter("sampleQuestionId", getSampleQuestionId())
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(ProjectRequestActionBean.class, "sampleRequestsResolution")
                .addParameter("projectId", projectId)
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("createRequests")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution createRequests() {

        return new ForwardResolution(RequestActionBean.class)
                .addParameter("sampleQuestionId", getSampleQuestionId())
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("decreaseAmount")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionApproved}"})
    public Resolution decreaseAmount() {

        if (!requestService.changeRequestedAmount(requestId, false, STEP_INCREASE_REQUEST_AMOUNT,
                getContext().getValidationErrors())) {

            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleQuestionId", getSampleQuestionId());
        }
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleQuestionId", getSampleQuestionId());
    }

    @DontValidate
    @HandlesEvent("increaseAmount")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionApproved}"})
    public Resolution increaseAmount() {

        if (!requestService.changeRequestedAmount(requestId, true, STEP_INCREASE_REQUEST_AMOUNT,
                getContext().getValidationErrors())) {

            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleQuestionId", getSampleQuestionId());
        }
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleQuestionId", getSampleQuestionId());
    }

    @DontValidate
    @HandlesEvent("removeRequest")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionApproved}"})
    public Resolution removeRequest() {
        if (!requestService.remove(requestId, getContext().getValidationErrors())) {

            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleQuestionId", getSampleQuestionId());
        }
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleQuestionId", getSampleQuestionId());
    }

    @DontValidate
    @HandlesEvent("changeStateToClosed")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionApproved}"})
    public Resolution changeStateToClosed() {

        if (!sampleRequestService.close(getSampleQuestionId(),
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleQuestionId", getSampleQuestionId())
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleQuestionId", getSampleQuestionId())
                .addParameter("biobankId", biobankId);
    }


    @DontValidate
    @HandlesEvent("confirmChosenSet")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor and isSampleQuestionClosed}"})
    public Resolution confirmChosenSet() {
        if (!sampleRequestService.confirmChosenSet(getSampleQuestionId(),
                getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleQuestionId", getSampleQuestionId())
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleQuestionId", getSampleQuestionId())
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("denyChosenSet")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor and isSampleQuestionClosed}"})
    public Resolution denyChosenSet() {

        if (!sampleRequestService.denyChosenSet(getSampleQuestionId(),
                getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleQuestionId", getSampleQuestionId())
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleQuestionId", getSampleQuestionId())
                .addParameter("biobankId", biobankId);
    }


    @DontValidate
    @HandlesEvent("setAsDelivered")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor and isSampleQuestionAgreed}"})
    public Resolution setAsDelivered() {

        if (!sampleRequestService.setAsDelivered(getSampleQuestionId(),
                getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("sampleQuestionId", getSampleQuestionId())
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("sampleQuestionId", getSampleQuestionId())
                .addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("exportSampleList")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor and isSampleQuestionAgreed}"})
    public Resolution exportSampleList() {

        return new ForwardResolution(REQUEST_EXPORT)
                .addParameter("sampleQuestionId", getSampleQuestionId())
                .addParameter("biobankId", biobankId);

    }

    @DontValidate
    @HandlesEvent("assignToProjectResolution")
    @RolesAllowed({"project_team_member if ${isSampleQuestionClosed}"})
    public Resolution assignToProjectResolution() {

        getBreadcrumbs().add(ReservationActionBean.getBreadcrumb(false));
        getBreadcrumbs().add(RequestActionBean.getSampleReservationBreadcrumb(false, getSampleQuestionId()));
        getBreadcrumbs().add(RequestActionBean.getAssignToProjectBreadcrumb(true, getSampleQuestionId()));

        return new ForwardResolution(REQUEST_ASSIGN_RESERVATION_TO_PROJECT)
                .addParameter("sampleQuestionId", getSampleQuestionId());
    }

    @DontValidate
    @HandlesEvent("assignToProject")
    @RolesAllowed({"project_team_member if ${isSampleQuestionClosed}"})
    public Resolution assignToProject() {

        if (!sampleReservationService.assignReservationToProject(getSampleQuestionId(), projectId, getContext().getValidationErrors())) {
            return new ForwardResolution(ReservationActionBean.class);
        }
        successMsg();

        return new RedirectResolution(ProjectRequestActionBean.class, "sampleRequestsResolution")
                .addParameter("projectId", projectId);
    }

}
