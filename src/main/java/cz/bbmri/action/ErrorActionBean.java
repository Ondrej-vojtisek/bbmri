package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@PermitAll
@HttpCache(allow = false)
@UrlBinding("/notAuthorized")
public class ErrorActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private final String NOT_AUTHORIZED = "/errors/not_authorized_to_access.jsp";

    @SpringBean
    private UserService userService;

    public List<User> getDevelopers(){
        return userService.getAllByRole(SystemRole.DEVELOPER);
    }

    @DefaultHandler
    public Resolution notEmployee(){
        return new ForwardResolution(NOT_AUTHORIZED);
    }
}
