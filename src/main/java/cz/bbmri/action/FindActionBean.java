package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.HttpCache;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
public class FindActionBean extends BasicActionBean {

    private static int FIND_RESULTS = 5;

    @SpringBean
    protected UserFacade userFacade;

    private User userFind;

    public User getUserFind() {
        return userFind;
    }

    public void setUserFind(User userFind) {
        this.userFind = userFind;
    }

    public List<User> getResults() {
        if (userFind == null) {
            return null;
        }
        return userFacade.find(userFind, FIND_RESULTS);
    }
}
