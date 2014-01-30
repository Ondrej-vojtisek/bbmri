package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.*;
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
@UrlBinding("/biobank/{$event}/{biobankId}")
public class BiobankActionBean extends PermissionActionBean {

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
        return biobankFacade.getAllPatients(biobankId);
    }

    public List<Sample> getSamples() {
        return biobankFacade.getAllSamples(biobankId);
    }

    /*
    * This is weird - if the first _if_ is missing then only address is set during edit from .jsp
    * */
    public Biobank getBiobank() {
        if (biobank == null) {
            if (biobankId != null) {
                biobank = biobankFacade.get(biobankId);
            }
        }
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public Set<BiobankAdministrator> getAdministrators() {

        if (biobankId == null) {
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

    public List<SampleQuestion> getSampleQuestions() {
        if (biobankId == null) {
            return null;
        }

        return biobankFacade.getBiobankSampleQuestions(biobankId);
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
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution detail() {
        return new ForwardResolution(BIOBANK_DETAIL_GENERAL);
    }

    /* Event is here because of access permission definition*/
    @HandlesEvent("edit")
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution edit() {
        return new ForwardResolution(BIOBANK_DETAIL_GENERAL);
    }

    @DontValidate
    @HandlesEvent("administratorsResolution")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution administratorsResolution() {
        return new ForwardResolution(BIOBANK_DETAIL_ADMINISTRATORS).addParameter("biobankId", biobankId);
    }

//    @HandlesEvent("administrators")
//    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
//    public Resolution administrators() {
//        // Handles situation when administrator refuses his manager permission
//        return new ForwardResolution(BIOBANK_ADMINISTRATORS).addParameter("biobankId", biobankId);
//        //      return administratorsResolution(true);
//    }

    @HandlesEvent("editAdministrators")
    @RolesAllowed({"biobank_operator if ${allowedBiobankManager}"})
    public Resolution editAdministrators() {
        return new ForwardResolution(BIOBANK_DETAIL_ADMINISTRATORS).addParameter("biobankId", biobankId);
    }

    @HandlesEvent("setPermission")
    @RolesAllowed({"biobank_operator if ${allowedBiobankManager}"})
    public Resolution setPermission() {

        if (!biobankFacade.changeAdministratorPermission(adminId, permission,
                getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(BIOBANK_DETAIL_ADMINISTRATORS).addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(BIOBANK_DETAIL_ADMINISTRATORS).addParameter("biobankId", biobankId);


    }

    @HandlesEvent("removeAdministrator")
    @RolesAllowed({"biobank_operator if ${allowedBiobankManager or isMyAccount}"})
    public Resolution removeAdministrator() {

        if (!biobankFacade.removeAdministrator(adminId,
                getContext().getValidationErrors(), getContext().getMyId())) {

            return new ForwardResolution(BIOBANK_DETAIL_ADMINISTRATORS).addParameter("biobankId", biobankId);
            //   return new RedirectResolution(this.getClass(), "administrators").addParameter("biobankId", biobankId);
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
    @RolesAllowed({"biobank_operator if ${allowedBiobankEditor}"})
    public Resolution update() {
        if (!biobankFacade.updateBiobank(biobank, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail").addParameter("biobankId", biobankId);

    }

    @HandlesEvent("samples")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution samples() {
        return new ForwardResolution(BIOBANK_DETAIL_SAMPLES);
    }

    @HandlesEvent("patients")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution patients() {
        return new ForwardResolution(BIOBANK_DETAIL_PATIENTS);
    }

    @HandlesEvent("sampleQuestions")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution sampleQuestions() {
        return new ForwardResolution(BIOBANK_DETAIL_SAMPLE_QUESTIONS);
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

    @HandlesEvent("addAdministrator")
    @RolesAllowed({"biobank_operator if ${allowedBiobankManager}"})
    public Resolution addAdministrator() {

        if (!biobankFacade.assignAdministrator(biobankId, adminId, permission, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(BIOBANK_DETAIL_ADMINISTRATORS).addParameter("biobankId", biobankId);
        }
        successMsg(null);
        return new RedirectResolution(BIOBANK_DETAIL_ADMINISTRATORS).addParameter("biobankId", biobankId);

    }

    /* Only for permission check of primary menu */
    @HandlesEvent("viewBiobanks")
    @RolesAllowed({"administrator", "developer", "biobank_operator"})
    public Resolution viewBiobanks() {
        // This should never happen
        return null;
    }

}
