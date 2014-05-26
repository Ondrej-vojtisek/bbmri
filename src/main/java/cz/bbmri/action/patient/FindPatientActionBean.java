package cz.bbmri.action.patient;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.biobank.BiobankPatientsActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.PatientService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/biobank/patient/findpatient/{biobankId}/{patientId}")
public class FindPatientActionBean extends PermissionActionBean<Patient> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private BiobankService biobankService;

    @SpringBean
    private PatientService patientService;

    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    private Biobank biobank;

    private static Breadcrumb getBreadcrumb(boolean active) {
        return new Breadcrumb(FindPatientActionBean.class.getName(),
                "findResolution", false, "cz.bbmri.action.patient.FindPatientActionBean.findPatient", active);
    }

    public FindPatientActionBean() {
        //default
        setPagination(new MyPagedListHolder<Patient>(new ArrayList<Patient>()));
        setComponentManager(new ComponentManager(
                ComponentManager.PATIENT_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("patientId");
    }


    public List<Patient> getPatients() {
        if (patient == null) {
            return null;
        }

        if (getBiobank() == null) {
            logger.debug("Biobank null ");
            return null;
        }

        // Find only in my biobank!
        patient.setBiobank(biobank);
        return patientService.find(patient, Constant.MAXIMUM_FIND_RESULTS);
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("findResolution")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution findResolution() {

        // Biobanks
        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        // Biobanks > Detail
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getBiobank().getId(),
                getBiobank()));
        // Biobanks > Detail > Patients
        getBreadcrumbs().add(BiobankPatientsActionBean.getBreadcrumb(false, getBiobank().getId()));
        // Biobanks > Detail > Patients > FindPatient
        getBreadcrumbs().add(FindPatientActionBean.getBreadcrumb(true));

        return new ForwardResolution(PATIENT_FIND)
                .addParameter("biobankId", biobankId);
    }

    @HandlesEvent("findPatient") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution findPatient() {
        return new ForwardResolution(this.getClass(), "findResolution")
                .addParameter("biobankId", biobankId);
    }
}
