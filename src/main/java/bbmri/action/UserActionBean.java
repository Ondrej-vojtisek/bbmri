package bbmri.action;

import bbmri.entities.User;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.util.List;


@UrlBinding("/user/{$event}/{user.id}")
public class UserActionBean extends BasicActionBean {

    private User user;
    private Long id;

    @SpringBean
    private UserService userService;
    private List<User> users;

    public List<User> getUsers() {
        return userService.getAll();
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

    @DefaultHandler
    public Resolution display() {
        users = userService.getAll();
        return new ForwardResolution("/user_all.jsp");
    }

    public Resolution create() {

        user.setBiobank(null);
        user.setAdministrator(false);
        user.setOnline(false);
        userService.create(user);
        getContext().getMessages().add(
                new SimpleMessage("User {0} was created", user)
        );
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution delete() {
        User user = userService.getById(id);
        userService.remove(user);
        getContext().getMessages().add(
                new SimpleMessage("User {0} was created", user)
        );
        return new RedirectResolution(this.getClass(), "display");
    }



    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
    }
}


