package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.BiobankService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.3.13
 * Time: 1:09
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/{$event}/{biobankId}")
public class BiobankActionBean extends PermissionActionBean<Biobank> {

/* Variables */

    @SpringBean
    private BiobankService biobankService;

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "allBiobanks", false, "" +
                "cz.bbmri.entities.Biobank.biobanks", active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Long biobankId, Biobank biobank) {
        return new Breadcrumb(BiobankActionBean.class.getName(), "detail", true, biobank.getAbbreviation(),
                active, "biobankId", biobankId);
    }


    public BiobankActionBean() {
        //default
        setPagination(new MyPagedListHolder<Biobank>(new ArrayList<Biobank>()));
        setComponentManager(new ComponentManager(
                ComponentManager.BIOBANK_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("allBiobanks") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer", "biobank_operator"})
    public Resolution allBiobanks() {
        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(true));

        initiatePagination();
        if (getOrderParam() == null) {
            getPagination().setOrderParam("name");
        }
        getPagination().setEvent("allBiobanks");
        getPagination().setSource(biobankService.allOrderedBy(
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(BIOBANK_ALL);
    }


    /*
   Access rules here are used to check permission in biobank_sec_menuj.jsp.
   It can't check directly CreateActionBean because it is set as Wizard.
  */
    @HandlesEvent("createBiobank")
    @RolesAllowed({"administrator", "developer"})
    public Resolution createBiobank() {
        return new ForwardResolution(CreateActionBean.class);
    }

    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution detail() {
        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(true, biobankId, getBiobank()));
        return new ForwardResolution(BIOBANK_DETAIL_GENERAL);
    }


    @HandlesEvent("update")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution update() {
        if (!biobankService.update(getBiobank(), getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail")
                    .addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail")
                .addParameter("biobankId", biobankId);

    }

    @HandlesEvent("delete")
    @RolesAllowed({"developer"})
    public Resolution delete() {
        if (!biobankService.remove(biobankId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(),"allBiobanks");
        }

        successMsg(null);
        return new RedirectResolution(this.getClass(),"allBiobanks");
    }


}
