package cz.bbmri.extension.security;

import cz.bbmri.action.LoginActionBean;
import cz.bbmri.action.base.BasicActionBean;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ErrorResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.config.ConfigurableComponent;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.exception.StripesRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stripesstuff.plugin.security.SecurityManager;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Security interceptor for the Stripes framework. Determines if handling the event
 * for the current execution context is allowed. Execution is allowed if there is no
 * security manager, or if the security manager allows it. The implementation based
 * on previous implementation available as org.stripesstuff project.
 *
 * @author Oscar Westra van Holthe - Kind
 * @author Fred Daoud
 *
 * @author Jan Sochor (jan.sochor@icebolt.info) - THALAMOSS project thalamoss-data.ics.muni.cz
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 *
 * @see SecurityManager
 * @see cz.bbmri.extension.security.SecurityHandler
 */
@Intercepts({LifecycleStage.BindingAndValidation, LifecycleStage.CustomValidation, LifecycleStage.EventHandling, LifecycleStage.ResolutionExecution})
public class SecurityInterceptor implements Interceptor, ConfigurableComponent {

    // Key used to store the security manager before processing resolutions
    private static final String MANAGER = java.lang.SecurityManager.class.getName();

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    // The configured security manager
    private SecurityManager securityManager;

    private void log(String msg){
        // Single point to turn of logging
        logger.debug(msg);
    }

    /**
     * Initialize the interceptor.
     *
     * @param configuration -   the configuration being used by Stripes
     * @throws StripesRuntimeException if the security manager cannot be created
     */
    @Override
    public void init(Configuration configuration) throws StripesRuntimeException {
//        log("INIT");

        // Instantiate the security manager
        try {
            // Create new instance of the given security manager
            securityManager = new AssociatedSecurityManager();

        } catch (Exception e) {
            throw new StripesRuntimeException("Failed to configure the SecurityManager: instantiation failed.", e);
        }

        if (securityManager != null) {
//            log("Initialized with the SecurityManager " + securityManager.toString());
        } else {
//            log("Initialized without a SecurityManager, complete access allowed");
        }
    }

    /**
     * Intercept execution.
     *
     * @param executionContext -    the context of the execution being intercepted
     * @return the resulting {@link Resolution}; returns {@link ExecutionContext#proceed()} if all is well
     * @throws Exception on error
     */
    @Override
    public Resolution intercept(ExecutionContext executionContext) throws Exception {
        Resolution resolution;

        if (securityManager != null) {

            // Lifecycle stages of context

            switch (executionContext.getLifecycleStage()) {
                case ActionBeanResolution:
                case HandlerResolution:
                case BindingAndValidation:
                case CustomValidation:
                    resolution = interceptBindingAndValidation(executionContext);
                    break;

                case EventHandling:
                    resolution = interceptEventHandling(executionContext);
                    break;

                case ResolutionExecution:
                    resolution = interceptResolutionExecution(executionContext);
                    break;

                // This should not happen due
                // to @Intercepts annotation
                default:
                    resolution = executionContext.proceed();
                    break;
            }
        } else {
            // There is no security manager, so everything is allowed
            resolution = executionContext.proceed();
        }

        return resolution;
    }

    /**
     * Intercept execution for binding and/or (custom) validations. Checks that the security doesn't deny access before
     * any error messages are shown.
     *
     * @param executionContext the context of the execution being intercepted
     * @return the resulting {@link net.sourceforge.stripes.action.Resolution}; returns {@link ExecutionContext#proceed()} if all is well
     * @throws Exception on error
     */
    Resolution interceptBindingAndValidation(ExecutionContext executionContext) throws Exception {

        Resolution resolution = executionContext.proceed();

        // If there are errors and a resolution to display them, check if access is allowed
        // If explicitly denied, access is denied (and showing errors would be an information leak)
        if (resolution != null && !executionContext.getActionBeanContext().getValidationErrors().isEmpty()) {
            if (Boolean.FALSE.equals(getAccessAllowed(executionContext))) {
                // If the security manager denies access, deny access
//                log("Binding and/or validation failed, and the security manager has denied access.");
                resolution = handleAccessDenied(executionContext.getActionBean(), executionContext.getHandler());
            }
        }

        // Return the result
        return resolution;
    }

