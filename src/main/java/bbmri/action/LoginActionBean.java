package bbmri.action;

import bbmri.entities.Project;
import bbmri.entities.User;
import bbmri.service.LoginService;
import bbmri.service.NotificationService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LongTypeConverter;
import net.sourceforge.stripes.validation.Validate;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 22.10.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */

@UrlBinding("/login")
public class LoginActionBean extends BasicActionBean {

    @SpringBean
    private UserService userService;

    @SpringBean
    private LoginService loginService;

    @SpringBean
    private NotificationService notificationService;

    @Validate(converter = LongTypeConverter.class, on = {"login"},
            required = true, minvalue = 1)
    private Long id;
    @Validate(on = {"login"}, required = true)
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution("/index.jsp");
    }

    @HandlesEvent("login")
    public Resolution login() {
        getContext().setLoggedUser(null);
        User user = loginService.login(id, password);
        if (user != null) {
            getContext().setLoggedUser(user);
            return new RedirectResolution(bbmri.action.Project.ProjectActionBean.class);
        }
        getContext().getMessages().add(
                       new SimpleMessage("Indentifier or password is not correct")
               );
        return new ForwardResolution(this.getClass(), "display");
    }

    public Resolution logout() {
        loginService.logout(getLoggedUser());
       // notificationService.setAllNewByRecipientToVisited(getLoggedUser().getId());
        getContext().setLoggedUser(null);
        return new RedirectResolution(this.getClass(), "display");
    }

    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
    }

}
