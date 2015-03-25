package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.ContactDAO;
import cz.bbmri.dao.CountryDAO;
import cz.bbmri.entity.*;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.ComponentManager;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/biobank/{$event}/{id}")
public class BiobankActionBean extends ComponentActionBean {

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private ContactDAO contactDAO;

    @SpringBean
    private CountryDAO countryDAO;

    private Integer id;

    private Biobank biobank;

    private Contact contact;

    private Integer countryId;

    private MyPagedListHolder<Biobank> pagination;

    private MyPagedListHolder<Attachment> attachmentPagination;

    public BiobankActionBean() {
        setComponentManager(new ComponentManager(ComponentManager.BIOBANK_DETAIL));
    }

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

    @DefaultHandler
    @HandlesEvent("all") /* Necessary for stripes security tag*/
    @RolesAllowed("authorized")
    public Resolution all() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(true));

        pagination = new MyPagedListHolder<Biobank>(biobankDAO.all());
        pagination.initiate(getPage(), getOrderParam(), isDesc());
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
    @RolesAllowed({"biobank_operator", "developer"})
    public Resolution attachments() {
        getBiobank();

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, getBiobank()));
        getBreadcrumbs().add(BiobankActionBean.getAttachmentsBreadcrumb(true, getBiobank()));

        attachmentPagination = new MyPagedListHolder<Attachment>(new ArrayList<Attachment>(biobank.getAttachment()));
        attachmentPagination.initiate(getPage(), getOrderParam(), isDesc());
        attachmentPagination.setEvent("attachments");
        attachmentPagination.setIdentifier(biobank.getId());
        attachmentPagination.setIdentifierParam("id");

        return new ForwardResolution(View.Biobank.ATTACHMENTS);
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
    @RolesAllowed({"biobank_operator"})
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

}
