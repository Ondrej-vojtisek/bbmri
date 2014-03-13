package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Sample;
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
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 7.3.14
 * Time: 22:45
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/samples/{$event}/{biobankId}")
public class BiobankSamplesActionBean extends PermissionActionBean<Sample> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private BiobankFacade biobankFacade;

    @SpringBean
    private SampleFacade sampleFacade;

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

        if (biobankId != null) {
            getPagination().setIdentifier(biobankId);
        }

        initiatePagination();
        if(getOrderParam() == null){
            getPagination().setOrderParam("sampleIdentification.sampleId");
        }
        getPagination().setEvent("display");
        getPagination().setSource(sampleFacade.getSortedSamples(biobankId,
                getPagination().getOrderParam(), getPagination().getDesc()));
        return new ForwardResolution(BIOBANK_DETAIL_SAMPLES);
    }

}
