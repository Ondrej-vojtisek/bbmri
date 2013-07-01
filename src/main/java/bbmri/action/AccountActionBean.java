package bbmri.action;

import bbmri.entities.User;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/account/{$event}/{user.id}")
public class AccountActionBean extends BasicActionBean {

    private static final String MY_ACCOUNT = "/my_account.jsp";

    @SpringBean
    private UserService userService;

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

    @DefaultHandler
    public Resolution display() {
        user = getLoggedUser();
        return new ForwardResolution(MY_ACCOUNT);
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"update", "changePassword"})
    public void fillInputs() {
        user = getLoggedUser();
    }

    public Resolution update() {
        if (user.getName() != null) {
            getLoggedUser().setName(user.getName());
        }
        if (user.getSurname() != null)
            getLoggedUser().setSurname(user.getSurname());

        userService.update(getLoggedUser());
        refreshLoggedUser();
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution changePassword() {
        if (password != null && password2 != null) {
            if (password.equals(password2))
                getLoggedUser().setPassword(password);
            userService.update(getLoggedUser());
            refreshLoggedUser();
            getContext().getMessages().add(
                    new SimpleMessage("Password was changed")
            );
        }
        user = getLoggedUser();
        return new RedirectResolution(this.getClass(), "display");
    }

    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
    }

}
