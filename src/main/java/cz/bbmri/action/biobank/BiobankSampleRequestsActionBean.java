package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.SampleRequestService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.3.14
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/sampleRequests/{$event}/{biobankId}")
public class BiobankSampleRequestsActionBean extends PermissionActionBean<SampleQuestion> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private SampleRequestService sampleRequestService;

    public static Breadcrumb getBreadcrumb(boolean active, Long biobankId){
        return new Breadcrumb(BiobankSampleRequestsActionBean.class.getName(), "display",
                false, "cz.bbmri.entities.SampleRequest.sampleRequests", active, "biobankId", biobankId);
    }

    public BiobankSampleRequestsActionBean() {
        //default
        setPagination(new MyPagedListHolder<SampleQuestion>(new ArrayList<SampleQuestion>()));
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.SAMPLEQUESTION_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("biobankId");
    }



    @DefaultHandler
    @HandlesEvent("display")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution display() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankSampleRequestsActionBean.getBreadcrumb(true, biobankId));

        if (biobankId != null) {
            getPagination().setIdentifier(biobankId);
        }

        initiatePagination();
        if(getOrderParam() == null){
            getPagination().setOrderParam("created");
        }
        getPagination().setEvent("display");
        getPagination().setSource(sampleRequestService.getSortedSampleRequest(biobankId,
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(BIOBANK_DETAIL_SAMPLE_REQUESTS);
    }
}
