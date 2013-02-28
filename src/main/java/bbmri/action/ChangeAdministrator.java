package bbmri.action;

import bbmri.entities.User;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/ChangeAdministrator/{$event}/{user.id}")
public class ChangeAdministrator extends BasicActionBean {

    private List<User> users;
    private User user;

    @SpringBean
    private UserService userService;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        this.users = userService.getAll();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/project_all.jsp");
    }

    public Resolution changeAdministrator() {
        userService.changeAdministrator(getContext().getLoggedUser().getId(), user.getId());
        refreshLoggedUser();
        getContext().getMessages().add(
                new SimpleMessage("Administrator was changed")
        );
        return new ForwardResolution("/project_all.jsp");
    }

    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
    }
}
