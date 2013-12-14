package cz.bbmri.extension.security.impl;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.action.LoginActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.extension.security.SecurityHandler;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

public class AssociatedSecurityManager extends InstanceBasedSecurityManager implements SecurityHandler
{
    private User user;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private User getUser(ActionBean bean){

        BasicActionBean basicBean = (BasicActionBean) bean;

        // Compare the identifier of given authenticated user
        Boolean set = null != basicBean.getContext().getMyId();

        // In case the identifier set
        // and no instance is assigned
        if(set && user == null){
            // Retrieve the appropriate instance
            user =  basicBean.getLoggedUser();
        }
        // When no identifier
        else if(!set){
            // Forget the user
            user = null;
        }

        // Return cache
        return user;
    }

    private Set<SystemRole> getRoles(ActionBean bean){
        return ((BasicActionBean) bean).getRoles();
    }

    @Override
    protected Boolean isUserAuthenticated(ActionBean bean, Method handler){
        logger.debug("User: " + getUser(bean));

        return null != getUser(bean);
    }


    @Override
    protected Boolean hasRoleName(ActionBean bean, Method handler, String role){

        for(SystemRole systemRole : getRoles(bean)){
            if(systemRole.toString().equals(role)){
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }



    @Override
    public Resolution handleAccessDenied(ActionBean bean, Method handler)
    {
        logger.debug("HandleAccessDenied");
        // If not authenticated user
        if(!isUserAuthenticated(bean, handler))
        {
            logger.debug("Return to login form");
            // Redirect to the login form
            return new ForwardResolution(LoginActionBean.class);
        }

        // Display the error message
        return new ErrorResolution(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

