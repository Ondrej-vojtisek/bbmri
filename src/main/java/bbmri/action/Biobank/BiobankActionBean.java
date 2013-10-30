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

@UrlBinding("/Biobank/{$event}/{id}")
public class BiobankActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

/* Variables */

    @SpringBean
    private BiobankFacade biobankFacade;

    @ValidateNestedProperties(value = {
            @Validate(on = {"update"}, field = "name", required = true),
            @Validate(on = {"update"}, field = "address", required = true),
    })
    private Biobank biobank;

    private Long biobankAdministratorId;

    private Permission permission;

    /* Setter / Getter */

    public List<Biobank> getBiobanks() {
        return biobankFacade.all();
    }

    public Biobank getBiobank() {
        if (id != null) {
            return biobankFacade.get(id);
        }
        return null;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    // Used to specify biobank
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getAllowedManager() {
        logger.debug("HasPermissionManager: " + biobankFacade.hasPermission(Permission.MANAGER, id, getContext().getMyId()));

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


    public Set<BiobankAdministrator> getBiobankRoles() {

        if (id == null) {
            return null;
        }
        return getBiobank().getBiobankAdministrators();
    }

    public Long getBiobankAdministratorId() {
        return biobankAdministratorId;
    }

    public void setBiobankAdministratorId(Long biobankAdministratorId) {
        this.biobankAdministratorId = biobankAdministratorId;
    }

//    public BiobankAdministrator getBiobankAdministrator(){
//        return biobankFacade.getBiobankAdministrator(biobankAdministratorId);
//    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("allBiobanks") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution display() {
        return new ForwardResolution(BIOBANK_ALL);
    }

    @DontValidate
    @HandlesEvent("createBiobank")
    public Resolution createBiobank() {
        return new ForwardResolution(BIOBANK_CREATE);
    }


    @DontValidate
    @HandlesEvent("edit")
    @RolesAllowed({"biobank_operator if ${allowedEditor}"})
    public Resolution edit() {
        return new ForwardResolution(BIOBANK_EDIT);
    }

    @DontValidate
    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
    public Resolution detail() {
        return new ForwardResolution(BIOBANK_DETAIL);
    }

    @DontValidate
    @HandlesEvent("administrators")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
    public Resolution administrators() {
        // Handles situation when administrator refuses his manager permission
        if (getAllowedManager()) {
            return new ForwardResolution(BIOBANK_ADMINISTRATORS_WRITE);
        }
        return new ForwardResolution(BIOBANK_ADMINISTRATORS);
    }

    @DontValidate
    @HandlesEvent("administratorsEdit")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution administratorsEdit() {
        return new ForwardResolution(BIOBANK_ADMINISTRATORS_WRITE);
    }

    @DontValidate
    @HandlesEvent("setPermission")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution setPermission() {
        biobankFacade.changeBiobankAdministratorPermission(biobankAdministratorId, permission, getContext().getMyId());
        // It changes data - redirect necessary
        return new RedirectResolution(BiobankActionBean.class, "administrators").addParameter("id", id);
    }

    @DontValidate
    @HandlesEvent("removeAdministrator")
    @RolesAllowed({"biobank_operator if ${allowedManager}"})
    public Resolution removeAdministrator() {
        biobankFacade.removeBiobankAdministrator(biobankAdministratorId, getContext().getMyId());
        // It changes data - redirect necessary
        return new RedirectResolution(BiobankActionBean.class, "administrators").addParameter("id", id);
    }


    @DontValidate
    @HandlesEvent("samples")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedVisitor}"})
    public Resolution samples() {
        return new ForwardResolution(BIOBANK_SAMPLES);
    }


    @DontValidate
    @HandlesEvent("create")
    @RolesAllowed({"developer", "administrator"})
    public Resolution create() {
        /*

        User resDB = userService.get(administrator.getId());

        BiobankAdministrator ba = resDB.getBiobankAdministrator();
        Biobank biobankDB = biobankService.get(ba.getBiobank().getId());

        if (biobankDB != null) {
            getContext().getMessages().add(
                         new SimpleMessage("Selected user is already an administrator of a biobank")
                 );
            return new ForwardResolution(this.getClass(), "display");
        }
        biobankService.create(newBiobank, administrator.getId());
        */
        return new ForwardResolution(this.getClass(), "display");
    }
}
