package cz.bbmri.action.user;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.converter.PasswordTypeConverter;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

@HttpCache(allow = false)
@UrlBinding("/user/{$event}/{userId}")
public class UserActionBean extends ComponentActionBean<User> {

    @SpringBean
    private UserService userService;

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

// TODO - sifrovani parametru, ktery se pouziva v URL
//    @Validate(encrypted=true)
    private Long userId;


    public UserActionBean() {
        setPagination(new MyPagedListHolder<User>(new ArrayList<User>()));
        //default
        setComponentManager(new ComponentManager(ComponentManager.USER_DETAIL));
    }

    private static Breadcrumb getBreadcrumb(boolean active) {
        return new Breadcrumb(UserActionBean.class.getName(),
                "display", false, "cz.bbmri.entities.User.users", active);
    }

    private static Breadcrumb getDetailBreadcrumb(boolean active, User user) {
        return new Breadcrumb(UserActionBean.class.getName(),
                "display", true, user.getWholeName(), active, "userId", user.getId());
    }

    private static Breadcrumb getMyDetailBreadcrumb(boolean active, User user) {
        return new Breadcrumb(UserActionBean.class.getName(),
                "display", false, "logged_user", active, "userId", user.getId());
    }

    private static Breadcrumb getRoleBreadcrumb(boolean active, Long userId) {
        return new Breadcrumb(UserActionBean.class.getName(),
                "rolesView", false, "cz.bbmri.action.user.UserActionBean.roles", active,
                "userId", userId);
    }

    private static Breadcrumb getPasswordBreadcrumb(boolean active, Long userId) {
        return new Breadcrumb(UserActionBean.class.getName(),
                "changePasswordView", false, "cz.bbmri.action.user.UserActionBean.password", active,
                "userId", userId);
    }

    private static Breadcrumb getSettingBreadcrumb(boolean active, Long userId) {
          return new Breadcrumb(UserActionBean.class.getName(),
                  "mySettingResolution", false, "cz.bbmri.action.user.UserActionBean.setting", active,
                  "userId", userId);
      }

    @Validate(on = {"changePassword"}, required = true, converter = PasswordTypeConverter.class)
    private String password;
    @Validate(on = {"changePassword"}, required = true, converter = PasswordTypeConverter.class)
    private String password2;


