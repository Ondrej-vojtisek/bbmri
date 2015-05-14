package cz.bbmri.action.base;

import cz.bbmri.dao.RoleDAO;
import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.Role;
import cz.bbmri.entity.User;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@PermitAll
public class ErrorActionBean extends ComponentActionBean {

    private final String NOT_AUTHORIZED = "/errors/not_authorized_to_access.jsp";

    @SpringBean
    private RoleDAO roleDAO;

        public List<User> getDevelopers(){

            Role role = roleDAO.get(Role.DEVELOPER.getId());

            if(role != null){
                return new ArrayList<User>(role.getUser());
            }
            return null;
        }

    @DefaultHandler
    public Resolution notEmployee() {
        return new ForwardResolution(NOT_AUTHORIZED);
    }

}
