package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.facade.UserFacade;
import cz.bbmri.facade.exceptions.AuthorizationException;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 22.10.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */

//@UrlBinding("/login/{$event}")
@HttpCache(allow=false)
@UrlBinding("/login")
public class LoginActionBean extends BasicActionBean implements ValidationErrorHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String INDEX = "/index.jsp";

    @SpringBean
    private UserFacade userFacade;

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

    private User initializeUser() {
          User user = new User();
          user = new User();
          user.setDisplayName(getContext().getShibbolethDisplayName());
          user.setEmail(getContext().getShibbolethMail());
          user.setEppn(getContext().getShibbolethEppn());
          user.setOrganization(getContext().getShibbolethOrganization());
          user.setAffiliation(getContext().getShibbolethAffiliation());
          user.setName(getContext().getShibbolethGivenName());
          user.setSurname(getContext().getShibbolethSn());
          user.setShibbolethUser(true);
          return user;
      }

    @DontValidate
    @DefaultHandler
    public Resolution display() {

        if (getContext().getIsShibbolethSession()) {

            User user = initializeUser();
            Long id = null;
            try {
                id = userFacade.loginShibbolethUser(user);
            } catch (AuthorizationException ex) {
                return new ErrorResolution(HttpServletResponse.SC_UNAUTHORIZED);
            }

            user = userFacade.get(id);

            if (user != null) {
                getContext().setLoggedUser(user);
                getContext().getMessages().add(new SimpleMessage("Succesfull login"));
            }

            return new ForwardResolution(DashboardActionBean.class);
        }


        getContext().dropUser();

        return new ForwardResolution(INDEX);
    }

    public Resolution login() {
        if (user != null) {
            getContext().setLoggedUser(user);
            getContext().getMessages().add(new SimpleMessage("Succesfull login"));
        }

        return new RedirectResolution(DashboardActionBean.class);
    }

    @PermitAll
    @DontValidate
    public Resolution logout() {
        logger.debug("LOGOUT");
        getContext().dropUser();
        if (getContext().getIsShibbolethSession()) {
            // false means to not append address to current context

            return new RedirectResolution("https://index.bbmri.cz/Shibboleth.sso/Logout", false);
        }
        return new RedirectResolution(INDEX);
    }

    @ValidationMethod
    public void validateUser(ValidationErrors errors) {

        if (id != null && password != null) {

            user = userFacade.login(id, password);
        }
        if (user == null) {

            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.LoginActionBean.loginIncorrect"));
        }
    }

    @DontValidate
    public Resolution cancel() {
        return new ForwardResolution(INDEX);
    }

    @Override
    public Resolution handleValidationErrors(ValidationErrors errors) {

        // When field erros occured
//        if (errors.hasFieldErrors()) {
//            // Display a global error message
//            // errors.addGlobalError(new LocalizableError("allFieldsRequired"));
//            getContext().getMessages().add(
//                    new LocalizableError("allFieldsRequired")
//            );
//        }


        // Implicit
        return null;
    }


}
