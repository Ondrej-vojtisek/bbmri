package cz.bbmri.action.biobank;

import cz.bbmri.action.FindActionBean;
import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 10:08
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/addAdministrator/{$event}/{id}")
public class FindBiobankAdminActionBean extends PermissionActionBean<User> {

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

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(BIOBANK_DETAIL_ADD_ADMINISTRATOR);
    }

    @HandlesEvent("find")
    public Resolution find() {
        return new ForwardResolution(BIOBANK_DETAIL_ADD_ADMINISTRATOR)
                .addParameter("UserFind", getUserFind())
                .addParameter("biobankId", biobankId);
    }
}
