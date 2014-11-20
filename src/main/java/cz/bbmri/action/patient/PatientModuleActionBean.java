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
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@HttpCache(allow = false)
@UrlBinding("/biobank/patient/module/{$event}/{patientId}")
public class PatientModuleActionBean extends PermissionActionBean<Sample> {

    @SpringBean
    private PatientService patientService;

    private static Breadcrumb getSTSBreadcrumb(boolean active, Long patientId) {
        return new Breadcrumb(PatientModuleActionBean.class.getName(),
                "modulests", false, "cz.bbmri.action.patient.PatientModuleActionBean.modulests",
                active, "patientId", patientId);
    }

    private static Breadcrumb getLTSBreadcrumb(boolean active, Long patientId) {
        return new Breadcrumb(PatientModuleActionBean.class.getName(),
                "modulelts", false, "cz.bbmri.action.patient.PatientModuleActionBean.modulelts",
                active, "patientId", patientId);
    }

    public PatientModuleActionBean() {
         //default
         setPagination(new MyPagedListHolder<Sample>(new ArrayList<Sample>()));
         setComponentManager(new ComponentManager(
                 ComponentManager.SAMPLE_DETAIL,
                 ComponentManager.PATIENT_DETAIL));
         getPagination().setIdentifierParam("patientId");
     }

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
                patient = patientService.get(patientId);
            }
        }
        return patient;
    }

    void selectSampleComparator() {
        if (getOrderParam() != null) {
            if (getOrderParam().equals("sampleIdentification.year")) {
                getPagination().setComparator(new YearComparator());
                return;
            }
            if (getOrderParam().equals("sampleIdentification.number")) {
                getPagination().setComparator(new NumberComparator());
                return;
            }
            if (getOrderParam().equals("takingDate")) {
                getPagination().setComparator(new TakingDateComparator());
                return;
            }
            if (getOrderParam().equals("materialType")) {
                getPagination().setComparator(new MaterialTypeComparator());
                return;
            }
            if (getOrderParam().equals("sampleNos.availableSamplesNo")) {
                getPagination().setComparator(new AvailableSamplesComparator());
                return;
            }
            if (getOrderParam().equals("sampleNos.samplesNo")) {
                getPagination().setComparator(new TotalSampleNumberComparator());
                return;
            }
        }
        // default
        getPagination().setOrderParam("sampleIdentification.sampleId");
        getPagination().setComparator(new SampleIdComparator());
    }

    @DefaultHandler
    @DontValidate
    @HandlesEvent("modulests")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution modulests() {

        // Biobanks
        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        // Biobanks > Detail
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getPatient().getBiobank().getId(),
                getPatient().getBiobank()));
        // Biobanks > Detail > Patients
        getBreadcrumbs().add(BiobankPatientsActionBean.getBreadcrumb(false, getPatient().getBiobank().getId()));
        // Biobanks > Detail > Patients > Patient
        getBreadcrumbs().add(PatientActionBean.getBreadcrumb(false, patientId));
        // Biobanks > Detail > Patients > Patient > Module STS
        getBreadcrumbs().add(PatientModuleActionBean.getSTSBreadcrumb(true, patientId));

        initiatePagination();
        if (patientId != null) {
            getPagination().setIdentifier(patientId);
        }

        setBiobankId(getPatient().getBiobank().getId());

        selectSampleComparator();
        getPagination().setEvent("modulests");

        System.out.println("Pokusny vypis:");
        System.out.println(getPatient().getModuleSTS().getSamples());

        getPagination().setSource(new ArrayList(getPatient().getModuleSTS().getSamples()));
        return new ForwardResolution(PATIENT_MODULESTS).addParameter("patientId", patientId);
    }

    @DontValidate
    @HandlesEvent("modulelts")
    @RolesAllowed({"biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution modulelts() {

        // Biobanks
        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        // Biobanks > Detail
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getPatient().getBiobank().getId(),
                getPatient().getBiobank()));
        // Biobanks > Detail > Patients
        getBreadcrumbs().add(BiobankPatientsActionBean.getBreadcrumb(false, getPatient().getBiobank().getId()));
        // Biobanks > Detail > Patients > Patient
        getBreadcrumbs().add(PatientActionBean.getBreadcrumb(false, patientId));
        // Biobanks > Detail > Patients > Patient > Module LTS
        getBreadcrumbs().add(PatientModuleActionBean.getLTSBreadcrumb(true, patientId));

        initiatePagination();
        if (patientId != null) {
            getPagination().setIdentifier(patientId);
        }

        setBiobankId(getPatient().getBiobank().getId());

        selectSampleComparator();
        getPagination().setEvent("modulelts");
        getPagination().setSource(new ArrayList(getPatient().getModuleLTS().getSamples()));
        return new ForwardResolution(PATIENT_MODULELTS).addParameter("patientId", patientId);
    }

}
