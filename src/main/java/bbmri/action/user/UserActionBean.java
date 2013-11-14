package bbmri.action.user;

import bbmri.action.BasicActionBean;
import bbmri.entities.User;
import bbmri.entities.enumeration.SystemRole;
import bbmri.entities.webEntities.RoleDTO;
import bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Set;

//import javax.annotation.security.RolesAllowed;


@UrlBinding("/user/{$event}/{id}")
public class UserActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserFacade userFacade;

    @ValidateNestedProperties(value = {
            @Validate(on = {"create"},
                    field = "name",
                    required = true),
            @Validate(on = {"create"},
                    field = "surname",
                    required = true),
            @Validate(on = {"create"},
                    field = "password",
                    required = true)
    })
    private User user;
    private Long id;

    @Validate(on = {"changePassword"}, required = true)
    private String password;
    @Validate(on = {"changePassword"}, required = true)
    private String password2;

    public List<User> getUsers() {
        return userFacade.all();
    }

    public User getUser() {
        if (user == null) {
            if (id != null) {
                user = userFacade.get(id);
            }
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RoleDTO> getUserRoles() {
        return userFacade.getRoles(id);
    }

    public Set<SystemRole> getSystemRoles() {
        return userFacade.getSystemRoles(id);
    }

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(id);
    }

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

    public boolean getIsDeveloper(){
            return getSystemRoles().contains(SystemRole.DEVELOPER);
        }

    public boolean getIsAdministrator(){
           return getSystemRoles().contains(SystemRole.ADMINISTRATOR);
       }


    @DontValidate
    @DefaultHandler
    @HandlesEvent("allUsers") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution display() {
        return new ForwardResolution(USER_ALL);
    }

    @HandlesEvent("createUser")
    @RolesAllowed({"administrator", "developer"})
    public Resolution createUser() {
        return new ForwardResolution(USER_CREATE);
    }

    @RolesAllowed({"administrator", "developer"})
    public Resolution create() {
        userFacade.create(user);
        getContext().getMessages().add(
                new SimpleMessage("User {0} was created", user)
        );
        return new RedirectResolution(this.getClass(), "display");
    }

    @DontValidate
    @HandlesEvent("remove")
    @RolesAllowed({"administrator", "developer"})
    public Resolution remove() {
        userFacade.remove(id);
        // TODO confirm window
        getContext().getMessages().add(
                new SimpleMessage("User was removed")
        );
        return new RedirectResolution(this.getClass(), "display");
    }

    @HandlesEvent("detail")
    @RolesAllowed({"developer", "administrator", "user if ${isMyAccount}"})
    public Resolution detail() {
        return new ForwardResolution(USER_PERSONAL_DATA).addParameter("id", id);
    }

    @HandlesEvent("rolesView")
    @RolesAllowed({"administrator", "developer", "user if ${isMyAccount}"})
    public Resolution rolesView() {
        return new ForwardResolution(USER_ROLES).addParameter("id", id);
    }

    @HandlesEvent("removeAdministratorRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution removeAdministratorRole() {
        userFacade.removeAdministratorRole(id);
        // TODO exception message
        return new ForwardResolution(USER_ROLES).addParameter("id", id);
    }

    @HandlesEvent("removeDeveloperRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution removeDeveloperRole() {
        userFacade.removeDeveloperRole(id);
        // TODO exception message
        return new ForwardResolution(USER_ROLES).addParameter("id", id);
    }

    @HandlesEvent("setAdministratorRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution setAdministratorRole() {
        userFacade.setAsAdministrator(id);
        return new ForwardResolution(USER_ROLES).addParameter("id", id);
    }

    @HandlesEvent("setDeveloperRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution setDeveloperRole() {
        userFacade.setAsDeveloper(id);
        return new ForwardResolution(USER_ROLES).addParameter("id", id);
    }

    @HandlesEvent("refuseAdministratorRole")
    @RolesAllowed({"user if ${isMyAccount}"})
    public Resolution refuseAdministratorRole() {
        userFacade.removeAdministratorRole(id);
        // TODO exception message

        return new ForwardResolution(USER_ROLES).addParameter("id", id);
    }

    @HandlesEvent("refuseDeveloperRole")
    @RolesAllowed({"user if ${isMyAccount}"})
    public Resolution refuseDeveloperRole() {
        userFacade.removeDeveloperRole(id);
        // TODO exception message
        return new ForwardResolution(USER_ROLES).addParameter("id", id);
    }

    @DontValidate
    @HandlesEvent("update")
    @RolesAllowed({"user if ${isMyAccount}"})
    public Resolution update() {
        userFacade.update(user);
        return new RedirectResolution(UserActionBean.class, "detail");
    }

    @HandlesEvent("changePassword")
    @RolesAllowed({"user if ${isMyAccount}"})
    public Resolution changePassword() {
        if (password != null && password2 != null && password.equals(password2)) {
            user.setPassword(password);
            userFacade.update(user);
            getContext().getMessages().add(
                    new SimpleMessage("Password was changed")
            );
        } else {
            // TODO message
        }
        return new RedirectResolution(UserActionBean.class, "detail");
    }

    @DontValidate
    @HandlesEvent("changePasswordView")
    @RolesAllowed({"user if ${isMyAccount}"})
    public Resolution changePasswordView() {
        return new ForwardResolution(USER_PASSWORD);
    }

}


