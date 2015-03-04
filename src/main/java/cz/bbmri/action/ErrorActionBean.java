package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@PermitAll
@HttpCache(allow = false)
@UrlBinding("/notAuthorized")
public class ErrorActionBean extends BasicActionBean {

    private final String NOT_AUTHORIZED = "/errors/not_authorized_to_access.jsp";

    @SpringBean
    private UserDao userDao;

    public List<User> getDevelopers(){
        return userDao.getAllWithSystemRole(SystemRole.DEVELOPER);
    }

    @DefaultHandler
    public Resolution notEmployee(){
        return new ForwardResolution(NOT_AUTHORIZED);
    }
}
