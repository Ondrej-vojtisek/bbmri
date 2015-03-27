package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.Contact;
import cz.bbmri.entity.Role;
import cz.bbmri.entity.User;
import cz.bbmri.entity.webEntities.ComponentManager;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/role/{$event}/{id}")
public class RoleActionBean extends ComponentActionBean {

    @SpringBean
    private UserDAO userDAO;

    private User user;

    private Long id;



    public User getUser() {
        if (user == null) {
            if (id != null) {
                user = userDAO.get(id);
            }
        }

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

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(id);
    }

    @DefaultHandler
    @HandlesEvent("detail") /* Necessary for stripes security tag*/
    @RolesAllowed({"admin", "developer", "authorized if ${isMyAccount}"})
    public Resolution detail() {
        getUser();

        if (user == null) {
            return new ForwardResolution(View.User.NOTFOUND);
        }

        if (user == null) {
            return new ForwardResolution(View.User.NOTFOUND);
        }

        if (getLoggedUser().isDeveloper() || getLoggedUser().isAdministrator()) {
            getBreadcrumbs().add(UserActionBean.getAllBreadcrumb(false));
            getBreadcrumbs().add(UserActionBean.getDetailBreadcrumb(true, getUser()));
        } else {
            getBreadcrumbs().add(UserActionBean.getDetailBreadcrumb(true, getLoggedUser()));
        }

        return new ForwardResolution(View.Role.USERDETAIL);
    }
}
