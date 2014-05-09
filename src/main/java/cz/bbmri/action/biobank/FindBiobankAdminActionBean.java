package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/biobank/administrators/addAdministrator/{$event}/{id}")
public class FindBiobankAdminActionBean extends PermissionActionBean<User> {

    @SpringBean
    private UserService userService;

    private User userFind;

    private static Breadcrumb getBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(FindBiobankAdminActionBean.class.getName(),
                "display", false, "cz.bbmri.action.biobank.BiobankActionBean.addAdministrator",
                active, "biobankId", biobankId);
    }

    public FindBiobankAdminActionBean() {
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.BIOBANK_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
    }


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
        return userService.find(userFind, 5);
    }

    @DefaultHandler
    public Resolution display() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankAdministratorsActionBean.getBreadcrumb(false, biobankId));
        getBreadcrumbs().add(FindBiobankAdminActionBean.getBreadcrumb(true, biobankId));

        return new ForwardResolution(BIOBANK_DETAIL_ADD_ADMINISTRATOR);
    }

    @HandlesEvent("find")
    public Resolution find() {
        return new ForwardResolution(this.getClass(), "display")
                .addParameter("UserFind", getUserFind())
                .addParameter("biobankId", biobankId);
    }

}
