package cz.bbmri.action.biobank;

import cz.bbmri.action.FindActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.User;
import cz.bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.10.13
 * Time: 16:25
 * To change this template use File | Settings | File Templates.
 */
@Wizard(startEvents = {"display"})
@UrlBinding("/biobank/create/{$event}")
public class CreateActionBean extends FindActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

   /* Variables */

//    @SpringBean
//    private UserFacade userFacade;

    @SpringBean
    private BiobankFacade biobankFacade;

    @Validate(on = {"addAdministrator", "done"}, required = true)
    private Long adminId;

    @ValidateNestedProperties(value = {
            @Validate(on = {"confirmStep1", "done"}, field = "name", required = true),
            @Validate(on = {"confirmStep1", "done"}, field = "address", required = true)
    })
    private Biobank newBiobank;

    public Biobank getNewBiobank() {
        return newBiobank;
    }

    public void setNewBiobank(Biobank newBiobank) {
        this.newBiobank = newBiobank;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public User getNewAdministrator() {
        if (adminId != null) {
            return userFacade.get(adminId);
        }
        return null;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("display")
    @RolesAllowed({"administrator", "developer"})
    public Resolution display() {
        return new ForwardResolution(BIOBANK_CREATE_GENERAL);
    }

    @DontValidate
    @HandlesEvent("backFromStep2")
    @RolesAllowed({"administrator", "developer"})
    public Resolution backFromStep2() {
        return new ForwardResolution(BIOBANK_CREATE_GENERAL);
    }

    @DontValidate
    @HandlesEvent("administrators")
    @RolesAllowed({"administrator", "developer"})
    public Resolution administrators() {
        return new ForwardResolution(BIOBANK_CREATE_ADMINISTRATORS);
    }

    @DontValidate
    @HandlesEvent("backFromStep3")
    @RolesAllowed({"administrator", "developer"})
    public Resolution backFromStep3() {
        return new ForwardResolution(BIOBANK_CREATE_ADMINISTRATORS);
    }


    @HandlesEvent("confirmStep3")
    @RolesAllowed({"administrator", "developer"})
    public Resolution confirmStep3() {
        ValidationErrors errors = new ValidationErrors();
        biobankFacade.createBiobank(newBiobank, adminId, errors, getContext().getPropertiesStoragePath());

        if(biobankFacade.createBiobank(newBiobank, adminId, errors, getContext().getPropertiesStoragePath())){
            successMsg(null);
            return new RedirectResolution(BiobankActionBean.class, "allBiobanks");
        }

        getContext().setValidationErrors(errors);
        return new ForwardResolution(BiobankActionBean.class, "allBiobanks");
    }

    @HandlesEvent("confirmStep1")
    @RolesAllowed({"administrator", "developer"})
    public Resolution confirmStep1() {
        return new ForwardResolution(this.getClass(), "administrators");
    }

    /* Add administrator */
    @HandlesEvent("confirmStep2")
    @RolesAllowed({"administrator", "developer"})
    public Resolution confirmStep2() {
        return new ForwardResolution(BIOBANK_CREATE_CONFIRM);
    }

    @DontValidate
    @HandlesEvent("find")
    @RolesAllowed({"administrator", "developer"})
    public Resolution find() {
        logger.debug("UserFind: " + getUserFind());

        return new ForwardResolution(BIOBANK_CREATE_ADMINISTRATORS)
                .addParameter("UserFind", getUserFind());
    }


}
