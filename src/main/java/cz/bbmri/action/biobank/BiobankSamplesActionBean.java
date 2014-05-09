package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.sample.Sample;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.SampleService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/biobank/samples/{$event}/{biobankId}")
public class BiobankSamplesActionBean extends PermissionActionBean<Sample> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private SampleService sampleService;

    public static Breadcrumb getBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(BiobankSamplesActionBean.class.getName(),
                "display", false, "cz.bbmri.entities.sample.Sample.samples",
                active, "biobankId", biobankId);
    }

    public BiobankSamplesActionBean() {
        // default
        setPagination(new MyPagedListHolder<Sample>(new ArrayList<Sample>()));
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.SAMPLE_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("biobankId");
    }



    @DefaultHandler
    @HandlesEvent("display")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution display() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankSamplesActionBean.getBreadcrumb(true, biobankId));

        if (biobankId != null) {
            getPagination().setIdentifier(biobankId);
        }

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("sampleIdentification.sampleId");
        }
        getPagination().setEvent("display");
        getPagination().setSource(sampleService.getSortedSamples(biobankId,
                getPagination().getOrderParam(), getPagination().getDesc()));
        return new ForwardResolution(BIOBANK_DETAIL_SAMPLES);
    }

}
