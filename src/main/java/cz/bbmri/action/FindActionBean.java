package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.User;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class FindActionBean<T> extends AuthorizationActionBean {

    @SpringBean
    protected UserDAO userDAO;

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
