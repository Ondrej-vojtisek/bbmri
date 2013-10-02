package bbmri.action.account;

import bbmri.action.BasicActionBean;
import bbmri.entities.Role;
import bbmri.entities.User;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/account")
public class AccountActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

 //   @SpringBean
 //   protected UserService userService;


    @Validate(on = {"changePassword"}, required = true)
    private String password;
    @Validate(on = {"changePassword"}, required = true)
    private String password2;
    private User user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getMyRoles() {
        List<String> roles = new ArrayList<String>();
        for (Role role : getLoggedUser().getRoles()) {
            roles.add(role.getName());
        }
        /*TODO - tohle bude muset byt udelano jinak*/

        /*
        BiobankAdministrator ba = getLoggedUser().getBiobankAdministrator();
        Biobank biobank = biobankService.get(ba.getBiobank().getId());

        if (biobank != null) {
            roles.add("Biobank operator of " + biobank.getName());
        }
        if (getLoggedUser().getProjectAdministrators() != null) {
            roles.add("Working on a project: ");

            //TODO
        }
        */
        return roles;
    }

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        user = getLoggedUser();
        return new ForwardResolution(ACCOUNT_PERSONAL_DATA);
    }


    @Before(stages = LifecycleStage.BindingAndValidation, on = {"update", "changePassword"})
    public void fillInputs() {
        user = getLoggedUser();
    }

    @DontValidate
    public Resolution update() {
        logger.debug("Update: User: " + user);
        User userDB = getLoggedUser();

        if (user.getName() != null) {
            userDB.setName(user.getName());
        }
        if (user.getSurname() != null)
            userDB.setSurname(user.getSurname());

        userService.update(userDB);
        return new RedirectResolution(this.getClass(), "display");
    }

    @DontValidate
    @HandlesEvent("changePasswordView")
    public Resolution changePasswordView() {
        return new ForwardResolution(ACCOUNT_CHANGE_PASSWORD);
    }

    @DontValidate
    @HandlesEvent("rolesView")
    public Resolution rolesView() {
        return new ForwardResolution(ACCOUNT_ROLES);
    }


    @DontValidate
    public Resolution changePassword() {
        User userDB = getLoggedUser();
        if (password != null && password2 != null) {
            if (password.equals(password2)) {
                userDB.setPassword(password);
                userService.update(userDB);
                getContext().getMessages().add(
                        new SimpleMessage("Password was changed")
                );

            }
        }
        return new RedirectResolution(this.getClass(), "display");
    }
}
