package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
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
@UrlBinding("/sample/{$event}/{id}")
public class SampleActionBean extends AuthorizationActionBean {

    @SpringBean
    private SampleDAO sampleDAO;

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private RetrievedDAO retrievedDAO;

    @SpringBean
    private SexDAO sexDAO;

    @SpringBean
    private MaterialTypeDAO materialTypeDAO;

    private Long id;
    private Sample sample;


    // Search params
    private Integer biobankId;
    private Short retrievedId;
    private Short available;
    private Short total;
    private String grading;
    private Short sexId;
    private Boolean sts;
    private Integer materialTypeId;

    private MyPagedListHolder<Sample> pagination = new MyPagedListHolder<Sample>(new ArrayList<Sample>());

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(SampleActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.Sample.samples", active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Sample sample) {
        return new Breadcrumb(SampleActionBean.class.getName(), "detail", true, sample.getInstitutionalId(),
                active, "id", sample.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(Integer biobankId) {
        this.biobankId = biobankId;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Sample getSample() {
        if (sample == null) {
            if (id != null) {
                sample = sampleDAO.get(id);
            }
        }

        return sample;
    }

    public Short getRetrievedId() {
        return retrievedId;
    }

    public void setRetrievedId(Short retrievedId) {
        this.retrievedId = retrievedId;
    }

    public List<Sample> getSampleSearch(){
        Biobank biobank = biobankDAO.get(biobankId);

        if(biobank == null){
            return null;
        }

        Retrieved retrieved = retrievedDAO.get(retrievedId);

        Sex sex = sexDAO.get(sexId);

        MaterialType materialType = materialTypeDAO.get(materialTypeId);

        return sampleDAO.getAllByBiobank(biobank);

    }


    public MyPagedListHolder<Sample> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Sample> pagination) {
        this.pagination = pagination;
    }

    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"developer"})
    public Resolution all() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(true));

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(new ArrayList<Sample>(sampleDAO.all()));
        pagination.setEvent("all");

        return new ForwardResolution(View.Sample.ALL);

    }

    @HandlesEvent("detail")
    @RolesAllowed({"biobank_operator if ${biobankVisitor}", "developer"})
    public Resolution detail() {

        getSample();

        if (sample == null) {
            return new ForwardResolution(View.Sample.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, sample.getPatient().getBiobank()));
        getBreadcrumbs().add(PatientActionBean.getDetailBreadcrumb(false, sample.getPatient()));
        getBreadcrumbs().add(PatientActionBean.getSampleBreadcrumb(false, sample.getPatient()));
        getBreadcrumbs().add(SampleActionBean.getDetailBreadcrumb(true, sample));

        return new ForwardResolution(View.Sample.DETAIL);
    }

}
