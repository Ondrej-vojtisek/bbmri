package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/biobank/withdraw/{$event}/{biobankId}")
public class BiobankWithdrawSamplesActionBean extends PermissionActionBean<Biobank> {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public static Breadcrumb getBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(BiobankWithdrawSamplesActionBean.class.getName(),
                "display", false, "cz.bbmri.action.biobank.BiobankWithdrawSamplesActionBean.selfRequisition",
                active, "biobankId", biobankId);
    }

    public BiobankWithdrawSamplesActionBean() {
        // default
        // setPagination(new MyPagedListHolder<Sample>(new ArrayList<Sample>()));
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.BIOBANK_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        //getPagination().setIdentifierParam("biobankId");
    }



    @DefaultHandler
    @HandlesEvent("display")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution display() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankWithdrawSamplesActionBean.getBreadcrumb(true, biobankId));

        return new ForwardResolution(BIOBANK_DETAIL_WITHDRAWN);
    }
}
