package cz.bbmri.action.support;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.facade.UserFacade;
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
public class SupportActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserFacade userFacade;

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(SUPPORT);
    }

    public List<User> getAllAdministrators() {
        return userFacade.getAdministrators();
    }

    public List<User> getAllDevelopers() {
        return userFacade.getDevelopers();
    }
}
