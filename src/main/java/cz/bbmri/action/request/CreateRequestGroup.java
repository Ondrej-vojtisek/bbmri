package cz.bbmri.action.request;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
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
@UrlBinding("/createrequestgroup/{$event}/{sampleRequestId}")
public class CreateRequestGroup extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private RequestFacade requestFacade;

    @SpringBean
    private SampleFacade sampleFacade;

    /* SampleQuestion identifier */
    private Long sampleRequestId;

    private SampleRequest sampleRequest;

    private Sample sample;

    private Patient patient;

    private boolean moduleLTS;

    private List<Long> selectedSamples;

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
            }
        }
        return sampleRequest;
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
        return new ForwardResolution(REQUESTGROUP_CREATE);
    }

    @HandlesEvent("find") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution find() {
        logger.debug("Sample: " + sample);
        logger.debug("Patient: " + patient);
        logger.debug("BiobankId: " + biobankId);
        logger.debug("ModuleLTS: " + moduleLTS);

        return new ForwardResolution(this.getClass(), "initial");
    }

    @HandlesEvent("cofirmSelected") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution cofirmSelected() {
        logger.debug("ConfirmSelected: " + selectedSamples);

        return new ForwardResolution(this.getClass(), "initial");
    }



}
