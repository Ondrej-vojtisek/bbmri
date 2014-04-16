package cz.bbmri.action;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
public class FindActionBean extends PermissionActionBean {

    @SpringBean
    protected UserService userService;

    private User userFind;

    protected User getUserFind() {
        return userFind;
    }

    public void setUserFind(User userFind) {
        this.userFind = userFind;
    }

    public List<User> getResults() {
        if (userFind == null) {
            return null;
        }
        return userService.find(userFind, Constant.MAXIMUM_FIND_RESULTS);
    }
}
