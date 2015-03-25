package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.Shibboleth;
import cz.bbmri.entity.User;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/welcome")
@HttpCache(allow = false)
public class WelcomeActionBean extends BasicActionBean {

    private String name;

    @SpringBean
    private UserDAO userDAO;

    public String getName() {
        return name;
    }


    @DontValidate
    @DefaultHandler
    public Resolution display() {

        if (getContext().getIsShibbolethSession()) {

            Shibboleth shibboleth = Shibboleth.initiate(getContext());

            if (!shibbolethSignIn(shibboleth)) {
                return new ErrorResolution(HttpServletResponse.SC_UNAUTHORIZED);
            }

            Long id = getContext().getMyId();

            User user = userDAO.get(id);

            if (user != null) {
                getContext().getMessages().add(new SimpleMessage("Succesfull login"));

                // Switch language to one prefered by user - and stored in DB
                if (!user.getSettings().getLocale().equals(getContext().getLocale())) {
                    return new RedirectResolution(DashboardActionBean.class).addParameter("locale", user.getSettings().getLocale());
                }
            }

            return new ForwardResolution(DashboardActionBean.class);
        }

        return new ForwardResolution(LoginActionBean.class);
    }

}
