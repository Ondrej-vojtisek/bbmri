package cz.bbmri.action.biobank;

import com.google.common.base.CharMatcher;
import cz.bbmri.action.FindActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.service.BiobankAdministratorService;
import cz.bbmri.service.BiobankService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.*;
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

    @SpringBean
    private BiobankService biobankService;

    @SpringBean
    private BiobankAdministratorService biobankAdministratorService;

    @Validate(on = {"addAdministrator", "done"}, required = true)
    private Long adminId;

    @ValidateNestedProperties(value = {
            @Validate(on = {"confirmStep1", "done"}, field = "abbreviation", required = true),
            @Validate(on = {"confirmStep1", "done"}, field = "name", required = true),
            @Validate(on = {"confirmStep1", "done"}, field = "address", required = true)
    })
    private Biobank biobank;

    public CreateActionBean() {
        //default
        setComponentManager(new ComponentManager());
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public User getNewAdministrator() {
        if (adminId != null) {
            return userService.get(adminId);
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
        boolean result = false;
        // create biobank
        result = biobankService.create(biobank, getContext().getValidationErrors());
        if(result){
            Biobank biobankDB = biobankService.getBiobankByAbbreviation(biobank.getAbbreviation());
            // Assign first admin
            result = biobankAdministratorService.assignAdministrator(biobankDB.getId(),
                    adminId, Permission.MANAGER, getContext().getValidationErrors(), getContext().getMyId());
        }

        if(!result){
            return new ForwardResolution(BiobankActionBean.class);
        }
        successMsg(null);
        return new RedirectResolution(BiobankActionBean.class);
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


    @ValidationMethod
    public void validateAbbreviation(ValidationErrors errors) {

        if (!CharMatcher.ASCII.matchesAllOf(biobank.getAbbreviation())) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.biobank.CreateActionBean.notAscii"));
        }

        if (biobankService.getBiobankByAbbreviation(biobank.getAbbreviation()) != null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.biobank.CreateActionBean.abbreviationExists"));
        }

    }


}
