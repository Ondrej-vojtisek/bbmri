package cz.bbmri.extension.security;

import cz.bbmri.action.LoginActionBean;
import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.Shibboleth;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Set;

/***
 * Excerpted from "Stripes: and Java Web Development is Fun Again",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit http://www.pragmaticprogrammer.com/titles/fdstr for more book information.
***/

/**
 * Checks if user is authenticated and if he is authorized (has role) to access specific resources.
 *
 * @author Jan Sochor (jan.sochor@icebolt.info) - THALAMOSS project thalamoss-data.ics.muni.cz
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class AssociatedSecurityManager extends InstanceBasedSecurityManager implements SecurityHandler {

    private User user;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private User getUser(ActionBean bean) {



        BasicActionBean basicBean = (BasicActionBean) bean;

        // Compare the identifier of given authenticated user
        Boolean set = false;
        if (basicBean.getContext().getMyId() != null) {

            set = true;
        }

        // In case the identifier set
        // and no instance is assigned
        if (set && user == null) {
            // Retrieve the appropriate instance
            user = basicBean.getLoggedUser();
        }

        // When no identifier and shibboleth user
        // This solves the situation when shibboleth user tries to access direct URL inside bbmri
        // instead of index
        else if (!set && basicBean.isShibbolethUser()) {
            // Sign in should return true on success
            Shibboleth shibboleth = Shibboleth.initiate(basicBean.getContext());

            if (!basicBean.shibbolethSignIn(shibboleth)) {
                user = null;
                logger.debug("Sign in of shibboleth user failed");
                return null;
            }

            logger.debug("Sign in of shibboleth user succeeded");

        } else if (!set) {
            // Forget the user
            user = null;
        }

        // Return cache
        return user;
    }

    private Set<SystemRole> getRoles(ActionBean bean) {
        // get roles of logged uesr
        return ((BasicActionBean) bean).getRoles();
    }

    @Override
    protected Boolean isUserAuthenticated(ActionBean bean, Method handler) {
        System.err.println("AssociateSecMan: " + getUser(bean));
        return null != getUser(bean);
    }


    @Override
    protected Boolean hasRoleName(ActionBean bean, Method handler, String role) {

        for (SystemRole systemRole : getRoles(bean)) {
            if (systemRole.toString().equals(role)) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }


    @Override
    public Resolution handleAccessDenied(ActionBean bean, Method handler) {
        logger.debug("HandleAccessDenied");

        // If not authenticated user
        if (!isUserAuthenticated(bean, handler)) {
            logger.debug("Return to login form");
            // Redirect to the login form
            return new ForwardResolution(LoginActionBean.class);
        }

        // Display the error message

        //return new ErrorResolution(HttpServletResponse.SC_UNAUTHORIZED);
        return new ForwardResolution("/errors/not_authorized_to_access.jsp");
    }
}

