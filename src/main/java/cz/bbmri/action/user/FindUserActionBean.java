package cz.bbmri.action.user;

import cz.bbmri.action.FindActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.10.13
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@UrlBinding("/user/find/{$event}")
public class FindUserActionBean extends FindActionBean {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FindUserActionBean() {
         setPagination(new MyPagedListHolder<User>(new ArrayList<User>()));
         //default
         setComponentManager(new ComponentManager());
     }

    public static Breadcrumb getBreadcrumb(boolean active) {
            return new Breadcrumb(FindUserActionBean.class.getName(),
                    "find", false, "cz.bbmri.action.user.UserActionBean.findUser", active);
        }

    @HandlesEvent("find")
    @RolesAllowed({"administrator", "developer"})
    public Resolution find() {
        getBreadcrumbs().add(FindUserActionBean.getBreadcrumb(true));
        return new ForwardResolution(USER_FIND).addParameter("UserFind", getUserFind());
    }

    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer"})
    public Resolution detail() {
        return new ForwardResolution(UserActionBean.class, "detail").addParameter("userId", id);
    }
}
