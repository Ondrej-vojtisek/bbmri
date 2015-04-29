package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.ContactDAO;
import cz.bbmri.dao.CountryDAO;
import cz.bbmri.dao.SampleDAO;
import cz.bbmri.entity.*;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/biobank/{$event}/{id}")
public class BiobankActionBean extends AuthorizationActionBean {

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private ContactDAO contactDAO;

    @SpringBean
    private CountryDAO countryDAO;

    @SpringBean
    private SampleDAO sampleDAO;

    private Integer id;

    private Biobank biobank;

    private Contact contact;

    private Integer countryId;

    private MyPagedListHolder<Biobank> pagination = new MyPagedListHolder<Biobank>(new ArrayList<Biobank>());

    private MyPagedListHolder<Attachment> attachmentPagination = new MyPagedListHolder<Attachment>(new ArrayList<Attachment>());

    private MyPagedListHolder<Patient> patientPagination = new MyPagedListHolder<Patient>(new ArrayList<Patient>());

    private MyPagedListHolder<Sample> samplePagination = new MyPagedListHolder<Sample>(new ArrayList<Sample>());

    private MyPagedListHolder<Withdraw> withdrawPagination = new MyPagedListHolder<Withdraw>(new ArrayList<Withdraw>());

    private MyPagedListHolder<Question> questionPagination = new MyPagedListHolder<Question>(new ArrayList<Question>());


    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.Biobank.biobanks", active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Biobank biobank) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "detail", true, biobank.getAcronym(),
                active, "id", biobank.getId());
    }

    public static Breadcrumb getAttachmentsBreadcrumb(boolean active, Biobank biobank) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "attachments", false, "cz.bbmri.entity.Biobank.attachment",
                active, "id", biobank.getId());
    }

    public static Breadcrumb getBiobankUserBreadcrumb(boolean active, Biobank biobank) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "biobankuser", false, "cz.bbmri.entity.BiobankUser.biobankUsers",
                active, "id", biobank.getId());
    }

    public static Breadcrumb getPatientsBreadcrumb(boolean active, Biobank biobank) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "patients", false, "cz.bbmri.entity.Patient.patients",
                active, "id", biobank.getId());
    }

    public static Breadcrumb getSamplesBreadcrumb(boolean active, Biobank biobank) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "samples", false, "cz.bbmri.entity.Sample.samples",
                active, "id", biobank.getId());
    }


    public static Breadcrumb getWithdrawsBreadcrumb(boolean active, Biobank biobank) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "withdraws", false, "cz.bbmri.entity.Withdraw.withdraws",
                active, "id", biobank.getId());
    }

    public static Breadcrumb getQuestionsBreadcrumb(boolean active, Biobank biobank) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "questions", false, "cz.bbmri.entity.Question.questions",
                active, "id", biobank.getId());
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public Biobank getBiobank() {
        if (biobank == null) {
            if (id != null) {
                biobank = biobankDAO.get(id);
            }
        }

        return biobank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {

        setAuthBiobankId(id);
        this.id = id;
    }

    public Contact getContact() {
        if (contact == null) {
            if (getBiobank() != null) {
                contact = biobank.getContact();
            }
        }

        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public MyPagedListHolder<Biobank> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Biobank> pagination) {
        this.pagination = pagination;
    }

    public MyPagedListHolder<Attachment> getAttachmentPagination() {
        return attachmentPagination;
    }

    public void setAttachmentPagination(MyPagedListHolder<Attachment> attachmentPagination) {
        this.attachmentPagination = attachmentPagination;
    }

    public MyPagedListHolder<Patient> getPatientPagination() {
        return patientPagination;
    }

    public void setPatientPagination(MyPagedListHolder<Patient> patientPagination) {
        this.patientPagination = patientPagination;
    }

    public MyPagedListHolder<Withdraw> getWithdrawPagination() {
        return withdrawPagination;
    }

    public void setWithdrawPagination(MyPagedListHolder<Withdraw> withdrawPagination) {
        this.withdrawPagination = withdrawPagination;
    }

    public MyPagedListHolder<Question> getQuestionPagination() {
        return questionPagination;
    }

    public void setQuestionPagination(MyPagedListHolder<Question> questionPagination) {
        this.questionPagination = questionPagination;
    }

    public MyPagedListHolder<Sample> getSamplePagination() {
        return samplePagination;
    }

    public void setSamplePagination(MyPagedListHolder<Sample> samplePagination) {
        this.samplePagination = samplePagination;
    }

    /**
     * @return all samples of a biobank
     */
    public List<Sample> getSamples() {
        getBiobank();
        if (biobank == null) {
            return null;
        }

        return sampleDAO.getAllByBiobank(biobank);
    }

    @DefaultHandler
    @HandlesEvent("all") /* Necessary for stripes security tag*/
    @RolesAllowed("authorized")
    public Resolution all() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(true));

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(biobankDAO.all());
        pagination.setEvent("all");

        // TODO jak radit podle Contactu ?

        //        default ordering
        //        if (getOrderParam() == null) {
        //            setOrderParam("surname");
        //            getPagination().setOrderParam("surname");
        //            getPagination().setDesc(false);
        //        }

        return new ForwardResolution(View.Biobank.ALL);
    }

    @HandlesEvent("attachments") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${biobankVisitor}", "admin", "developer"})
    public Resolution attachments() {
        getBiobank();

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getBiobank()));
        getBreadcrumbs().add(BiobankActionBean.getAttachmentsBreadcrumb(true, getBiobank()));

        attachmentPagination.initiate(getPage(), getOrderParam(), isDesc());
        attachmentPagination.setSource(new ArrayList<Attachment>(biobank.getAttachment()));
        attachmentPagination.setEvent("attachments");
        attachmentPagination.setIdentifier(biobank.getId());
        attachmentPagination.setIdentifierParam("id");

        return new ForwardResolution(View.Biobank.ATTACHMENTS);
    }

    @HandlesEvent("biobankuser") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${biobankVisitor}", "admin", "developer"})
    public Resolution biobankuser() {
        getBiobank();

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getBiobank()));
        getBreadcrumbs().add(BiobankActionBean.getBiobankUserBreadcrumb(true, getBiobank()));

        return new ForwardResolution(View.Biobank.BIOBANK_USER);
    }

    @HandlesEvent("patients") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${biobankVisitor}", "admin", "developer"})
    public Resolution patients() {
        getBiobank();

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getBiobank()));
        getBreadcrumbs().add(BiobankActionBean.getPatientsBreadcrumb(true, getBiobank()));

        patientPagination.initiate(getPage(), getOrderParam(), isDesc());
        patientPagination.setSource(new ArrayList<Patient>(biobank.getPatient()));
        patientPagination.setEvent("patients");
        patientPagination.setIdentifier(biobank.getId());
        patientPagination.setIdentifierParam("id");

        return new ForwardResolution(View.Biobank.PATIENTS);
    }

    @HandlesEvent("samples") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${biobankVisitor}", "admin", "developer"})
    public Resolution samples() {
        getBiobank();

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getBiobank()));
        getBreadcrumbs().add(BiobankActionBean.getSamplesBreadcrumb(true, getBiobank()));

        samplePagination.initiate(getPage(), getOrderParam(), isDesc());
        samplePagination.setSource(new ArrayList<Sample>(getSamples()));
        samplePagination.setEvent("samples");
        samplePagination.setIdentifier(biobank.getId());
        samplePagination.setIdentifierParam("id");

        return new ForwardResolution(View.Biobank.SAMPLES);
    }

    @HandlesEvent("withdraws") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${biobankVisitor}", "admin", "developer"})
    public Resolution withdraws() {
        getBiobank();

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getBiobank()));
        getBreadcrumbs().add(BiobankActionBean.getWithdrawsBreadcrumb(true, getBiobank()));

        withdrawPagination.initiate(getPage(), getOrderParam(), isDesc());
        withdrawPagination.setSource(new ArrayList<Withdraw>(biobank.getWithdraw()));
        withdrawPagination.setEvent("withdraws");
        withdrawPagination.setIdentifier(biobank.getId());
        withdrawPagination.setIdentifierParam("id");

        return new ForwardResolution(View.Biobank.WITHDRAWS);
    }

    @HandlesEvent("questions") /* Necessary for stripes security tag*/
    @RolesAllowed({"biobank_operator if ${biobankVisitor}", "admin", "developer"})
    public Resolution questions() {
        getBiobank();

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getBiobank()));
        getBreadcrumbs().add(BiobankActionBean.getQuestionsBreadcrumb(true, getBiobank()));

        questionPagination.initiate(getPage(), getOrderParam(), isDesc());
        questionPagination.setSource(new ArrayList<Question>(biobank.getQuestion()));
        questionPagination.setEvent("questions");
        questionPagination.setIdentifier(biobank.getId());
        questionPagination.setIdentifierParam("id");

        return new ForwardResolution(View.Biobank.QUESTIONS);
    }


    @HandlesEvent("detail") /* Necessary for stripes security tag*/
    @RolesAllowed("authorized")
    public Resolution detail() {
        getBiobank();

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(true, getBiobank()));


        if (getBiobank().getContact() == null) {
            Contact contact = new Contact();
            contact.setBiobank(biobank);
            contactDAO.save(contact);
            return new RedirectResolution(BiobankActionBean.class, "detail").addParameter("id", id);
        }

        return new ForwardResolution(View.Biobank.DETAIL);
    }

    // TODO instance based authorization

    @HandlesEvent("save")
    @RolesAllowed({"biobank_operator if ${biobankEditor}", "developer"})
    public Resolution save() {

        if (countryId != null) {
            Country country = countryDAO.get(countryId);
            if (country != null) {
                contact.setCountry(country);
            }
        }

        biobankDAO.save(biobank);
        contactDAO.save(contact);
        return new RedirectResolution(BiobankActionBean.class, "detail").addParameter("id", biobank.getId());
    }

    public List<Biobank> getBiobanks() {
        return biobankDAO.all();
    }

}
