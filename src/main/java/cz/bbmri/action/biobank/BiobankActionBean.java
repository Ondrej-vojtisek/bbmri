package cz.bbmri.action.biobank;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.Patient;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
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
@HttpCache(allow = false)
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

    public List<Patient> getPatients() {
        return biobankFacade.getAllPatients(id);
    }

    public List<Sample> getSamples() {
        return biobankFacade.getAllSamples(id);
    }

    private Long userAdminId;

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


    public Long getUserAdminId() {
        return userAdminId;
    }

    public void setUserAdminId(Long userAdminId) {
        this.userAdminId = userAdminId;
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

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(userAdminId);
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

    @DontValidate
    @HandlesEvent("administratorsResolution")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
    public Resolution administratorsResolution() {
        return new ForwardResolution(BIOBANK_ADMINISTRATORS).addParameter("id", id);
    }

//    @HandlesEvent("administrators")
//    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
//    public Resolution administrators() {
//        // Handles situation when administrator refuses his manager permission
//        return new ForwardResolution(BIOBANK_ADMINISTRATORS).addParameter("id", id);
//        //      return administratorsResolution(true);
//    }

    @HandlesEvent("editAdministrators")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution editAdministrators() {
        return new ForwardResolution(BIOBANK_ADMINISTRATORS).addParameter("id", id);
    }

    @HandlesEvent("setPermission")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution setPermission() {

        if (!biobankFacade.changeAdministratorPermission(adminId, permission,
                getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(BIOBANK_ADMINISTRATORS).addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(BIOBANK_ADMINISTRATORS).addParameter("id", id);


    }

    @HandlesEvent("removeAdministrator")
    @RolesAllowed({"biobank_operator if ${allowedManager or isMyAccount}"})
    public Resolution removeAdministrator() {

        if (!biobankFacade.removeAdministrator(adminId,
                getContext().getValidationErrors(), getContext().getMyId())) {

            return new ForwardResolution(BIOBANK_ADMINISTRATORS).addParameter("id", id);
            //   return new RedirectResolution(this.getClass(), "administrators").addParameter("id", id);
        }

        // TODO: pokud jsem smazal sebe tak jdi na MyProjects, pokud jsem smazal nekoho jineho tak jdi na administrators

        successMsg(null);
        return new RedirectResolution(this.getClass());

    }

    public String getRemoveQuestion() {
        if (getIsMyAccount()) {
            return this.getName() + ".questionRemoveMyself";
        }
        return this.getName() + ".questionRemoveAdministrator";
    }

    @HandlesEvent("update")
    @RolesAllowed({"biobank_operator if ${allowedEditor}"})
    public Resolution update() {
        if (!biobankFacade.updateBiobank(biobank, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail").addParameter("id", id);

    }

    @HandlesEvent("samples")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
    public Resolution samples() {
        return new ForwardResolution(BIOBANK_SAMPLES);
    }

    @HandlesEvent("patients")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
    public Resolution patients() {
        return new ForwardResolution(BIOBANK_PATIENTS);
    }

    @HandlesEvent("delete")
    @RolesAllowed({"developer"})
    public Resolution delete() {

        if (!biobankFacade.removeBiobank(id, getContext().getValidationErrors(),
                getContext().getPropertiesStoragePath(), getContext().getMyId())) {
            return new ForwardResolution(BIOBANK_ALL);
        }

        successMsg(null);
        return new RedirectResolution(BIOBANK_ALL);


    }

    @HandlesEvent("addAdministrator")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution addAdministrator() {

        if (!biobankFacade.assignAdministrator(id, adminId, permission, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(BIOBANK_ADMINISTRATORS).addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(BIOBANK_ADMINISTRATORS).addParameter("id", id);

    }

    /* Only for permission check of primary menu */
    @HandlesEvent("viewBiobanks")
    @RolesAllowed({"administrator", "developer", "biobank_operator"})
    public Resolution viewBiobanks() {
        // This should never happen
        return null;
    }

}
