package bbmri.action.user;

import bbmri.action.FindActionBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.10.13
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/user/find/{$event}")
public class FindUserActionBean extends FindActionBean {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @HandlesEvent("find")
    @RolesAllowed({"administrator", "developer"})
    public Resolution find() {
        return new ForwardResolution(USER_FIND).addParameter("UserFind", getUserFind());
    }

    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer"})
    public Resolution detail() {
        return new ForwardResolution(UserActionBean.class, "detail").addParameter("id", id);
    }
}
