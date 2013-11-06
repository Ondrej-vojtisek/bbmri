package bbmri.action.biobank;

import bbmri.action.user.FindUserActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.User;
import bbmri.facade.BiobankFacade;
import bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
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
public class CreateActionBean extends FindUserActionBean {


    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

   /* Variables */

    @SpringBean
    private UserFacade userFacade;


    @SpringBean
    private BiobankFacade biobankFacade;

    private Long id;

    @ValidateNestedProperties(value = {
            @Validate(on = {"create"}, field = "name", required = true),
            @Validate(on = {"create"}, field = "address", required = true)
    })
    private Biobank newBiobank;

    public Biobank getNewBiobank() {
        return newBiobank;
    }

    public void setNewBiobank(Biobank newBiobank) {
        this.newBiobank = newBiobank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getNewAdministrator() {
        if (id != null) {
            return userFacade.get(id);
        }
        return null;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("display") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution display() {
        return new ForwardResolution(BIOBANK_CREATE_GENERAL);
    }

    @DontValidate
    @HandlesEvent("administrators") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution administrators() {
        return new ForwardResolution(BIOBANK_CREATE_ADMINISTRATORS);
    }

    @DontValidate
    @HandlesEvent("done") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution done() {
        biobankFacade.createBiobank(newBiobank, id);
//        getContext().getMessages().add(
//                new SimpleMessage("Biobank was succesfully created.")
//        );
        return new ForwardResolution(BiobankActionBean.class, "allBiobanks");
    }

    @HandlesEvent("create") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution create() {
        return new ForwardResolution(CreateActionBean.class, "administrators");
    }

    @DontValidate
    @HandlesEvent("selectAdministrator")
    @RolesAllowed({"administrator", "developer"})
    public Resolution selectAdministrator() {
        return new ForwardResolution(BIOBANK_CREATE_CONFIRM);
    }

    @HandlesEvent("find")
    @RolesAllowed({"administrator", "developer"})
    public Resolution find() {
        return new ForwardResolution(BIOBANK_CREATE_ADMINISTRATORS).addParameter("UserFind", getUserFind());
    }


}
