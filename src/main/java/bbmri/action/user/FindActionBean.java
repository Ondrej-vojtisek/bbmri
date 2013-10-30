package bbmri.action.user;

import bbmri.action.BasicActionBean;
import bbmri.entities.User;
import bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.10.13
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public class FindActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

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
        return userFacade.find(userFind, 5);
    }

    @HandlesEvent("find")
    @RolesAllowed({"administrator", "developer"})
    public Resolution find() {
        return new ForwardResolution(USER_FIND).addParameter("UserFind", userFind);
    }

    @HandlesEvent("findResolution")
    public Resolution findResolution() {
        return new ForwardResolution(USER_FIND);
    }

    @HandlesEvent("submitFind")
    @RolesAllowed({"administrator", "developer"})
    public Resolution submitFind() {
        return new ForwardResolution(USER_ALL);
    }
}
