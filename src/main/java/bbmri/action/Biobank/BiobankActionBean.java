package bbmri.action.biobank;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.User;
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

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.3.13
 * Time: 1:09
 * To change this template use File | Settings | File Templates.
 */

@UrlBinding("/Biobank/{$event}/{biobank.id}")
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


    /* Setter / Getter */

    public List<Biobank> getBiobanks() {
        return biobankFacade.all();
    }

    public Biobank getBiobank() {
        if (biobank == null) {
            biobank = biobankFacade.get(id);
        }
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

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
    @RolesAllowed({"biobank_operator if ${allowedVisitor}"})
    public Resolution detail() {
        return new ForwardResolution(BIOBANK_DETAIL);
    }

    @DontValidate
    @HandlesEvent("administrators")
    @RolesAllowed({"biobank_operator if ${allowedVisitor}"})
    public Resolution administrators() {
        return new ForwardResolution(BIOBANK_ADMINISTRATORS);
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

//    @DontValidate
//    public Resolution update() {
//        biobankService.update(biobank);
//        return new ForwardResolution(this.getClass(), "display");
//    }

//    @DontValidate
//    public Resolution removeAll() {
//        Integer removed = 0;
//        if (selected != null) {
//            for (Long id : selected) {
//                if (id.equals(getContext().getMyId())) {
//                           /*you can't remove yourself*/
//                    return new ForwardResolution(this.getClass(), "display");
//                }
//                biobankService.removeAdministratorFromBiobank(id, getBiobank().getId());
//                removed++;
//            }
//        }
//        getContext().getMessages().add(
//                new SimpleMessage("{0} administrators removed", removed)
//        );
//        return new RedirectResolution(this.getClass(), "display");
//    }

//    @DontValidate
//    public Resolution changeOwnership() {
//
//        logger.debug("New user = " + user);
//
//        getContext().getMessages().add(
//                new SimpleMessage("Ownership of biobank was changed")
//        );
//        return new RedirectResolution(this.getClass(), "display");
//    }

//    @DontValidate
//    public Resolution assignAll() {
//        Integer assigned = 0;
//        if (selectedApprove != null) {
//            for (Long userId : selectedApprove) {
//                // TODO: fix
//                //biobankService.assignAdministrator(userId, getBiobank().getId());
//                assigned++;
//            }
//        }
//        getContext().getMessages().add(
//                new SimpleMessage("{0} users assigned", assigned)
//        );
//        return new RedirectResolution(this.getClass(), "display");
//    }
}
