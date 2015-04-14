package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.SampleDAO;
import cz.bbmri.entity.Patient;
import cz.bbmri.entity.Sample;
import cz.bbmri.entity.webEntities.Breadcrumb;
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
@UrlBinding("/sample/{$event}/{id}")
public class SampleActionBean extends AuthorizationActionBean {

    @SpringBean
    private SampleDAO sampleDAO;

    private Long id;

    private Sample sample;

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
