package cz.bbmri.action.patient;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Patient;
import cz.bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.2.14
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@UrlBinding("/patient/{biobankId}/{patientId}")
public class PatientActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private BiobankFacade biobankFacade;

    private Long patientId;

    private Patient patient;


    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Patient getPatient() {
        if (patient == null) {
            if (patientId != null) {
                patient = biobankFacade.getPatient(patientId);
            }
        }
        return patient;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("detail")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution detail() {
        return new ForwardResolution(BIOBANK_DETAIL_PATIENT_DETAIL);
    }

    @DontValidate
    @HandlesEvent("modulests")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution modulests() {
        return new ForwardResolution(BIOBANK_DETAIL_PATIENT_MODULESTS);
    }

    @DontValidate
      @HandlesEvent("modulelts")
      @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
      public Resolution modulelts() {
          return new ForwardResolution(BIOBANK_DETAIL_PATIENT_MODULELTS);
      }
}
