package cz.bbmri.action;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.User;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class FindActionBean<T> extends PermissionActionBean<T> {

    @SpringBean
    protected UserDao userDao;

    private User userFind;

    // must be public bcs of .jsp
    public User getUserFind() {
        return userFind;
    }

    public void setUserFind(User userFind) {
        this.userFind = userFind;
    }

//    public List<User> getResults() {
//        if (userFind == null) {
//            return null;
//        }
//        return userDao.findUser(userFind, 5);
//    }
}
