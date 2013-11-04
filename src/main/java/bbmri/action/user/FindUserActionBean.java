package bbmri.action.user;

import bbmri.action.BasicActionBean;
import bbmri.action.FindActionBean;
import bbmri.entities.User;
import bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
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
