package cz.bbmri.action.patient;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.biobank.BiobankPatientsActionBean;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.comparator.sample.*;
import cz.bbmri.entities.sample.Sample;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.PatientService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.2.14
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@UrlBinding("/biobank/patient/{$event}/{patientId}")
public class PatientActionBean extends PermissionActionBean<Patient> {

    @SpringBean
    private PatientService patientService;

    public static Breadcrumb getBreadcrumb(boolean active, Long patientId) {
        return new Breadcrumb(PatientActionBean.class.getName(),
                "display", false, "cz.bbmri.entities.Patient.patient",
                active, "patientId", patientId);
    }

    private Long patientId;

    private Patient patient;

    public PatientActionBean() {

        //default
        setPagination(new MyPagedListHolder<Patient>(new ArrayList<Patient>()));
        setComponentManager(new ComponentManager(
                ComponentManager.PATIENT_DETAIL,
                ComponentManager.PATIENT_DETAIL));
        getPagination().setIdentifierParam("patientId");
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Patient getPatient() {
        if (patient == null) {
            if (patientId != null) {
                patient = patientService.get(patientId);
            }
        }
        return patient;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("detail")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution detail() {
        // Biobanks
        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        // Biobanks > Detail
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getPatient().getBiobank().getId(),
                getPatient().getBiobank()));
        // Biobanks > Detail > Patients
        getBreadcrumbs().add(BiobankPatientsActionBean.getBreadcrumb(false, getPatient().getBiobank().getId()));
        // Biobanks > Detail > Patients > Patient
        getBreadcrumbs().add(PatientActionBean.getBreadcrumb(true, patientId));

        setBiobankId(getPatient().getBiobank().getId());

        return new ForwardResolution(PATIENT_DETAIL);
    }


    @HandlesEvent(" findPatient") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution findPatient() {
        return new ForwardResolution(SAMPLE_CREATE_INITIAL);
    }
}
