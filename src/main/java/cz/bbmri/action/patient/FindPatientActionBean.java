package cz.bbmri.action.patient;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import cz.bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.2.14
 * Time: 10:49
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/findpatient/{biobankId}/{patientId}")
public class FindPatientActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private BiobankFacade biobankFacade;


    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    private Biobank biobank;

    public Biobank getBiobank() {
          if (biobank == null) {
              if (biobankId != null) {
                  biobank = biobankFacade.get(biobankId);
              }
          }
          return biobank;
      }

    public List<Patient> getPatients(){
        if(patient == null){
            return null;
        }

        logger.debug("Patient: " + patient);

        logger.debug("BiobankId: " + biobankId);

        if(getBiobank() == null){
            logger.debug("Biobank null ");
            return null;
        }
        patient.setBiobank(biobank);
        return biobankFacade.find(patient, 5);
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("findResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution findResolution() {

        logger.debug("BiobankId: " + biobankId);
        return new ForwardResolution(PATIENT_FIND)
                .addParameter("biobankId", biobankId);
    }

    @HandlesEvent("findPatient") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution findPatient() {

        logger.debug("patient: " + patient);

        return new ForwardResolution(this.getClass(), "findResolution")
                .addParameter("biobankId", biobankId);
    }
}
