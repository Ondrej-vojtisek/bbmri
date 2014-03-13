package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Set;

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
    private BiobankFacade biobankFacade;

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
        initiatePagination();
        if(getOrderParam() == null){
            getPagination().setOrderParam("name");
        }
        getPagination().setEvent("allBiobanks");
        getPagination().setSource(biobankFacade.allOrderedBy(
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
        return new ForwardResolution(BIOBANK_DETAIL_GENERAL);
    }


    @HandlesEvent("update")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution update() {
        if (!biobankFacade.updateBiobank(getBiobank(), getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail").addParameter("biobankId", biobankId);

    }

    @HandlesEvent("delete")
    @RolesAllowed({"developer"})
    public Resolution delete() {

        if (!biobankFacade.removeBiobank(biobankId, getContext().getValidationErrors(),
                getContext().getPropertiesStoragePath(), getContext().getMyId())) {
            return new ForwardResolution(BIOBANK_ALL);
        }

        successMsg(null);
        return new RedirectResolution(BIOBANK_ALL);
    }


}
