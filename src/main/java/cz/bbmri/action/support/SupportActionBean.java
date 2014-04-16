package cz.bbmri.action.support;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 18.12.13
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/support")
@HttpCache(allow = false)
public class SupportActionBean extends ComponentActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserService userService;

    public SupportActionBean() {
        setComponentManager(new ComponentManager());
    }

    private static Breadcrumb getBreadcrumb(boolean active) {
        return new Breadcrumb(SupportActionBean.class.getName(),
                "display", false, "cz.bbmri.action.support.SupportActionBean.contacts", active);
    }

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        getBreadcrumbs().add(SupportActionBean.getBreadcrumb(true));
        return new ForwardResolution(SUPPORT);
    }

    public List<User> getAllAdministrators() {
        return userService.getAllByRole(SystemRole.ADMINISTRATOR);
    }

    public List<User> getAllDevelopers() {
        return userService.getAllByRole(SystemRole.DEVELOPER);
    }
}
