package cz.bbmri.action.base;

import cz.bbmri.action.DashboardActionBean;
import cz.bbmri.dao.ShibbolethDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.dao.UserSettingDao;
import cz.bbmri.entities.Shibboleth;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.systemAdministration.UserSetting;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.extension.context.TheActionBeanContext;
import cz.bbmri.extension.localization.LocalePicker;
import cz.bbmri.service.UserService;
import cz.bbmri.service.exceptions.AuthorizationException;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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
    private UserDao userDao;

    @SpringBean
    private UserSettingDao userSettingDao;

    @SpringBean
    private ShibbolethDao shibbolethDao;

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


    public boolean isShibbolethUser() {
        return null != getContext().getShibbolethSession();
    }

    public boolean shibbolethSignIn(Shibboleth shibboleth) {

        if (!shibboleth.isAuthorized()) {
            logger.debug("User doesn't have sufficient rights to access BBMRI - " +
                    "user: " + shibboleth.getSurname() + "affiliation: " + shibboleth.getAffiliation());
            return false;
        }

        Shibboleth shibbolethDB = shibbolethDao.get(
                shibboleth.getEppn(),
                shibboleth.getTargetedId(),
                shibboleth.getPersistentId());

        User user;

        if (shibbolethDB == null) {
            // new user to system

            user = new User();
            user.setCreated(new Date());
            user.getSystemRoles().add(SystemRole.USER);
            user.setShibboleth(shibboleth);

            userDao.create(user);

            // initiate user settings
            if (user.getUserSetting() == null) {
                UserSetting setting = new UserSetting();
                setting.setUser(user);
                if (getContext().getLocale() != null) {
                    setting.setLocale(getContext().getLocale().getLanguage());
                }
                userSettingDao.create(setting);
            }

            shibboleth.setUser(user);
            shibbolethDao.create(shibboleth);

        } else {
            user = shibbolethDB.getUser();
            shibbolethDao.update(shibboleth);
        }

        user.setLastLogin(new Date());

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
        return userDao.get(id);
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
