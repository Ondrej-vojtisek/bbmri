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
import cz.bbmri.service.RequestService;
import cz.bbmri.service.SampleQuestionService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 27.2.14
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/samplequestion/{$event}/{sampleQuestionId}")
public class CreateSampleQuestion extends PermissionActionBean {

    @SpringBean
    private BiobankService biobankService;

    @SpringBean
    private SampleQuestionService sampleQuestionService;

    @ValidateNestedProperties(value = {
            @Validate(field = "specification",
                    required = true,
                    on = {"confirmSampleRequest", "confirmSampleReservation"})
    })
    private SampleQuestion sampleQuestion;

    public static Breadcrumb getBreadcrumb(boolean active) {
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

        if (!sampleQuestionService.createSampleRequest(sampleRequest, projectId, biobankId, getContext().getValidationErrors())) {
            return new ForwardResolution(ProjectRequestActionBean.class, "sampleRequestsResolution")
                    .addParameter("projectId", projectId);
        }
        successMsg(null);
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

        if (!sampleQuestionService.createSampleQuestion(sampleReservation, biobankId, getContext().getValidationErrors())) {
            return new ForwardResolution(ReservationActionBean.class, "all");
        }
        successMsg(null);
        return new RedirectResolution(ReservationActionBean.class, "all");
    }
}
