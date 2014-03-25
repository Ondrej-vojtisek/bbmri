package cz.bbmri.action.patient;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.biobank.BiobankPatientsActionBean;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.comparator.sample.*;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.facade.BiobankFacade;
import cz.bbmri.facade.SampleFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class PatientActionBean extends PermissionActionBean<Sample> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private BiobankFacade biobankFacade;

    @SpringBean
    private SampleFacade sampleFacade;

    public static Breadcrumb getBreadcrumb(boolean active, Long patientId) {
        return new Breadcrumb(PatientActionBean.class.getName(),
                "display", false, "cz.bbmri.entities.Patient.patient",
                active, "patientId", patientId);
    }

    public static Breadcrumb getSTSBreadcrumb(boolean active, Long patientId) {
        return new Breadcrumb(PatientActionBean.class.getName(),
                "modulests", false, "cz.bbmri.action.patient.PatientActionBean.modulests",
                active, "patientId", patientId);
    }

    public static Breadcrumb getLTSBreadcrumb(boolean active, Long patientId) {
        return new Breadcrumb(PatientActionBean.class.getName(),
                "modulelts", false, "cz.bbmri.action.patient.PatientActionBean.modulelts",
                active, "patientId", patientId);
    }

    private Long patientId;

    private Patient patient;

    public PatientActionBean() {
        //default
        setPagination(new MyPagedListHolder<Sample>(new ArrayList<Sample>()));
        setComponentManager(new ComponentManager(
                ComponentManager.SAMPLE_DETAIL,
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
                patient = biobankFacade.getPatient(patientId);
            }
        }
        return patient;
    }

    public void selectSampleComparator() {
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

        return new ForwardResolution(PATIENT_DETAIL);
    }

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
        getBreadcrumbs().add(PatientActionBean.getSTSBreadcrumb(true, patientId));

        initiatePagination();
        if (patientId != null) {
            getPagination().setIdentifier(patientId);
        }

        selectSampleComparator();
        getPagination().setEvent("modulests");
        getPagination().setSource(getPatient().getModuleSTS().getSamples());
        return new ForwardResolution(PATIENT_MODULESTS);
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
        getBreadcrumbs().add(PatientActionBean.getLTSBreadcrumb(true, patientId));

        initiatePagination();
        if (patientId != null) {
            getPagination().setIdentifier(patientId);
        }

        selectSampleComparator();
        getPagination().setEvent("modulelts");
        getPagination().setSource(getPatient().getModuleLTS().getSamples());
        return new ForwardResolution(PATIENT_MODULELTS);
    }

    @HandlesEvent(" findPatient") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution findPatient() {
        return new ForwardResolution(SAMPLE_CREATE_INITIAL);
    }
}
