package bbmri.action;

import bbmri.entities.User;
import bbmri.service.UserService;
import bbmri.serviceImpl.UserServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/account/{$event}/{user.id}")
public class AccountActionBean implements ActionBean {

    private MyActionBeanContext ctx;
    private UserService userService;
    @Validate(on = {"changePassword"}, required = true)
    private String password;
    @Validate(on = {"changePassword"}, required = true)
    private String password2;
    private User user;
    private User loggedUser;

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

    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    public User getLoggedUser() {
        loggedUser = ctx.getLoggedUser();
        return loggedUser;
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @DefaultHandler
    public Resolution zobraz() {

        return new ForwardResolution("/myAccount.jsp");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"update", "changePassword"})
    public void fillInputs() {
        user = getLoggedUser();
    }

    public Resolution update() {
        if (user.getName() != null)
            loggedUser.setName(user.getName());

        if (user.getSurname() != null)
            loggedUser.setSurname(user.getSurname());

        getUserService().update(loggedUser);
        ctx.setLoggedUser(loggedUser);
        return new RedirectResolution(this.getClass(), "zobraz");
    }

    public Resolution changePassword() {
        if (password != null && password2 != null) {
            if (password.equals(password2))
                getLoggedUser().setPassword(password);
            getUserService().update(getLoggedUser());
        }
        user = getLoggedUser();
        return new RedirectResolution(this.getClass(), "zobraz");
    }

}
