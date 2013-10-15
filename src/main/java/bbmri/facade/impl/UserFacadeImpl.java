package bbmri.facade.impl;

import bbmri.entities.BiobankAdministrator;
import bbmri.entities.ProjectAdministrator;
import bbmri.entities.User;
import bbmri.entities.enumeration.SystemRole;
import bbmri.entities.webEntities.RoleDTO;
import bbmri.facade.UserFacade;
import bbmri.service.BiobankAdministratorService;
import bbmri.service.LoginService;
import bbmri.service.UserService;
import net.sourceforge.stripes.integration.spring.SpringBean;
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

@Controller
public class UserFacadeImpl extends BasicFacade implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private BiobankAdministratorService biobankAdministratorService;

    @Autowired
    private LoginService loginService;

    //@Autowired
    //private ProjectAdministrator biobankAdministrator;

    public List<RoleDTO> getRoles(Long userId) {
        notNull(userId);
        List<RoleDTO> results = new ArrayList<RoleDTO>();

        User userDB = userService.eagerGet(userId, false, true, true);

        if (userDB == null) {
            return null;
            //TODO exception
        }

        /* Add all biobanks of user */
        List<BiobankAdministrator> baList = userDB.getBiobankAdministrators();
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
        userService.create(user);
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
        if(getAdministrators().size() == 1){
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
            if(getDevelopers().size() == 1){
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

    public User login(Long id, String password){
        notNull(id);
        notNull(password);
        User user = loginService.login(id, password);
        if(user != null){
            logger.debug("SetDate");
            user.setLastLogin(new Date());
            userService.update(user);
        }
        return user;
    }
}
