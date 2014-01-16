package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.facade.UserFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 22.12.13
 * Time: 12:45
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@HttpCache(allow = false)
@UrlBinding("/notAuthorized")
public class ErrorActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final String NOT_AUTHORIZED = "/errors/not_authorized_to_access.jsp";

    @SpringBean
    private UserFacade userFacade;

    public List<User> getDevelopers(){
        return userFacade.getDevelopers();
    }


    @DefaultHandler
    public Resolution notEmployee(){
        return new ForwardResolution(NOT_AUTHORIZED);
    }
}
