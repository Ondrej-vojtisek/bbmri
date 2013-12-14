package cz.bbmri.facade.impl;

import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.webEntities.RoleDTO;
import cz.bbmri.facade.UserFacade;
import cz.bbmri.facade.exceptions.AuthorizationException;
import cz.bbmri.service.BiobankAdministratorService;
import cz.bbmri.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.10.13
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */

@Controller("userFacade")
public class UserFacadeImpl extends BasicFacade implements UserFacade {

    private static final int MAXIMUM_FIND_RESULTS = 5;

    @Autowired
    private UserService userService;

    @Autowired
    private BiobankAdministratorService biobankAdministratorService;

    public List<RoleDTO> getRoles(Long userId) {
        notNull(userId);
        List<RoleDTO> results = new ArrayList<RoleDTO>();

        User userDB = userService.eagerGet(userId, false, true, true);

        if (userDB == null) {
            return null;
        }

        /* Add all biobanks of user */
        Set<BiobankAdministrator> baList = userDB.getBiobankAdministrators();
        for (BiobankAdministrator ba : baList) {
            RoleDTO newRole = new RoleDTO();
            newRole.setSubject(ba.getBiobank().getName());
            newRole.setType(ba.getClass());
            newRole.setPermission(ba.getPermission());
            newRole.setReferenceId(ba.getBiobank().getId());

            results.add(newRole);
        }

        /* Add all projects of user */
        List<ProjectAdministrator> paList = userDB.getProjectAdministrators();
        for (ProjectAdministrator pa : paList) {
            RoleDTO newRole = new RoleDTO();
            newRole.setSubject(pa.getProject().getName());
            newRole.setType(pa.getClass());
            newRole.setPermission(pa.getPermission());
            newRole.setReferenceId(pa.getProject().getId());

            results.add(newRole);
        }

        return results;
    }

    public void update(User user) {
        notNull(user);
        userService.update(user);
    }

    public List<User> all() {
        return userService.all();
    }

    public void create(User user) {
        notNull(user);
        user.setShibbolethUser(false);
        user = userService.create(user);
        userService.setSystemRole(user.getId(), SystemRole.USER);
    }

    public void remove(Long userId) {
        notNull(userId);
        userService.remove(userId);
    }

    public User get(Long userId) {
        notNull(userId);
        return userService.get(userId);
    }

    public void setAsDeveloper(Long userId) {
        notNull(userId);
        userService.setSystemRole(userId, SystemRole.DEVELOPER);
    }

    public void setAsAdministrator(Long userId) {
        notNull(userId);
        userService.setSystemRole(userId, SystemRole.DEVELOPER);
    }

    public void removeSystemRole(Long userId, SystemRole systemRole) {
        notNull(userId);
        notNull(systemRole);
        userService.removeSystemRole(userId, systemRole);
    }

    public void removeAdministratorRole(Long userId) {
        notNull(userId);
        User userDB = userService.get(userId);
        if (userDB == null) {
            return;
            // TODO: exception
        }
        if (getAdministrators().size() == 1) {
            // TODO Exception
            // can't remove last administrator
            return;
        }
        userService.removeSystemRole(userId, SystemRole.ADMINISTRATOR);
    }

    public void removeDeveloperRole(Long userId) {
        notNull(userId);
        User userDB = userService.get(userId);
        if (userDB == null) {
            return;
            // TODO: exception
        }
        if (getDevelopers().size() == 1) {
            // TODO Exception
            // can't remove last administrator
            return;
        }
        userService.removeSystemRole(userId, SystemRole.DEVELOPER);
    }


    public List<User> getAdministrators() {
        return userService.getAllByRole(SystemRole.ADMINISTRATOR);
    }

    public List<User> getDevelopers() {
        return userService.getAllByRole(SystemRole.DEVELOPER);
    }

    public Set<SystemRole> getSystemRoles(Long userId) {
        notNull(userId);
        User userDB = userService.get(userId);
        if (userDB == null) {
            return null;
            // TODO: exception
        }
        return userDB.getSystemRoles();
    }

    public User login(Long id, String password) {
        notNull(id);
        notNull(password);

        User userDB = userService.get(id);
        if (userDB == null) {
            return null;
        }
        if (!userDB.getPassword().equals(password)) {
           return null;
        }

        userDB.setLastLogin(new Date());
        userService.update(userDB);
        return userDB;
    }

    public List<User> find(User user, int requiredResults) {
        if (user == null) {
            return null;
        }
        if (requiredResults < 1) {
            requiredResults = MAXIMUM_FIND_RESULTS;
        }

        return userService.find(user, requiredResults);

    }

    public User get(String eppn) {
        notNull(eppn);
        return userService.get(eppn);
    }

    public Long loginShibbolethUser(User user) throws AuthorizationException {

        if (user == null) {
            throw new IllegalArgumentException("Object can't be a null object> User: " + user);
        }

        if (!user.isEmployee()) {
            throw new AuthorizationException("Only employees are authorized to access");
        }

        User userDB = userService.get(user.getEppn());

        if (userDB == null) {
            userDB = userService.create(user);
            user.setId(userDB.getId());
        } else {
            /* If user changed its credentials in system of IdentityProvider then we want to
            * make local user stored in database up-to-date. */
            user.setId(userDB.getId());
        }

        user.setLastLogin(new Date());
        userService.update(userDB);
        return userDB.getId();
    }
}
