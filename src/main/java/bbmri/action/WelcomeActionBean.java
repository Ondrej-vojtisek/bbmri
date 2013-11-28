package bbmri.action;

import bbmri.action.project.ProjectActionBean;
import bbmri.entities.User;
import bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.11.13
 * Time: 16:06
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/welcome")
public class WelcomeActionBean extends BasicActionBean {


    private String name;

    @SpringBean
    private UserFacade userFacade;

    public String getName() {
        return name;
    }

//    private void initializeUser() {
//        user = new User();
//        user.setDisplayName(getContext().getShibbolethDisplayName());
//        user.setEmail(getContext().getShibbolethMail());
//        user.setEppn(getContext().getShibbolethEppn());
//        user.setOrganization(getContext().getShibbolethOrganization());
//        user.setAffiliation(getContext().getShibbolethAffiliation());
//        user.setName(getContext().getShibbolethGivenName());
//        user.setSurname(getContext().getShibbolethSn());
//    }

    @DontValidate
    @DefaultHandler
    public Resolution display() {

        if (getContext().getIsShibbolethSession()) {

            User user1 = new User();
            user1.setDisplayName(getContext().getShibbolethDisplayName());
            user1.setEmail(getContext().getShibbolethMail());
            user1.setEppn(getContext().getShibbolethEppn());
            user1.setOrganization(getContext().getShibbolethOrganization());
            user1.setAffiliation(getContext().getShibbolethAffiliation());
            user1.setName(getContext().getShibbolethGivenName());
            user1.setSurname(getContext().getShibbolethSn());

            Long id = userFacade.loginShibbolethUser(user1);
//
            user1 = userFacade.get(id);

            if (user1 != null) {
                getContext().setLoggedUser(user1);
                getContext().getMessages().add(new SimpleMessage("Succesfull login"));
            }

            return new RedirectResolution(DashboardActionBean.class);
        }

        logger.debug("WELCOME SCREEN");

        return new ForwardResolution(WELCOME);
    }

}
