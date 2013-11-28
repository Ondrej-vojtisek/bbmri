package bbmri.action.biobank;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.BiobankAdministrator;
import bbmri.entities.enumeration.Permission;
import bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.3.13
 * Time: 1:09
 * To change this template use File | Settings | File Templates.
 */

@UrlBinding("/biobank/{$event}/{id}")
public class BiobankActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

/* Variables */

    @SpringBean
    private BiobankFacade biobankFacade;

    @ValidateNestedProperties(value = {
            @Validate(field = "name",
                    required = true, on = "update"),
            @Validate(field = "address",
                    required = true, on = "update")
    })
    private Biobank biobank;

    @Validate(required = true, on = {"addAdministrator", "setPermission"})
    private Permission permission;

    @Validate(required = true, on = {"addAdministrator", "removeAdministrator", "setPermission"})
    private Long adminId;

    /* Setter / Getter */

    public List<Biobank> getBiobanks() {
        return biobankFacade.all();
    }

    /*
    * This is weird - if the first _if_ is missing then only address is set during edit from .jsp
    * */
    public Biobank getBiobank() {
        if (biobank == null) {
            if (id != null) {
                biobank = biobankFacade.get(id);
            }
        }
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    // Used to specify biobank
    @Validate(required = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getAllowedManager() {
        return biobankFacade.hasPermission(Permission.MANAGER, id, getContext().getMyId());
    }

    public boolean getAllowedEditor() {
        return biobankFacade.hasPermission(Permission.EDITOR, id, getContext().getMyId());
    }

    public boolean getAllowedExecutor() {
        return biobankFacade.hasPermission(Permission.EXECUTOR, id, getContext().getMyId());
    }

    public boolean getAllowedVisitor() {
        return biobankFacade.hasPermission(Permission.VISITOR, id, getContext().getMyId());
    }


    public Set<BiobankAdministrator> getAdministrators() {

        if (id == null) {
            return null;
        }
        return getBiobank().getBiobankAdministrators();
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public List<Biobank> getMyBiobanks() {
        return biobankFacade.getBiobanksByUser(getContext().getMyId());
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("allBiobanks") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution allBiobanks() {
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
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
    public Resolution detail() {
        return new ForwardResolution(BIOBANK_DETAIL);
    }

    /* Event is here because of access permission definition*/
    @HandlesEvent("edit")
    @RolesAllowed({"biobank_operator if ${allowedEditor}"})
    public Resolution edit() {
        return new ForwardResolution(BIOBANK_DETAIL);
    }

    private Resolution administratorsResolution(boolean forward) {
        if (forward) {
            if (getAllowedManager()) {
                return new ForwardResolution(this.getClass(), "editAdministrators").addParameter("id", id);
            }
            return new ForwardResolution(BIOBANK_ADMINISTRATORS).addParameter("id", id);
        }

        if (getAllowedManager()) {
            return new RedirectResolution(this.getClass(), "editAdministrators").addParameter("id", id);
        }
        return new RedirectResolution(this.getClass(), "administrators").addParameter("id", id);
    }


    @HandlesEvent("administrators")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
    public Resolution administrators() {
        // Handles situation when administrator refuses his manager permission
        return administratorsResolution(true);
    }

    @HandlesEvent("editAdministrators")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution editAdministrators() {
        return new ForwardResolution(BIOBANK_ADMINISTRATORS_WRITE).addParameter("id", id);
    }

    @HandlesEvent("setPermission")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution setPermission() {

        ValidationErrors errors = new ValidationErrors();

        if(biobankFacade.changeBiobankAdministratorPermission(adminId, permission, errors)){
            successMsg(null);
            // It changes data - redirect necessary
            return administratorsResolution(false);
        }
        // It changes data - redirect necessary
        getContext().setValidationErrors(errors);
        return administratorsResolution(true);
    }

    @HandlesEvent("removeAdministrator")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution removeAdministrator() {

        ValidationErrors errors = new ValidationErrors();

        if (biobankFacade.removeBiobankAdministrator(adminId, errors)) {

            // Everything fine, print operation succeeded msg and continue to Redirect resolution

            successMsg(null);
            return administratorsResolution(false);
        }

        // Something went wrong - Forward Resolution with error msg

        getContext().setValidationErrors(errors);
        return administratorsResolution(true);
    }

    @HandlesEvent("update")
    @RolesAllowed({"biobank_operator if ${allowedEditor}"})
    public Resolution update() {

        ValidationErrors errors = new ValidationErrors();

        if (biobankFacade.updateBiobank(biobank, errors)) {
            successMsg(null);
            return new RedirectResolution(this.getClass(), "detail").addParameter("id", id);
        }
        getContext().setValidationErrors(errors);
        return new ForwardResolution(this.getClass(), "detail").addParameter("id", id);
    }

    @HandlesEvent("samples")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
    public Resolution samples() {
        return new ForwardResolution(BIOBANK_SAMPLES);
    }

    @HandlesEvent("delete")
    @RolesAllowed({"developer"})
    public Resolution delete() {

        ValidationErrors errors = new ValidationErrors();

        if (biobankFacade.removeBiobank(id, errors)) {
            successMsg(null);
            return new RedirectResolution(BIOBANK_ALL);
        }
        getContext().setValidationErrors(errors);
        return new ForwardResolution(BIOBANK_ALL);
    }

    @HandlesEvent("addAdministrator")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution addAdministrator() {

        ValidationErrors errors = new ValidationErrors();

        if(biobankFacade.assignAdministratorToBiobank(id, getContext().getMyId(), adminId, permission, errors)){
            successMsg(null);

            // Redirect resolution
            return administratorsResolution(false);
        }
        getContext().setValidationErrors(errors);
        // Forward resolution
        return administratorsResolution(true);
    }

    /* Only for permission check of primary menu */
    @HandlesEvent("viewBiobanks")
    @RolesAllowed({"administrator", "developer", "biobank_operator"})
    public Resolution viewBiobanks() {
        // This should never happen
        return null;
    }

}
