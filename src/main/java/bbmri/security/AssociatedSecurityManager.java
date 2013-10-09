package bbmri.security;

import bbmri.action.BasicActionBean;
import bbmri.action.LoginActionBean;
import bbmri.entities.User;
import bbmri.entities.enumeration.RoleType;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stripesstuff.plugin.security.SecurityHandler;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Set;

public class AssociatedSecurityManager extends J2EESecurityManager implements SecurityHandler
{
    private User user;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected User getUser(ActionBean bean){


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

    protected Set<RoleType> getRoles(ActionBean bean){
        return ((BasicActionBean) bean).getRoles();
    }

    @Override
    protected Boolean isUserAuthenticated(ActionBean bean, Method handler){
        return null != getUser(bean);
    }

    @Override
    protected Boolean hasRole(ActionBean bean, Method handler, String role){
        /* Get all roles of logged user and compares them with requirements*/

        for(RoleType roleType : getRoles(bean)){
            if(roleType.toString().equals(role)){
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    @Override
    public Resolution handleAccessDenied(ActionBean bean, Method handler)
    {
        // If not authenticated user
        if(!isUserAuthenticated(bean, handler))
        {
            // Redirect to the login form
            return new RedirectResolution(LoginActionBean.class);
        }

        // Display the error message
        return new ErrorResolution(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

