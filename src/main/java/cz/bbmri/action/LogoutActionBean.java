package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 28.1.14
 * Time: 17:03
 * To change this template use File | Settings | File Templates.
 */

@HttpCache(allow = false)
@UrlBinding("/logout")
public class LogoutActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @PermitAll
    @DontValidate
    @DefaultHandler
    public Resolution logout() {
        logger.debug("LOGOUT");

        if (!isShibbolethUser()) {

            // localhost user
            getContext().dropUser();
            return new RedirectResolution(INDEX);
        }

        // Shibboleth user
        getContext().dropUser();
        return new RedirectResolution("https://index.bbmri.cz/Shibboleth.sso/Logout", false);

    }
}
