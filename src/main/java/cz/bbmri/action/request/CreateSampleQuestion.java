package cz.bbmri.action.request;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.action.reservation.ReservationActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.facade.BiobankFacade;
import cz.bbmri.facade.RequestFacade;
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

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private RequestFacade requestFacade;

    @SpringBean
    private BiobankFacade biobankFacade;

    @ValidateNestedProperties(value = {
            @Validate(field = "specification",
                    required = true,
                    on = {"confirmSampleRequest", "confirmSampleReservation"})
    })
    private SampleQuestion sampleQuestion;

    public SampleQuestion getSampleQuestion() {
        return sampleQuestion;
    }

    public void setSampleQuestion(SampleQuestion sampleQuestion) {
        this.sampleQuestion = sampleQuestion;
    }

    public List<Biobank> getAllBiobanks() {
        return biobankFacade.all();
    }

    @DefaultHandler
    @DontValidate
    @HandlesEvent("createSampleRequest")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor}"})
    public Resolution createSampleRequest() {
        return new ForwardResolution(REQUEST_CREATE_SAMPLE_REQUEST);
    }

    //VALIDATE
    @HandlesEvent("confirmSampleRequest")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor}"})
    public Resolution confirmSampleRequest() {

        SampleRequest sampleRequest = (SampleRequest) sampleQuestion;
        sampleRequest.setProject(getProject());

        if (!requestFacade.createSampleRequest(sampleRequest, projectId, biobankId, getContext().getValidationErrors())) {
            return new ForwardResolution(ProjectActionBean.class, "sampleRequests")
                    .addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(ProjectActionBean.class, "sampleRequests")
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

        if (!requestFacade.createSampleQuestion(sampleReservation, biobankId, getContext().getValidationErrors())) {
            return new ForwardResolution(ReservationActionBean.class, "all");
        }
        successMsg(null);
        return new RedirectResolution(ReservationActionBean.class, "all");
    }
}
