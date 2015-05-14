package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.converter.PasswordTypeConverter;
import cz.bbmri.dao.ContactDAO;
import cz.bbmri.dao.CountryDAO;
import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.Contact;
import cz.bbmri.entity.Country;
import cz.bbmri.entity.User;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.ComponentManager;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/user/{$event}/{id}")
public class UserActionBean extends ComponentActionBean {

    @SpringBean
    private UserDAO userDAO;

    @SpringBean
    private ContactDAO contactDAO;

    @SpringBean
    private CountryDAO countryDAO;

    private User user;

    private Long id;

    private Contact contact;

    private Integer countryId;

    private MyPagedListHolder<User> pagination = new MyPagedListHolder<User>(new ArrayList<User>());

    @Validate(on = {"changePassword"}, required = true, converter = PasswordTypeConverter.class)
    private String password;
    @Validate(on = {"changePassword"}, required = true, converter = PasswordTypeConverter.class)
    private String password2;

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(UserActionBean.class.getName(),
                "all", false, "cz.bbmri.entity.User.users", active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, User user) {
        return new Breadcrumb(UserActionBean.class.getName(),
                "detail", true, user.getWholeName(), active, "id", user.getId());
    }

    private void detailResolutionBreadcrumbs() {
        if (getLoggedUser().isDeveloper() || getLoggedUser().isAdministrator()) {
            getBreadcrumbs().add(UserActionBean.getAllBreadcrumb(false));
            getBreadcrumbs().add(UserActionBean.getDetailBreadcrumb(true, getUser()));
        } else {
            getBreadcrumbs().add(UserActionBean.getDetailBreadcrumb(true, getLoggedUser()));
        }
    }


    public User getUser() {
        if (user == null) {
            if (id != null) {
                user = userDAO.get(id);
            }
        }

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Contact getContact() {
        if (contact == null) {
            if (getUser() != null) {
                contact = user.getContact();
            }
        }

        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(id);
    }

    public boolean getCanChangePassword() {

        return (getUser().getShibboleth() == null) && getIsMyAccount();
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

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MyPagedListHolder<User> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<User> pagination) {
        this.pagination = pagination;
    }

    @DefaultHandler
    @HandlesEvent("all") /* Necessary for stripes security tag*/
    @RolesAllowed({"admin", "developer"})
    public Resolution all() {

        getBreadcrumbs().add(UserActionBean.getAllBreadcrumb(true));

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(userDAO.all());
        pagination.setEvent("all");

        // TODO jak radit podle Contactu ?

//        default ordering
//        if (getOrderParam() == null) {
//            setOrderParam("surname");
//            getPagination().setOrderParam("surname");
//            getPagination().setDesc(false);
//        }

        return new ForwardResolution(View.User.ALL);
    }

    @HandlesEvent("detail") /* Necessary for stripes security tag*/
    @RolesAllowed({"admin", "developer", "authorized if ${isMyAccount}"})
    public Resolution detail() {
        getUser();

        if (user == null) {
            return new ForwardResolution(View.User.NOTFOUND);
        }

        detailResolutionBreadcrumbs();

        if (getUser().getContact() == null) {
            Contact contact = new Contact();
            contact = contactDAO.save(contact);
            contact.setUser(user);
            contactDAO.save(contact);
            return new ForwardResolution(View.User.DETAIL);
          //  return new RedirectResolution(UserActionBean.class, "detail").addParameter("id", user.getId());
        }

        return new ForwardResolution(View.User.DETAIL);
    }

    @HandlesEvent("save")
    @RolesAllowed({"authorized if ${isMyAccount}"})
    public Resolution save() {
        if (countryId != null) {
            Country country = countryDAO.get(countryId);
            if(country != null){
                contact.setCountry(country);
            }
        }

        contactDAO.save(contact);
        return new RedirectResolution(UserActionBean.class, "detail").addParameter("id", user.getId());
    }

    @HandlesEvent("changePasswordResolution")
    @RolesAllowed({"authorized if ${canChangePassword}"})
    public Resolution changePasswordResolution() {

        getUser();

        if (user == null) {
            return new ForwardResolution(View.User.NOTFOUND);
        }

        detailResolutionBreadcrumbs();

        return new ForwardResolution(View.User.PASSWORD);
    }

    @HandlesEvent("changePassword")
    @RolesAllowed({"authorized if ${canChangePassword}"})
    public Resolution changePassword() {
        if (password.equals(password2)) {
            user.setPassword(password);
            userDAO.save(user);
            getContext().getMessages().add(
                    new SimpleMessage("Password was changed")
            );
        } else {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.user.UserActionBean.passwordNotMatch"));
            return new ForwardResolution(this.getClass(), "changePasswordView").addParameter("id", user.getId());
        }
        return new RedirectResolution(UserActionBean.class, "detail");
    }

    @HandlesEvent("shibbolethResolution")
    @RolesAllowed({"admin", "developer", "authorized if ${isMyAccount}"})
    public Resolution shibbolethResolution() {

        getUser();

        if (user == null) {
            return new ForwardResolution(View.User.NOTFOUND);
        }

        detailResolutionBreadcrumbs();

        return new ForwardResolution(View.User.SHIBBOLETH);
    }

}
