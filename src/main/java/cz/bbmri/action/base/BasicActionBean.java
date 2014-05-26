package cz.bbmri.action.base;

import cz.bbmri.action.DashboardActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.extension.context.TheActionBeanContext;
import cz.bbmri.extension.localization.LocalePicker;
import cz.bbmri.service.UserService;
import cz.bbmri.service.exceptions.AuthorizationException;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Base for all action beans
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@HttpCache(allow = false)
public class BasicActionBean extends Links implements ActionBean {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserService userService;

    private TheActionBeanContext ctx;

    // context of application, stores logged user, ...
    @Override
    public void setContext(ActionBeanContext ctx) {
        this.ctx = (TheActionBeanContext) ctx;
    }

    @Override
    public TheActionBeanContext getContext() {
        return ctx;
    }

    private ComponentManager componentManager;

    public ComponentManager getComponentManager() {
        return componentManager;
    }

    protected void setComponentManager(ComponentManager componentManager) {
        this.componentManager = componentManager;
    }

    /**
     * Get all attributes from shibboleth, which are stored in header for authentication of user
     *
     * @return instance of user with all shibboleth attributes set
     */
    public User initializeShibbolethUser() {
        User user = new User();
        user.setDisplayName(getContext().getShibbolethDisplayName());
        user.setEmail(getContext().getShibbolethMail());
        user.setEppn(getContext().getShibbolethEppn());
        user.setTargetedId(getContext().getShibbolethTargetedId());
        user.setPersistentId(getContext().getShibbolethPersistentId());
        user.setOrganization(getContext().getShibbolethOrganization());
        user.setAffiliation(getContext().getShibbolethAffiliation());
        user.setName(getContext().getShibbolethGivenName());
        user.setSurname(getContext().getShibbolethSn());
        user.setShibbolethUser(true);
        return user;
    }

    public boolean isShibbolethUser() {
        return null != getContext().getShibbolethSession();
    }

    public boolean shibbolethSignIn(User user) {
        Long id;

        try {
            id = userService.loginShibbolethUser(user, getContext().getLocale());
            logger.debug("User have sufficient rights to access BBMRI - " +
                    "user: " + user + "affiliation: " + user.getAffiliation());
        } catch (AuthorizationException ex) {
            logger.debug("User doesn't have sufficient rights to access BBMRI - " +
                    "user: " + user + "affiliation: " + user.getAffiliation());
            return false;
        }

        if (id == null) {
            logger.debug("Id of user is null during sign in - " +
                    "user: " + user);
            return false;
        }

        getContext().setMyId(id);

        return true;
    }

    // This method is used if user is accessing LoginActionBean
    // It is dedicated for user accessing directly index page
    public Resolution signInShibbolethOnIndex() {
        User user = initializeShibbolethUser();

        if (!shibbolethSignIn(user)) {
            return new RedirectResolution("/errors/not_authorized_to_access.jsp");
        }

        // Continue to Dashboard
        return new RedirectResolution(DashboardActionBean.class);
    }

    public User getLoggedUser() {
        Long id = ctx.getMyId();
        return userService.get(id);
    }

    public Set<SystemRole> getRoles() {
        return getLoggedUser().getSystemRoles();

    }

    public String getName() {
        return this.getClass().getName();
    }

    protected void successMsg() {
            getContext().getMessages().add(
                    new LocalizableMessage("cz.bbmri.action.base.BasicActionBean.success"));
    }

    public String getLastUrl() {

        HttpServletRequest req = getContext().getRequest();
        StringBuilder sb = new StringBuilder();

        // Start with the URI and the path
        String uri = (String)
                req.getAttribute("javax.servlet.forward.request_uri");
        String path = (String)
                req.getAttribute("javax.servlet.forward.path_info");
        if (uri == null) {
            uri = req.getRequestURI();
            path = req.getPathInfo();
        }
        sb.append(uri);
        if (path != null) {
            sb.append(path);
        }

        // Now the request parameters
        sb.append('?');
        Map<String, String[]> map =
                new HashMap<String, String[]>(req.getParameterMap());

        // Remove previous locale parameter, if present.
        map.remove(LocalePicker.LOCALE);

        // Append the parameters to the URL
        for (String key : map.keySet()) {
            String[] values = map.get(key);

            for (String value : values) {
                sb.append(key).append('=').append(value).append('&');
            }
        }
        // Remove the last '&'
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
