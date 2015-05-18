package cz.bbmri.action.base;

import cz.bbmri.action.map.View;
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

    @SpringBean
    private RoleDAO roleDAO;

    public List<User> getDevelopers() {

        Role role = roleDAO.get(Role.DEVELOPER.getId());

        if (role == null) {
            return null;
        }
        return new ArrayList<User>(role.getUser());
    }

    public List<User> getAdministrators() {

        Role role = roleDAO.get(Role.ADMIN.getId());

        if (role == null) {
            return null;
        }
        return new ArrayList<User>(role.getUser());
    }

    @DefaultHandler
    public Resolution notEmployee() {
        return new ForwardResolution(View.Error.NOT_AUTHORIZED);
    }

}
