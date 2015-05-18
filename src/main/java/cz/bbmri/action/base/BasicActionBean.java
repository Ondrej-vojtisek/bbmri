package cz.bbmri.action.base;

import cz.bbmri.action.DashboardActionBean;
import cz.bbmri.action.map.*;
import cz.bbmri.dao.SettingsDAO;
import cz.bbmri.dao.ShibbolethDAO;
import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.Role;
import cz.bbmri.entity.Settings;
import cz.bbmri.entity.Shibboleth;
import cz.bbmri.entity.User;
import cz.bbmri.entity.webEntities.ComponentManager;
import cz.bbmri.extension.context.TheActionBeanContext;
import cz.bbmri.extension.localization.LocalePicker;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Date;

/**
 * Base for all action beans
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@HttpCache(allow = false)
public abstract class BasicActionBean implements ActionBean {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserDAO userDAO;

    @SpringBean
    private SettingsDAO settingsDAO;

    @SpringBean
    private ShibbolethDAO shibbolethDAO;

    private TheActionBeanContext ctx;


    // List of predefined constants for URL attribute binding
    public static final String BIND_IDENTIFIER = "identifier";

    // Appropriate URL attribute
    // values intentionally taken
    // from application requstes
    protected String identifier;

    // context of application, stores logged user, ...
    @Override
    public void setContext(ActionBeanContext ctx) {
        this.ctx = (TheActionBeanContext) ctx;
    }

    @Override
    public TheActionBeanContext getContext() {
        return ctx;
    }

    public boolean isShibbolethUser() {
        return null != getContext().getShibbolethSession();
    }

    public boolean shibbolethSignIn(Shibboleth shibboleth) {

//        if (!shibboleth.isAuthorized()) {
//            logger.debug("User doesn't have sufficient rights to access BBMRI - " +
//                    "user: " + shibboleth.getSurname() + "affiliation: " + shibboleth.getAffiliation());
//            return false;
//        }

        Shibboleth shibbolethDB = shibbolethDAO.get(
                shibboleth.getEppn(),
                shibboleth.getTargeted(),
                shibboleth.getPersistent());

        User user;

        if (shibbolethDB == null) {
            // new user to system

            user = new User();
            user.setCreated(new Date());

            if(shibboleth.isAuthorized()){
                user.getRole().add(Role.AUTHORIZED);
            }

            // necessary to assign id
            shibboleth.setUser(user);
            user.setShibboleth(shibboleth);

            userDAO.save(user);

            // initiate user settings
            if (user.getSettings() == null) {
                Settings settings = new Settings();
                settings.setUser(user);
                if (getContext().getLocale() != null) {
                    settings.setLocale(getContext().getLocale().getLanguage());
                }
                settingsDAO.save(settings);
            }

            shibboleth.setUser(user);
            shibbolethDAO.save(shibboleth);

        } else {
            user = shibbolethDB.getUser();
            shibbolethDAO.save(shibbolethDB);
        }

        getContext().setMyId(user.getId());

        return true;
    }

    // This method is used if user is accessing LoginActionBean
    // It is dedicated for user accessing directly index page
    public Resolution signInShibbolethOnIndex() {
        Shibboleth shibboleth = Shibboleth.initiate(getContext());

        if (!shibbolethSignIn(shibboleth)) {
            return new ErrorResolution(HttpServletResponse.SC_UNAUTHORIZED);
        }

        // Continue to Dashboard
        return new RedirectResolution(DashboardActionBean.class);
    }

    public User getLoggedUser() {
        Long id = ctx.getMyId();
        return userDAO.get(id);
    }

    public Set<Role> getRoles() {
        return getLoggedUser().getRole();

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

    /**
     * How to handle exceptions - print then for user if possible or ad then to logger.
     *
     * @param errors
     * @param ex
     */
    protected void operationFailed(ValidationErrors errors, Exception ex) {
        if (errors != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.fail"));
        }
        if (ex != null) {
            logger.error(ex.getLocalizedMessage());
        }
    }

    public Component getComponent() {
         // Retrieve the instance
         return Component.INSTANCE;
     }


}
