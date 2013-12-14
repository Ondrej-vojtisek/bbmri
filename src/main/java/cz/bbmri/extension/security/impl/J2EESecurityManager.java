package cz.bbmri.extension.security.impl;

import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.config.DontAutoLoad;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import org.stripesstuff.plugin.security.SecurityManager;

/**
 * Security manager that implements the J2EE security annotations.
 * In this, method level annotations override class level annotations. Apart from that:<ol>
 * <li>@{@link javax.annotation.security.DenyAll} denies access,</li>
 * <li>otherwise, @{@link javax.annotation.security.PermitAll} allows access for all roles,</li>
 * <li>otherwise, @{@link javax.annotation.security.RolesAllowed} lists the roles that allow access</li>
 * </ol>
 *
 * @author Oscar Westra van Holthe - Kind
 * @version
 */
@DontAutoLoad
public class J2EESecurityManager implements SecurityManager
{


    @SpringBean
    private UserService userService;


    /**
     * Logger for this class.
     */
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Determines if access for the given execution context is allowed. The security manager is used to determine if
     * access is allowed (to handle an event) or if access is not denied (thus allowing the display of error messages
     * for binding and/or validation errors for a secured event). If the latter would not be checked, a user can (even
     * if only theoretically) see an error message, correct his input, and then see an &quot;access forbidden&quot;
     * message.
     * <p>
     * If required contextual information (like what data is affected) is not available, no decision should be made.
     * This is to ensure that access is not denied when required data is missing because of a binding and/or validation
     * error.
     *
     * @param bean    the action bean on which to perform the action
     * @param handler the event handler to check authorization for
     * @return {@link Boolean#TRUE} if access is allowed, {@link Boolean#FALSE} if not, and null if no decision can be made
     * @see SecurityManager#getAccessAllowed(ActionBean, java.lang.reflect.Method)
     */
    @Override
    public Boolean getAccessAllowed(ActionBean bean, Method handler)
    {
        // Determine if the event handler allows access
        logger.debug("Determining if access is allowed for " + handler.getName() + " on " + bean.toString());
        Boolean allowed = determineAccessOnElement(bean, handler, handler);

        // If the event handler didn't decide, determine
        // if the action bean class allows access
        // Rinse and repeat for all superclasses
        Class<?> beanClass = bean.getClass();

        // Iterate through the class nesting
        while(allowed == null && beanClass != null)
        {
            logger.debug("Determining if access is allowed for " + beanClass.getName() + " on " + bean.toString());
            allowed = determineAccessOnElement(bean, handler, beanClass);
            beanClass = beanClass.getSuperclass();
        }

        // If the event handler nor the action bean class decided, allow access
        // This default allows access if no security annotations are used
        if(allowed == null)
        {
            allowed = true;
        }

        // Return the decision
        return allowed;
    }

    /**
     * Decide if the annotated element allows access to the current user.
     *
     * @param bean    the action bean; used for security decisions
     * @param handler the event handler; used for security decisions
     * @param element the element to check authorization for
     * @return {@link Boolean#TRUE TRUE} if access is allowed, {@link Boolean#FALSE FALSE} if access is denied, and {@code null} if undecided
     * @see javax.annotation.security.DenyAll
     * @see javax.annotation.security.PermitAll
     * @see javax.annotation.security.RolesAllowed
     */
    protected Boolean determineAccessOnElement(ActionBean bean, Method handler, AnnotatedElement element){

        // Default decision: none
        Boolean allowed = null;

        if(element.isAnnotationPresent(DenyAll.class))
        {
            // The element denies access
            allowed = false;
            logger.debug("DenyAll");
        }
        else if(element.isAnnotationPresent(PermitAll.class))
        {
            // The element allows access to all security roles (i.e. any authenticated user)
            allowed = isUserAuthenticated(bean, handler);
            logger.debug("User: Result:" + allowed);
            logger.debug("PermitAll");
        }
        else
        {
            logger.debug("Try to get which roles are allowed");
            RolesAllowed rolesAllowed = element.getAnnotation(RolesAllowed.class);
            if(rolesAllowed != null)
            {
                // Still need to check if the users is authorized
                allowed = isUserAuthenticated(bean, handler);

                logger.debug("Is userAuthenticated: " + allowed);

                if(allowed == null || allowed.booleanValue())
                {
                    // The element allows access
                    // if the user has one of
                    // the specified roles
                    allowed = false;

                    for(String role : rolesAllowed.value())
                    {
                        Boolean hasRole = hasRole(bean, handler, role);

                        if(hasRole != null && hasRole)
                        {
                            allowed = true;
                            break;
                        }
                    }
                }
            }
        }

        return allowed;

    }


    /**
     * Determine if the user is authenticated. The default implementation is to use {@code getUserPrincipal() != null}
     * on the HttpServletRequest in the ActionBeanContext.
     *
     * @param bean    the current action bean; used for security decisions
     * @param handler the current event handler; used for security decisions
     * @return {@link Boolean#TRUE TRUE} if the user is authenticated, {@link Boolean#FALSE FALSE} if not, and {@code null} if undecided
     */
    protected Boolean isUserAuthenticated(ActionBean bean, Method handler){
        logger.error("Implementation problem! This method should not be called - method in AssociatedSecurityManager should have been called instead.");
        //return bean.getContext().getRequest().getUserPrincipal() != null;
        return Boolean.FALSE;
    }

    /**
     * Determine if the current user has the specified role.
     * Note that '*' is a special role that resolves to any role (see the servlet spec. v2.4, section 12.8).
     *
     * @param bean    the current action bean
     * @param handler the current event handler
     * @param role    the role to check
     * @return {@code true} if the user has the role, and {@code false} otherwise
     */
    protected Boolean hasRole(ActionBean bean, Method handler, String role){
        logger.error("Implementation problem! This method should not be called - method in AssociatedSecurityManager should have been called instead.");
        //return bean.getContext().getRequest().isUserInRole(role);
        return Boolean.FALSE;
    }
}

