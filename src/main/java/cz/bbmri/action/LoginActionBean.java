package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.LongTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 22.10.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */

@HttpCache(allow = false)
@UrlBinding("/login")
public class LoginActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String INDEX = "/index.jsp";

    @SpringBean
    private UserService userService;

    @Validate(converter = LongTypeConverter.class,
            required = true, minvalue = 1)
    private Long id;

    @Validate(required = true)
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

        logger.debug("Login display");
        getContext().dropUser();
        return new ForwardResolution(LOGIN);
    }

    public Resolution login() {
        if (user != null) {
            getContext().setLoggedUser(user);
            getContext().getMessages().add(new LocalizableMessage("cz.bbmri.action.base.BasicActionBean.loginSuccess"));
        }

        return new RedirectResolution(DashboardActionBean.class);
    }


    @ValidationMethod
    public void validateUser() {

        if (id != null && password != null) {

            user = userService.login(id, password, getContext().getLocale());
        }
        if (user == null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
        }
    }

    @DontValidate
    public Resolution cancel() {
        return new ForwardResolution(INDEX);
    }

}
