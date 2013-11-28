package bbmri.action;

import bbmri.facade.BiobankFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 8.10.13
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
@HttpCache(allow = false)
@UrlBinding("/dashboard")
public class DashboardActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @PermitAll
    @DontValidate
    @DefaultHandler
    public Resolution display(){
        return new ForwardResolution(DASHBOARD);
    }


}
