package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.converter.PasswordTypeConverter;
import cz.bbmri.dao.SettingsDAO;
import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.Settings;
import cz.bbmri.entity.User;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.LongTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@HttpCache(allow = false)
@UrlBinding("/login")
public class LoginActionBean extends BasicActionBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String INDEX = "/index.jsp";

    @SpringBean
    private UserDAO userDAO;

    @SpringBean
    private SettingsDAO settingsDAO;

    @Validate(converter = LongTypeConverter.class,
            required = true, minvalue = 1, on = "login")
    private Long id;

    @Validate(required = true, converter = PasswordTypeConverter.class, on = "login")
    private String password;

    private User user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        getContext().dropUser();
        return new ForwardResolution(View.LOGIN);
    }

    @HandlesEvent("login")
    public Resolution login() {
        if (user != null) {
            getContext().setLoggedUser(user);
            getContext().getMessages().add(new LocalizableMessage("cz.bbmri.action.base.BasicActionBean.loginSuccess"));

            if (user.getSettings() != null) {
                // Switch language to one prefered by user - and stored in DB
                if (!user.getSettings().getLocaleSettings().equals(getContext().getLocale())) {
                    return new RedirectResolution(DashboardActionBean.class).addParameter("locale", user.getSettings().getLocale());
                }
            }
        }
        return new RedirectResolution(DashboardActionBean.class);
    }


    @ValidationMethod
    public void validateUser() {

        if (id == null || password == null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
            return;
        }

        User userDB = userDAO.get(id);
        if (userDB == null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
            return;
        }

        if (userDB.getPassword() == null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
            return;
        }

        if (!userDB.getPassword().equals(password)) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
            return;
        }

        // hack to initiace userSetting for all test users during login
        if (userDB.getSettings() == null) {
            Settings setting = new Settings();
            setting.setUser(userDB);
            if (getContext().getLocale() != null) {
                setting.setLocale(getContext().getLocale().getLanguage());
            }
            settingsDAO.save(setting);
        }

        Date date = new Date();

        userDB.setLastLogin(new Timestamp(date.getTime()));
        userDAO.save(userDB);
        user = userDB;
    }

    @DontValidate
    @HandlesEvent("cancel")
    public Resolution cancel() {
        return new RedirectResolution(LoginActionBean.class);
    }

}