    /**
     * Intercept execution for event handling. Checks if the security manager allows access before allowing the event.
     *
     * @param executionContext the context of the execution being intercepted
     * @return the resulting {@link net.sourceforge.stripes.action.Resolution}; returns {@link ExecutionContext#proceed()} if all is well
     * @throws Exception on error
     */
    Resolution interceptEventHandling(ExecutionContext executionContext) throws Exception {

        // If user is accessing index and is using shibboleth than redirect him to dashboard

        BasicActionBean basicBean = (BasicActionBean) executionContext.getActionBean();

        // User tries to access index page using LoginActionBean

        if (executionContext.getActionBean().getClass().equals(LoginActionBean.class)) {

            // if shibboleth, then set user and return resolution to welcome page

            if (basicBean.isShibbolethUser()) {
                return basicBean.signInShibbolethOnIndex();
            }
        }

        // Before handling the event,
        // check if access is allowed
        // If not explicitly allowed,
        // access is denied then
        Resolution resolution;

        if (Boolean.TRUE.equals(getAccessAllowed(executionContext))) {
            resolution = executionContext.proceed();
        } else {
//            log("The security manager has denied access.");
            resolution = handleAccessDenied(executionContext.getActionBean(), executionContext.getHandler());
        }

        return resolution;
    }

    /**
     * Intercept execution for resolution execution. Adds the security manager to the request attributes, which is used
     * to give security tags access to the security manager.
     *
     * @param executionContext the context of the execution being intercepted
     * @return the resulting {@link net.sourceforge.stripes.action.Resolution}; returns {@link ExecutionContext#proceed()} if all is well
     * @throws Exception on error
     */
    Resolution interceptResolutionExecution(ExecutionContext executionContext) throws Exception {

        log("INTERCEPT: interceptResolutionExecution");

        // Before processing the resolution, add the security manager to the request, used (for example) by the security tag
        executionContext.getActionBeanContext().getRequest().setAttribute(SecurityInterceptor.MANAGER, securityManager);

        // Return the selected resolution
        return executionContext.proceed();
    }

    /**
     * Determine if the security manager allows access.
     * The return value of this method is the same as the result of
     * {@link SecurityManager#getAccessAllowed(ActionBean, java.lang.reflect.Method) getAccessAllowed(ActionBean, Method)}
     * of the current security manager, unless there is nu security manager (in which case the event is allowed).
     *
     * @param executionContext the current execution context
     * @return whether or not the security manager allows access, if a decision can be made
     */
    Boolean getAccessAllowed(ExecutionContext executionContext) {
//        log("Checking access for " + executionContext + " at " + executionContext.getLifecycleStage());

        Boolean accessAllowed;
        if (securityManager == null) {
//            log("There is no security manager, so access is allowed by default.");
            accessAllowed = true;
        } else {
            ActionBean actionBean = executionContext.getActionBean();

            Method handler = executionContext.getHandler();

            accessAllowed = securityManager.getAccessAllowed(actionBean, handler);
//            log("Security manager returned access allowed: " + accessAllowed);
        }

        return accessAllowed;
    }

    /**
     * Determine what to do when access has been denied. If the SecurityManager implements the optional interface
     * [@Link SecurityHandler}, ask the SecurityManager. Otherwise, return the HTTP error "forbidden".
     *
     * @param bean    the action bean to which access was denied
     * @param handler the event handler to which access was denied
     * @return the Resolution to be executed when access has been denied
     */
    Resolution handleAccessDenied(ActionBean bean, Method handler) {
        Resolution resolution;

        if (securityManager instanceof SecurityHandler) {
//            log("HandleAccessDenied");
            resolution = ((SecurityHandler) securityManager).handleAccessDenied(bean, handler);
        } else {
            resolution = new ErrorResolution(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return resolution;
    }
}

