package cz.bbmri.action.patient;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Patient;
import cz.bbmri.service.PatientService;
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
 * Date: 18.2.14
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@Wizard(startEvents = {"initial"})
@UrlBinding("/createpatient/{biobankId}/{patientId}")
public class CreatePatientActionBean extends PermissionActionBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private PatientService patientService;


    @ValidateNestedProperties(value = {
            @Validate(on = "createPatient", field = "institutionId", required = true),
            @Validate(on = "createPatient", field = "sex", required = true),
            @Validate(on = "createPatient", field = "birthYear", required = true),
            @Validate(on = "createPatient", field = "birthMonth", required = true),
            @Validate(on = "createPatient", field = "consent", required = true)
    })
    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("initial") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution initial() {
        return new ForwardResolution(PATIENT_CREATE_INITIAL);
    }

    @HandlesEvent("createPatient") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution createPatient() {
        logger.debug("Patient: " + patient);
        if (!patientService.create(patient, biobankId, getContext().getValidationErrors())) {
            return new ForwardResolution(cz.bbmri.action.biobank.BiobankPatientsActionBean.class, "display")
                    .addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(cz.bbmri.action.biobank.BiobankPatientsActionBean.class, "display")
                .addParameter("biobankId", biobankId);
    }


}
