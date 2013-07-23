package bbmri.action;

import bbmri.action.Project.ProjectActionBean;
import bbmri.entities.Project;
import bbmri.entities.User;
import bbmri.service.LoginService;
import bbmri.service.NotificationService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;


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

    private static final String INDEX = "/login.jsp";

    @SpringBean
    private LoginService loginService;

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

        logger.debug("Display - Context: " + getContext().getIdentifier() );

        getContext().dropUser();
        return new ForwardResolution(INDEX);
    }

    public Resolution login() {
        logger.debug("Login");
        if (user != null) {
            //getContext().setLoggedUser(user);
            getContext().setLoggedUser(user);
            getContext().getMessages().add(new SimpleMessage("Succesfull login"));
        }
        //return new RedirectResolution(DashboardActionBean.class);
        return new RedirectResolution(ProjectActionBean.class);
    }

    @PermitAll
    @DontValidate
    public Resolution logout() {
        logger.debug("LOGOUT");
        getContext().dropUser();
        return new RedirectResolution(INDEX);
    }

    @ValidationMethod
      public void validateUser(ValidationErrors errors){
          if(id != null && password != null){
              user = loginService.login(id, password);
          }

          if(user == null){
              getContext().getMessages().add(
                new SimpleMessage("Login incorrect")
              );

          }
      }

    @DontValidate
    public Resolution cancel(){
          return new RedirectResolution(INDEX);
    }

    @Override
      public Resolution handleValidationErrors(ValidationErrors errors)
      {
          // When field erros occured
          if(errors.hasFieldErrors())
          {
              // Display a global error message
             // errors.addGlobalError(new LocalizableError("allFieldsRequired"));
              getContext().getMessages().add(
                             new SimpleMessage("allFieldsRequired")
                           );
          }

          // Implicit
          return null;
      }

}
