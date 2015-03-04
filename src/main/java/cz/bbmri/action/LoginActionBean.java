package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.converter.PasswordTypeConverter;
import cz.bbmri.dao.UserDao;
import cz.bbmri.dao.UserSettingDao;
import cz.bbmri.entities.User;
import cz.bbmri.entities.systemAdministration.UserSetting;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.LongTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private UserDao userDao;

    @SpringBean
    private UserSettingDao userSettingDao;

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
        return new ForwardResolution(LOGIN);
    }

    @HandlesEvent("login")
    public Resolution login() {
        if (user != null) {
            getContext().setLoggedUser(user);
            getContext().getMessages().add(new LocalizableMessage("cz.bbmri.action.base.BasicActionBean.loginSuccess"));

            // Switch language to one prefered by user - and stored in DB
            if (!user.getUserSetting().getLocale().equals(getContext().getLocale())) {
                return new RedirectResolution(DashboardActionBean.class).addParameter("locale", user.getUserSetting().getLocale());
            }
        }
        return new RedirectResolution(DashboardActionBean.class);
    }


    @ValidationMethod
    public void validateUser() {

        if (id == null || password == null) {
            System.err.println("1");
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
            return;
        }

        User userDB = userDao.get(id);
        if (userDB == null) {
            System.err.println("2");
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
            return;
        }

        if(userDB.getPassword() == null){
            System.err.println("3");
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
            return;
        }

        if (!userDB.getPassword().equals(password)) {
            System.err.println("4");
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
            return;
        }

        // hack to initiace userSetting for all test users during login
        if (userDB.getUserSetting() == null) {
            UserSetting setting = new UserSetting();
            setting.setUser(userDB);
            if (getContext().getLocale() != null) {
                setting.setLocale(getContext().getLocale().getLanguage());
            }
            userSettingDao.create(setting);
        }

        userDB.setLastLogin(new Date());
        userDao.update(userDB);
        user = userDB;
    }

    @DontValidate
    @HandlesEvent("cancel")
    public Resolution cancel() {
        return new RedirectResolution(LoginActionBean.class, "display");
    }

}