    User getUser() {
        if (user == null) {
            if (userId != null) {
                user = userService.get(userId);
            }
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    boolean getIsMyAccount() {
        return getContext().getMyId().equals(userId);
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

    public boolean getIsDeveloper() {
        return getUser().getSystemRoles().contains(SystemRole.DEVELOPER);
    }

    public boolean getIsAdministrator() {
        return getUser().getSystemRoles().contains(SystemRole.ADMINISTRATOR);
    }


    @DontValidate
    @DefaultHandler
    @HandlesEvent("display") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution display() {

        getBreadcrumbs().add(UserActionBean.getBreadcrumb(true));

        initiatePagination();
        // default ordering
        if (getOrderParam() == null) {
            setOrderParam("surname");
            getPagination().setOrderParam("surname");
            getPagination().setDesc(false);
        }
        getPagination().setEvent("display");
        getPagination().setSource(userService.allOrderedBy(
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(USER_ALL);
    }

    /* Create user is only for testing purposes. So it is allowed only on localhost. */

    @HandlesEvent("createUser")
    @RolesAllowed({"developer if ${!isShibbolethUser}"})
    public Resolution createUser() {
        return new ForwardResolution(USER_CREATE);
    }

    @RolesAllowed({"administrator", "developer"})
    public Resolution create() {
        if (!userService.create(user, getContext().getLocale())) {
            return new ForwardResolution(this.getClass(), "display");
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "display");
    }

    @DontValidate
    @HandlesEvent("remove")
    @RolesAllowed({"administrator", "developer"})
    public Resolution remove() {
        if (!userService.remove(userId)) {
            return new ForwardResolution(this.getClass(), "display");
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "display");
    }

    @HandlesEvent("detail")
    @RolesAllowed({"developer", "administrator", "user if ${isMyAccount}"})
    public Resolution detail() {

        if (getIsMyAccount()) {
            getBreadcrumbs().add(UserActionBean.getMyDetailBreadcrumb(true, getLoggedUser()));

        } else {
            getBreadcrumbs().add(UserActionBean.getBreadcrumb(false));
            getBreadcrumbs().add(UserActionBean.getDetailBreadcrumb(true, getUser()));
        }
        return new ForwardResolution(USER_PERSONAL_DATA).addParameter("userId", userId);
    }

    @HandlesEvent("rolesView")
    @RolesAllowed({"administrator", "developer", "user if ${isMyAccount}"})
    public Resolution rolesView() {

        if (getIsMyAccount()) {
                   getBreadcrumbs().add(UserActionBean.getMyDetailBreadcrumb(false, getLoggedUser()));

               } else {
                   getBreadcrumbs().add(UserActionBean.getBreadcrumb(false));
                   getBreadcrumbs().add(UserActionBean.getDetailBreadcrumb(false, getUser()));
               }

               getBreadcrumbs().add(UserActionBean.getRoleBreadcrumb(true, userId));

        return new ForwardResolution(USER_ROLES).addParameter("userId", userId);
    }

    @HandlesEvent("removeAdministratorRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution removeAdministratorRole() {
        if (!userService.removeSystemRole(userId, SystemRole.ADMINISTRATOR, getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "rolesView").addParameter("userId", userId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "rolesView").addParameter("userId", userId);
    }

    @HandlesEvent("removeDeveloperRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution removeDeveloperRole() {
        if (!userService.removeSystemRole(userId, SystemRole.DEVELOPER, getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "rolesView").addParameter("userId", userId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "rolesView").addParameter("userId", userId);
    }

    @HandlesEvent("setAdministratorRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution setAdministratorRole() {
        if (!userService.setSystemRole(userId, SystemRole.ADMINISTRATOR, getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "rolesView").addParameter("userId", userId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "rolesView").addParameter("userId", userId);
    }

    @HandlesEvent("setDeveloperRole")
    @RolesAllowed({"administrator", "developer"})
    public Resolution setDeveloperRole() {
        if (!userService.setSystemRole(userId, SystemRole.DEVELOPER, getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "rolesView").addParameter("userId", userId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "rolesView").addParameter("userId", userId);
    }

    /* Credentials of user using Shibboleth are loaded during sign in. Any change inside BBMRI index is unnecessary*/
    @HandlesEvent("update")
    @RolesAllowed({"user if ${isMyAccount && !isShibbolethUser}"})
    public Resolution update() {
        if (userService.update(user) == null) {
            return new ForwardResolution(UserActionBean.class, "detail").addParameter("userId", userId);
        }
        successMsg();
        return new RedirectResolution(UserActionBean.class, "detail").addParameter("userId", userId);
    }

    /* Credentials of user using Shibboleth are loaded during sign in. Any change inside BBMRI index is unnecessary*/

    @HandlesEvent("changePassword")
    @RolesAllowed({"user if ${isMyAccount && !isShibbolethUser}"})
    public Resolution changePassword() {
        if (password.equals(password2)) {
            user.setPassword(password);
            userService.update(user);
            getContext().getMessages().add(
                    new SimpleMessage("Password was changed")
            );
        } else {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.user.UserActionBean.passwordNotMatch"));
            return new ForwardResolution(this.getClass(), "changePasswordView").addParameter("userId", userId);
        }
        return new RedirectResolution(UserActionBean.class, "detail");
    }

    /* Credentials of user using Shibboleth are loaded during sign in. Any change inside BBMRI index is unnecessary*/

    @DontValidate
    @HandlesEvent("changePasswordView")
    @RolesAllowed({"user if ${isMyAccount && !isShibbolethUser}"})
    public Resolution changePasswordView() {
        if (getIsMyAccount()) {
            getBreadcrumbs().add(UserActionBean.getMyDetailBreadcrumb(false, getLoggedUser()));

        } else {
            getBreadcrumbs().add(UserActionBean.getBreadcrumb(false));
            getBreadcrumbs().add(UserActionBean.getDetailBreadcrumb(false, getUser()));
        }

        getBreadcrumbs().add(UserActionBean.getPasswordBreadcrumb(true, userId));


        return new ForwardResolution(USER_PASSWORD).addParameter("userId", userId);
    }

    @DontValidate
    @HandlesEvent("mySettingResolution")
    @RolesAllowed({"user if ${isMyAccount}"})
    public Resolution mySettingResolution() {

        getBreadcrumbs().add(UserActionBean.getMyDetailBreadcrumb(false, getLoggedUser()));
        getBreadcrumbs().add(UserActionBean.getSettingBreadcrumb(true, getContext().getMyId()));
        return new ForwardResolution(USER_SETTING).addParameter("userId", userId);
    }

}


