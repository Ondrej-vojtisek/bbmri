package cz.bbmri.action.request;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.facade.RequestFacade;
import cz.bbmri.facade.SampleFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 20.2.14
 * Time: 15:12
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/createrequests/{$event}/{sampleQuestionId}")
public class CreateRequestsActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private RequestFacade requestFacade;

    @SpringBean
    private SampleFacade sampleFacade;

    /* SampleQuestion identifier */
    private Long sampleQuestionId;

    private SampleQuestion sampleQuestion;

    private Sample sample;

    private Patient patient;

    private boolean moduleLTS;

    private List<Long> selectedSamples;

    public Long getSampleQuestionId() {
        return sampleQuestionId;
    }

    public void setSampleQuestionId(Long sampleQuestionId) {
        this.sampleQuestionId = sampleQuestionId;
    }

    public SampleRequest getSampleRequest() {
        if(getSampleQuestion() == null){
            return null;
        }
        return (SampleRequest) getSampleQuestion();
    }

    public SampleQuestion getSampleQuestion() {

        if (sampleQuestion == null) {

            if (sampleQuestionId != null) {

                sampleQuestion = requestFacade.getSampleQuestion(sampleQuestionId);
            }
        }
        return sampleQuestion;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isModuleLTS() {
        return moduleLTS;
    }

    public void setModuleLTS(boolean moduleLTS) {
        this.moduleLTS = moduleLTS;
    }

    public List<Long> getSelectedSamples() {
        return selectedSamples;
    }

    public void setSelectedSamples(List<Long> selectedSamples) {
        this.selectedSamples = selectedSamples;
    }

    public List<Sample> getSamples() {
        if (sample == null && patient == null) {
            logger.debug("Sample and patient is null");
            return null;
        }

        logger.debug("Sample: " + sample);
        logger.debug("Patient: " + patient);
        logger.debug("BiobankId: " + biobankId);
        logger.debug("ModuleLTS: " + moduleLTS);

        return sampleFacade.findSamples(sample, biobankId, patient, moduleLTS);
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("initial")
    @RolesAllowed({"administrator", "developer", "project_team_member", "biobank_operator"})
    public Resolution initial() {
        return new ForwardResolution(CREATE_REQUESTS);
    }

    @HandlesEvent("find") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution find() {
        return new ForwardResolution(this.getClass(), "initial")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleQuestionId", sampleQuestionId);
    }

    @HandlesEvent("confirmSelected") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution confirmSelected() {
        logger.debug("ConfirmSelected: " + selectedSamples);
        logger.debug("SampleQuestionId: " + sampleQuestionId);

        if (!requestFacade.createRequests(selectedSamples,
                sampleQuestionId,
                getContext().getValidationErrors(),
                getContext().getMessages())) {

            return new ForwardResolution(RequestActionBean.class, "detail")
                    .addParameter("biobankId", biobankId)
                    .addParameter("sampleQuestionId", sampleQuestionId);
        }

        return new RedirectResolution(RequestActionBean.class, "detail")
                .addParameter("biobankId", biobankId)
                .addParameter("sampleQuestionId", sampleQuestionId);
    }


}