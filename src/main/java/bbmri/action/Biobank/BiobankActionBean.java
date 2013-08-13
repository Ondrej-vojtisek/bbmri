package bbmri.action.Biobank;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.User;
import bbmri.service.BiobankService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.3.13
 * Time: 1:09
 * To change this template use File | Settings | File Templates.
 */

@PermitAll
@UrlBinding("/Biobank")
public class BiobankActionBean extends BasicActionBean {

    private static final String ALL = "/biobank_all.jsp";
    private static final String CREATE = "/biobank_create.jsp";
    private static final String EDIT = "/biobank_edit.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

/* Variables */


    @ValidateNestedProperties(value = {
            @Validate(on = {"update"}, field = "name", required = true),
            @Validate(on = {"update"}, field = "address", required = true),
    })
    private Biobank biobank;
    private List<Biobank> biobanks;
    private User administrator;
    private List<Long> selected;
    private User user;
    private List<Long> selectedApprove;
    @ValidateNestedProperties(value = {
               @Validate(on = {"create"}, field = "name", required = true),
               @Validate(on = {"create"}, field = "address", required = true),
       })
    private Biobank newBiobank;

    /* Setter / Getter */

    public List<Biobank> getBiobanks() {
        return biobankService.getAll();
    }

    public Biobank getBiobank() {
        if (biobank == null) {
            biobank = getLoggedUser().getBiobank();
        }
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public Biobank getNewBiobank() {
           return newBiobank;
       }

       public void setNewBiobank(Biobank newBiobank) {
           this.newBiobank = newBiobank;
       }

    public User getAdministrator() {
        return administrator;
    }

    public void setAdministrator(User administrator) {
        this.administrator = administrator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Long> getSelected() {
        return selected;
    }

    public void setSelected(List<Long> selected) {
        this.selected = selected;
    }

    public List<User> getUsers() {
        return userService.getAll();
    }

    public List<User> getNonAdministrators() {
        return userService.getNonAdministratorUsers();
    }

    public List<Long> getSelectedApprove() {
        return selectedApprove;
    }

    public void setSelectedApprove(List<Long> selectedApprove) {
        this.selectedApprove = selectedApprove;
    }

    public List<User> getAdministrators() {
        return biobankService.getAllAdministrators(getLoggedUser().getBiobank().getId());
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    public Resolution display() {
        biobanks = biobankService.getAll();
        return new ForwardResolution(ALL);
    }
    @DontValidate
    @HandlesEvent("createBiobank")
    public Resolution createBiobank(){
        return new ForwardResolution(CREATE);
    }
    @DontValidate
    public Resolution edit() {
        biobank = getLoggedUser().getBiobank();
        getContext().setBiobank(biobank);
        return new ForwardResolution(EDIT);
    }
    @DontValidate
    public Resolution create() {
        User resDB = userService.getById(administrator.getId());
        if (resDB.getBiobank() != null) {
            getContext().getMessages().add(
                         new SimpleMessage("Selected user is already an administrator of a biobank")
                 );
            return new ForwardResolution(this.getClass(), "display");
        }
        biobankService.create(newBiobank, administrator.getId());
        return new ForwardResolution(this.getClass(), "display");
    }
    @DontValidate
    public Resolution update() {
        biobankService.update(biobank);
        return new ForwardResolution(this.getClass(), "display");
    }
    @DontValidate
    public Resolution removeAll() {
        Integer removed = 0;
        if (selected != null) {
            for (Long id : selected) {
                if (id.equals(getContext().getIdentifier())) {
                           /*you can't remove yourself*/
                    return new ForwardResolution(this.getClass(), "display");
                }
                biobankService.removeAdministratorFromBiobank(id, getBiobank().getId());
                removed++;
            }
        }
        getContext().getMessages().add(
                new SimpleMessage("{0} administrators removed", removed)
        );
        return new RedirectResolution(this.getClass(), "display");
    }
    @DontValidate
    public Resolution changeOwnership() {

        logger.debug("New user = " + user);
        biobankService.changeOwnership(getLoggedUser().getBiobank().getId(), user.getId());
        getContext().getMessages().add(
                new SimpleMessage("Ownership of biobank was changed")
        );
        return new RedirectResolution(this.getClass(), "display");
    }
    @DontValidate
    public Resolution assignAll() {
        Integer assigned = 0;
        if (selectedApprove != null) {
            for (Long userId : selectedApprove) {
                biobankService.assignAdministrator(userId, getBiobank().getId());
                assigned++;
            }
        }
        getContext().getMessages().add(
                new SimpleMessage("{0} users assigned", assigned)
        );
        return new RedirectResolution(this.getClass(), "display");
    }
}
