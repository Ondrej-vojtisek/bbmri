package cz.bbmri.action.request;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.action.project.ProjectRequestActionBean;
import cz.bbmri.action.reservation.ReservationActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.SampleRequestService;
import cz.bbmri.service.SampleReservationService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/samplequestion/{$event}/{sampleQuestionId}")
public class CreateSampleQuestion extends PermissionActionBean {

    @SpringBean
    private BiobankService biobankService;

    @SpringBean
    private SampleRequestService sampleRequestService;

    @SpringBean
    private SampleReservationService sampleReservationService;

    @ValidateNestedProperties(value = {
            @Validate(field = "specification",
                    required = true,
                    on = {"confirmSampleRequest", "confirmSampleReservation"})
    })
    private SampleQuestion sampleQuestion;

    private static Breadcrumb getBreadcrumb(boolean active) {
        return new Breadcrumb(CreateSampleQuestion.class.getName(),
                "createSampleRequest", false, "cz.bbmri.action.request.CreateSampleQuestion.createSampleRequest.breadcrumb",
                active);
    }

    public CreateSampleQuestion() {
        setComponentManager(new ComponentManager());
    }

    public SampleQuestion getSampleQuestion() {
        return sampleQuestion;
    }

    public void setSampleQuestion(SampleQuestion sampleQuestion) {
        this.sampleQuestion = sampleQuestion;
    }

    public List<Biobank> getAllBiobanks() {
        return biobankService.all();
    }

    @DefaultHandler
    @DontValidate
    @HandlesEvent("createSampleRequest")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor}"})
    public Resolution createSampleRequest() {

        getBreadcrumbs().add(ProjectActionBean.getProjectsBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(false, projectId));
        getBreadcrumbs().add(CreateSampleQuestion.getBreadcrumb(true));

        return new ForwardResolution(REQUEST_CREATE_SAMPLE_REQUEST);
    }

    //VALIDATE
    @HandlesEvent("confirmSampleRequest")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor}"})
    public Resolution confirmSampleRequest() {

        SampleRequest sampleRequest = new SampleRequest();
        sampleRequest.setProject(getProject());
        sampleRequest.setSpecification(sampleQuestion.getSpecification());

        if (!sampleRequestService.createSampleRequest(sampleRequest, projectId, biobankId,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(ProjectRequestActionBean.class, "sampleRequestsResolution")
                    .addParameter("projectId", projectId);
        }
        successMsg();
        return new RedirectResolution(ProjectRequestActionBean.class, "sampleRequestsResolution")
                .addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("createSampleReservation")
    @RolesAllowed({"user"})
    public Resolution createSampleReservation() {
        return new ForwardResolution(REQUEST_CREATE_SAMPLE_RESERVATION);
    }

    //VALIDATE
    @HandlesEvent("confirmSampleReservation")
    @RolesAllowed({"user"})
    public Resolution confirmSampleReservation() {
        SampleReservation sampleReservation = new SampleReservation();
        sampleReservation.setUser(getLoggedUser());
        sampleReservation.setSpecification(sampleQuestion.getSpecification());

        if (!sampleReservationService.createSampleReservation(sampleReservation, biobankId, getLoggedUser(),
                getContext().getValidationErrors())) {
            return new ForwardResolution(ReservationActionBean.class, "all");
        }
        successMsg();
        return new RedirectResolution(ReservationActionBean.class, "all");
    }
}
