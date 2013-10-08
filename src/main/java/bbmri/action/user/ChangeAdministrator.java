package bbmri.action.user;

import bbmri.action.BasicActionBean;
import bbmri.entities.User;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/ChangeAdministrator")
public class ChangeAdministrator extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private List<User> users;
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<User> getUsers() {
        this.users = userService.all();
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(USER_CHANGE_ADMINISTRATOR);
    }

    @DontValidate
    public Resolution changeAdministrator() {
        logger.debug("Roles: " + getRoles());

        userService.changeAdministrator(getContext().getMyId(), user.getId());
        logger.debug("Roles: " + getRoles());
        getContext().getMessages().add(
                new SimpleMessage("Administrator was changed")
        );
        return new RedirectResolution(PROJECT_MY_PROJECTS);
    }
}
