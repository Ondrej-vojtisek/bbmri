package bbmri.action;

import bbmri.entities.User;
import bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.11.13
 * Time: 13:59
 * To change this template use File | Settings | File Templates.
 */
public class LoginShibbolethActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private UserFacade userFacade;

    private User user;

    private void initializeUser(){
        if(getContext().getIsShibbolethSession()){

        }
    }

    @PermitAll
    @DefaultHandler
    public Resolution display() {

        if(getContext().getIsShibbolethSession()){

        }else{

        }


        return new ForwardResolution(WELCOME);
    }


}
