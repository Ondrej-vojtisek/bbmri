package bbmri.action.user;

import bbmri.action.BasicActionBean;
import bbmri.entities.User;
import bbmri.entities.enumeration.RoleType;
import bbmri.entities.webEntities.RoleDTO;
import bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/account/{$event}/{user.id}")
public class AccountActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserFacade userFacade;

    private User user;

    public User getUser() {
           return user;
       }

    public void setUser(User user) {
           this.user = user;
       }

    @Validate(on = {"changePassword"}, required = true)
    private String password;
    @Validate(on = {"changePassword"}, required = true)
    private String password2;

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

    public List<RoleDTO> getUserRoles() {
        return userFacade.getRoles(getContext().getMyId());
    }

    public Set<RoleType> getRoleTypes(){
        return userFacade.getRoleTypes(getContext().getMyId());
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
        /* fillInputs guarantee that user has id */
        userFacade.update(user);
        return new RedirectResolution(this.getClass(), "display");
    }

    @DontValidate
    @HandlesEvent("changePasswordView")
    public Resolution changePasswordView() {
        return new ForwardResolution(ACCOUNT_PASSWORD);
    }

    @DontValidate
    @HandlesEvent("rolesView")
    public Resolution rolesView() {
        return new ForwardResolution(ACCOUNT_ROLES);
    }

    @DontValidate
    public Resolution changePassword() {

        if(password != null && password2 != null && password.equals(password2)){
            user.setPassword(password);
            userFacade.update(user);
            getContext().getMessages().add(
                new SimpleMessage("Password was changed")
            );
        }else{
            // TODO message
        }
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution refuseAdministratorRole(){
        userFacade.removeAdministratorRole(getContext().getMyId());
        // TODO exception message

        return new ForwardResolution(ACCOUNT_ROLES);
    }

    public Resolution refuseDeveloperRole(){
           userFacade.removeDeveloperRole(getContext().getMyId());
        // TODO exception message
           return new ForwardResolution(ACCOUNT_ROLES);
       }

    public Resolution setAdministratorRole() {
        userFacade.setAsAdministrator(getContext().getMyId());
        return new ForwardResolution(ACCOUNT_ROLES);
    }

    public Resolution setDeveloperRole() {
        userFacade.setAsDeveloper(getContext().getMyId());
        return new ForwardResolution(ACCOUNT_ROLES);
    }
}
