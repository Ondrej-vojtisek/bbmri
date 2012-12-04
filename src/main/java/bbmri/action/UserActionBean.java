package bbmri.action;

import bbmri.entities.User;
import bbmri.service.UserService;
import bbmri.serviceImpl.UserServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.util.List;


@UrlBinding("/user/{$event}/{user.id}")
public class UserActionBean implements ActionBean {

    private MyActionBeanContext ctx;
    private User user;
    private long id;
    private UserService userService;
    private List<User> users;


    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    public User getLoggedUser() {
        return ctx.getLoggedUser();
    }

    public List<User> getUsers() {
        return getUserService().getAll();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ValidateNestedProperties(value = {
            @Validate(on = {"pridej", "uloz"}, field = "name", required = true),
            @Validate(on = {"pridej", "uloz"}, field = "surname", required = true)
    }
    )

    @DefaultHandler
    public Resolution zobraz() {

        users = getUserService().getAll();
        return new ForwardResolution("/allUsers.jsp");
    }

    public Resolution create() {
        getUserService().create(user);
        return new RedirectResolution(this.getClass(), "zobraz");
    }

    public Resolution delete() {
        getUserService().remove((Long) id);
        return new RedirectResolution(this.getClass(), "zobraz");
    }

}


