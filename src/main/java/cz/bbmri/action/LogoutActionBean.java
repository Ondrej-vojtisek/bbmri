package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.action.map.View;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@HttpCache(allow = false)
@UrlBinding("/logout")
public class LogoutActionBean extends BasicActionBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @PermitAll
    @DontValidate
    @DefaultHandler
    public Resolution logout() {

        if (!isShibbolethUser()) {

            // localhost user
            getContext().dropUser();
            return new RedirectResolution(View.LOGIN);
        }

        // Shibboleth user
        getContext().dropUser();
        return new RedirectResolution("https://index.bbmri.cz/Shibboleth.sso/Logout", false);

    }
}
