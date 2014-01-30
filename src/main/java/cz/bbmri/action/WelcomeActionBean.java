package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.facade.UserFacade;
import cz.bbmri.facade.exceptions.AuthorizationException;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.11.13
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/welcome")
@HttpCache(allow = false)
public class WelcomeActionBean extends BasicActionBean {

    private String name;

    @SpringBean
    private UserFacade userFacade;

    public String getName() {
        return name;
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
            try{
                id = userFacade.loginShibbolethUser(user);
            }catch(AuthorizationException ex){
                return new ErrorResolution(HttpServletResponse.SC_UNAUTHORIZED);
            }

            user = userFacade.get(id);

            if (user != null) {
                getContext().setLoggedUser(user);
                getContext().getMessages().add(new SimpleMessage("Succesfull login"));
            }

            return new ForwardResolution(DashboardActionBean.class);
        }

        return new ForwardResolution(LoginActionBean.class);
    }

}