package bbmri.action.user;

import bbmri.action.BasicActionBean;
import bbmri.entities.User;
import bbmri.entities.enumeration.RoleType;
import bbmri.entities.webEntities.RoleDTO;
import bbmri.facade.UserFacade;
import bbmri.io.ExcelImport;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
//import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@UrlBinding("/user/{$event}/{user.id}")
public class UserActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    protected UserFacade userFacade;

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

    private List<User> users;

    public List<User> getUsers() {
        return userFacade.all();
    }

    public User getUser() {
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
        return userFacade.getRoles(user.getId());
    }

    public Set<RoleType> getRoleTypes(){
        return userFacade.getRoleTypes(user.getId());
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
    public Resolution createUser(){
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
    @RolesAllowed({"administrator", "developer"})
    public Resolution detail() {
        if(id.equals(getContext().getMyId())){
            return new ForwardResolution(AccountActionBean.class, "display");
        }
        user = userFacade.get(id);
        return new ForwardResolution(USER_PERSONAL_DATA);
    }

    @HandlesEvent("rolesView")
    @RolesAllowed({"administrator", "developer"})
    public Resolution rolesView() {
           user = userFacade.get(id);
           return new ForwardResolution(USER_ROLES);
    }



}


