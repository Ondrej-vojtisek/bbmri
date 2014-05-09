package cz.bbmri.action.sample;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.patient.PatientActionBean;
import cz.bbmri.entities.Module;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.enumeration.SampleType;
import cz.bbmri.entities.sample.*;
import cz.bbmri.entities.sample.field.SampleNos;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.service.ModuleService;
import cz.bbmri.service.SampleService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@HttpCache(allow = false)
@Wizard(startEvents = {"initial"})
@UrlBinding("/createSample/{$event}/{biobankId}/{sampleId}")
public class CreateSampleActionBean extends PermissionActionBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    public CreateSampleActionBean() {
         //default
         setComponentManager(new ComponentManager());
     }

    @SpringBean
    private SampleService sampleService;

    @SpringBean
    private ModuleService moduleService;

    private Long sampleId;

    @ValidateNestedProperties(value = {
            @Validate(on = "confirmStep1", field = "sampleIdentification.sampleId", required = true),
            @Validate(on = "confirmStep1", field = "retrieved", required = true),
            @Validate(on = "confirmStep1", field = "takingDate", required = true),
            @Validate(on = "confirmStep2", field = "materialType.type", required = true)
    })
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
                module = moduleService.get(moduleId);
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
        return sampleType != null &&  sampleType.equals(SampleType.TISSUE);
    }

    public boolean getIsDiagnosisMaterial() {
        return sampleType != null && sampleType.equals(SampleType.DIAGNOSIS_MATERIAL);
    }

    public boolean getIsSerum() {
        return sampleType != null && sampleType.equals(SampleType.SERUM);
    }

    public boolean getIsGenome() {
        return sampleType != null && sampleType.equals(SampleType.GENOME);
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
        Sample sampleOUT = tissue;

        if (getIsTissue()) {
            sampleOUT = tissue;
        }

        if (getIsSerum()) {
            sampleOUT = serum;
        }

        if (getIsDiagnosisMaterial()) {

            /* This is not part of create wizard */

            if(sample.getSampleNos() == null){
                sample.setSampleNos(new SampleNos());
            }

             /* Implicit amount - there is only one sample */

            sample.getSampleNos().setAvailableSamplesNo(1);
            sample.getSampleNos().setSamplesNo(1);

            sampleOUT = diagnosisMaterial;
        }

        if (getIsGenome()) {
            sampleOUT = genome;
        }

        logger.debug("Sample: " + sample);
        logger.debug("Sample OUT: " + sampleOUT);


        sampleOUT.setMaterialType(sample.getMaterialType());
        sampleOUT.setTakingDate(sample.getTakingDate());
        sampleOUT.setSampleIdentification(sample.getSampleIdentification());
        sampleOUT.setRetrieved(sample.getRetrieved());
        sampleOUT.setModule(getModule());
        sampleOUT.setSampleNos(sample.getSampleNos());

        if(!sampleService.create(sampleOUT, null, getContext().getValidationErrors())){
            return new RedirectResolution(PatientActionBean.class, "detail")
                    .addParameter("patientId", getPatient().getId())
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
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
