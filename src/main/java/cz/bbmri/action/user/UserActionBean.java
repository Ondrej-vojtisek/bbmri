package cz.bbmri.action.user;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.webEntities.RoleDTO;
import cz.bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Set;

@HttpCache(allow = false)
@UrlBinding("/user/{$event}/{id}")
public class UserActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserFacade userFacade;

    @ValidateNestedProperties(value = {
            @Validate(on = {"create", "update"},
                    field = "name",
                    required = true),
            @Validate(on = {"create", "update"},
                    field = "surname",
                    required = true),
            @Validate(on = {"create"},
                    field = "password",
                    required = true)
    })
    private User user;
    private Long id;

    private String orderParam;

    public String getOrderParam() {
        return orderParam;
    }

    public void setOrderParam(String orderParam) {
        this.orderParam = orderParam;
    }

    @Validate(on = {"changePassword"}, required = true)
    private String password;
    @Validate(on = {"changePassword"}, required = true)
    private String password2;

    public List<User> getUsers() {
        if(orderParam == null){
            return userFacade.all();
        }
        return userFacade.allOrderedBy(orderParam, false);
    }

    @PermitAll
    @HandlesEvent("orderBy")
    public Resolution orderBy(){

        logger.debug("orderBy Event");

        return new ForwardResolution(USER_ALL);
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

    public boolean getIsShibbolethUser() {
           return getLoggedUser().isShibbolethUser();
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
        if(!userFacade.create(user)){
            return new ForwardResolution(this.getClass(), "display");
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "display");
    }

    @DontValidate
    @HandlesEvent("remove")
    @RolesAllowed({"administrator", "developer"})
    public Resolution remove() {
        if(!userFacade.remove(id)){
            return new ForwardResolution(this.getClass(), "display");
        }
        successMsg(null);
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
         if(!userFacade.removeAdministratorRole(id, getContext().getValidationErrors())){
             return new ForwardResolution(USER_ROLES).addParameter("id", id);
         }
        successMsg(null);
        return new RedirectResolution(USER_ROLES).addParameter("id", id);
    }

    @HandlesEvent("removeDeveloperRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution removeDeveloperRole() {
        if(!userFacade.removeDeveloperRole(id, getContext().getValidationErrors())){
            return new ForwardResolution(USER_ROLES).addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(USER_ROLES).addParameter("id", id);
    }

    @HandlesEvent("setAdministratorRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution setAdministratorRole() {
        if(!userFacade.setAsAdministrator(id, getContext().getValidationErrors())){
            return new ForwardResolution(USER_ROLES).addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(USER_ROLES).addParameter("id", id);
    }

    @HandlesEvent("setDeveloperRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution setDeveloperRole() {
        if(!userFacade.setAsDeveloper(id, getContext().getValidationErrors())){
            return new ForwardResolution(USER_ROLES).addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(USER_ROLES).addParameter("id", id);
    }

    /* Credentials of user using Shibboleth are loaded during sign in. Any change inside BBMRI index is unnecessary*/
    @HandlesEvent("update")
    @RolesAllowed({"user if ${isMyAccount && !isShibbolethUser}"})
    public Resolution update() {
        if(!userFacade.update(user)){
            return new ForwardResolution(UserActionBean.class, "detail").addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(UserActionBean.class, "detail").addParameter("id", id);
    }

    /* Credentials of user using Shibboleth are loaded during sign in. Any change inside BBMRI index is unnecessary*/

    @HandlesEvent("changePassword")
    @RolesAllowed({"user if ${isMyAccount && !isShibbolethUser}"})
    public Resolution changePassword() {
        if (password.equals(password2)) {
            user.setPassword(password);
            userFacade.update(user);
            getContext().getMessages().add(
                    new SimpleMessage("Password was changed")
            );
        } else {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.user.UserActionBean.passwordNotMatch"));
            return new ForwardResolution("USER_PASSWORD").addParameter("id", id);
        }
        return new RedirectResolution(UserActionBean.class, "detail");
    }

    /* Credentials of user using Shibboleth are loaded during sign in. Any change inside BBMRI index is unnecessary*/

    @DontValidate
    @HandlesEvent("changePasswordView")
    @RolesAllowed({"user if ${isMyAccount && !isShibbolethUser}"})
    public Resolution changePasswordView() {
        return new ForwardResolution(USER_PASSWORD).addParameter("id", id);
    }

}


