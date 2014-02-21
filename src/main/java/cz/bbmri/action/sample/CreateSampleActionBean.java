package cz.bbmri.action.sample;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.patient.PatientActionBean;
import cz.bbmri.entities.Module;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.enumeration.SampleType;
import cz.bbmri.entities.sample.DiagnosisMaterial;
import cz.bbmri.entities.sample.Genome;
import cz.bbmri.entities.sample.Serum;
import cz.bbmri.entities.sample.Tissue;
import cz.bbmri.facade.BiobankFacade;
import cz.bbmri.facade.SampleFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.2.14
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */

@HttpCache(allow = false)
@Wizard(startEvents = {"initial"})
@UrlBinding("/createSample/{$event}/{biobankId}/{sampleId}")
public class CreateSampleActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private SampleFacade sampleFacade;

    @SpringBean
    private BiobankFacade biobankFacade;

    private Long sampleId;

//    @ValidateNestedProperties(value = {
//            @Validate(on = "confirmStep1", field = "sampleIdentificator.sampleId", required = true),
//            @Validate(on = "confirmStep1", field = "retrieved", required = true),
//            @Validate(on = "confirmStep1", field = "takingDate", required = true),
//            @Validate(on = "confirmStep2", field = "materialType.type", required = true)
//    })
    private Sample sample;

    private Long moduleId;

    private Patient patient;

    private Module module;

    private Tissue tissue;

    private Serum serum;

    private DiagnosisMaterial diagnosisMaterial;

    private Genome genome;

    @Validate(on = "confirmStep2", required = true)
    private SampleType sampleType;

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Tissue getTissue() {
        return tissue;
    }

    public void setTissue(Tissue tissue) {
        this.tissue = tissue;
    }

    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

    public Serum getSerum() {
        return serum;
    }

    public void setSerum(Serum serum) {
        this.serum = serum;
    }

    public DiagnosisMaterial getDiagnosisMaterial() {
        return diagnosisMaterial;
    }

    public void setDiagnosisMaterial(DiagnosisMaterial diagnosisMaterial) {
        this.diagnosisMaterial = diagnosisMaterial;
    }

    public Module getModule() {
        if (module == null) {
            if (moduleId != null) {
                module = sampleFacade.getModule(moduleId);
            }
        }
        return module;
    }

    public Patient getPatient() {
        if (patient == null) {
            if (getModule() != null) {
                patient = getModule().getPatient();
            }
        }
        return patient;
    }


    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public boolean getIsTissue() {
        if (sampleType == null) {
            return false;
        }
        return sampleType.equals(SampleType.TISSUE);
    }

    public boolean getIsDiagnosisMaterial() {
        if (sampleType == null) {
            return false;
        }
        return sampleType.equals(SampleType.DIAGNOSIS_MATERIAL);
    }

    public boolean getIsSerum() {
        if (sampleType == null) {
            return false;
        }
        return sampleType.equals(SampleType.SERUM);
    }

    public boolean getIsGenome() {
        if (sampleType == null) {
            return false;
        }
        return sampleType.equals(SampleType.GENOME);
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("initial") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution initial() {
        return new ForwardResolution(SAMPLE_CREATE_INITIAL);
    }

    @HandlesEvent("confirmStep1") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution confirmStep1() {
        logger.debug("Sample " + sample);

        return new ForwardResolution(SAMPLE_CREATE_TYPE);
    }


    @HandlesEvent("confirmStep2") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution confirmStep2() {
        return new ForwardResolution(SAMPLE_CREATE_CLASIFICATIONS);
    }

    @HandlesEvent("backFromStep2") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution backFromStep2() {
        return new ForwardResolution(this.getClass(), "initial");
    }

    @HandlesEvent("confirmStep3") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution confirmStep3() {
        return new ForwardResolution(SAMPLE_CREATE_CONFIRM);
    }

    @HandlesEvent("backFromStep3") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution backFromStep3() {
        return new ForwardResolution(SAMPLE_CREATE_TYPE);
    }

    @HandlesEvent("confirmStep4") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution confirmStep4() {
        Sample sampleOUT = (Sample) tissue;
        String type = "";

        if (getIsTissue()) {
            sampleOUT = (Sample) tissue;
        }

        if (getIsSerum()) {
            sampleOUT = (Sample) serum;
        }

        if (getIsDiagnosisMaterial()) {
            sampleOUT = (Sample) diagnosisMaterial;
        }

        if (getIsGenome()) {
            sampleOUT = (Sample) genome;
        }

        sampleOUT.setMaterialType(sample.getMaterialType());
        sampleOUT.setTakingDate(sample.getTakingDate());
        sampleOUT.setSampleIdentificator(sample.getSampleIdentificator());
        sampleOUT.setRetrieved(sample.getRetrieved());
        sampleOUT.setModule(getModule());

        Object object = sampleOUT;
        logger.debug("ObjectType: " + object.getClass());

        if(!sampleFacade.create(sampleOUT, getContext().getValidationErrors())){
            return new RedirectResolution(PatientActionBean.class, "detail")
                    .addParameter("patientId", getPatient().getId())
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(PatientActionBean.class, "detail")
                .addParameter("patientId", getPatient().getId())
                .addParameter("biobankId", biobankId);
    }

    @HandlesEvent("backFromStep4") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution backFromStep4() {
        return new ForwardResolution(SAMPLE_CREATE_CLASIFICATIONS);
    }


}
