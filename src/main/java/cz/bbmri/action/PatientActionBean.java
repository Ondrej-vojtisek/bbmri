package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.PatientDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.Notification;
import cz.bbmri.entity.Patient;
import cz.bbmri.entity.Sample;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.ComponentManager;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
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
@UrlBinding("/patient/{$event}/{id}")
public class PatientActionBean extends AuthorizationActionBean {

    @SpringBean
    private PatientDAO patientDAO;

    private Long id;

    private Patient patient;

    private MyPagedListHolder<Patient> pagination = new MyPagedListHolder<Patient>(new ArrayList<Patient>());

    private MyPagedListHolder<Sample> samplePagination = new MyPagedListHolder<Sample>(new ArrayList<Sample>());

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(PatientActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.Patient.patients", active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Patient patient) {
        return new Breadcrumb(PatientActionBean.class.getName(), "detail", true, patient.getInstitutionalId(),
                active, "id", patient.getId());
    }

    public static Breadcrumb getSampleBreadcrumb(boolean active, Patient patient) {
        return new Breadcrumb(PatientActionBean.class.getName(), "samples", false, "cz.bbmri.entity.Sample.samples",
                active, "id", patient.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        if (patient == null) {
            if (id != null) {
                patient = patientDAO.get(id);
            }
        }

        return patient;
    }

    public MyPagedListHolder<Patient> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Patient> pagination) {
        this.pagination = pagination;
    }

    public MyPagedListHolder<Sample> getSamplePagination() {
        return samplePagination;
    }

    public void setSamplePagination(MyPagedListHolder<Sample> samplePagination) {
        this.samplePagination = samplePagination;
    }

    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"developer"})
    public Resolution all() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(true));

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(patientDAO.all());
        pagination.setEvent("all");

        return new ForwardResolution(View.Patient.ALL);

    }

    @HandlesEvent("detail")
    @RolesAllowed({"biobank_operator if ${biobankVisitor}", "developer"})
    public Resolution detail() {

        getPatient();

        if (patient == null) {
            return new ForwardResolution(View.Patient.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, patient.getBiobank()));
        getBreadcrumbs().add(PatientActionBean.getDetailBreadcrumb(true, patient));

        return new ForwardResolution(View.Patient.DETAIL);
    }

    @HandlesEvent("samples")
    @RolesAllowed({"biobank_operator if ${biobankVisitor}", "developer"})
    public Resolution samples() {

        getPatient();

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, patient.getBiobank()));
        getBreadcrumbs().add(PatientActionBean.getDetailBreadcrumb(false, patient));
        getBreadcrumbs().add(PatientActionBean.getSampleBreadcrumb(true, patient));

        samplePagination.initiate(getPage(), getOrderParam(), isDesc());
        samplePagination.setSource(new ArrayList<Sample>(patient.getSample()));
        samplePagination.setEvent("samples");
        samplePagination.setIdentifier(patient.getId());
        samplePagination.setIdentifierParam("id");

        return new ForwardResolution(View.Patient.SAMPLES);

    }

}
