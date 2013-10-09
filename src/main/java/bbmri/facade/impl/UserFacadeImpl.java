package bbmri.facade.impl;

import bbmri.entities.BiobankAdministrator;
import bbmri.entities.ProjectAdministrator;
import bbmri.entities.User;
import bbmri.entities.enumeration.RoleType;
import bbmri.entities.webEntities.RoleDTO;
import bbmri.facade.UserFacade;
import bbmri.service.BiobankAdministratorService;
import bbmri.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
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
        userService.setSystemRole(userId, RoleType.DEVELOPER);
    }

    public void setAsAdministrator(Long userId) {
        notNull(userId);
        userService.setSystemRole(userId, RoleType.DEVELOPER);
    }

    public void removeSystemRole(Long userId, RoleType roleType) {
        notNull(userId);
        notNull(roleType);
        userService.removeSystemRole(userId, roleType);
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
        userService.removeSystemRole(userId, RoleType.ADMINISTRATOR);
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
            userService.removeSystemRole(userId, RoleType.DEVELOPER);
        }


    public List<User> getAdministrators() {
        return userService.getAllByRole(RoleType.ADMINISTRATOR);
    }

    public List<User> getDevelopers() {
        return userService.getAllByRole(RoleType.DEVELOPER);
    }

    public Set<RoleType> getRoleTypes(Long userId) {
        notNull(userId);
        User userDB = userService.get(userId);
        if (userDB == null) {
            return null;
            // TODO: exception
        }
        return userDB.getRoleTypes();

                /* Add general roles of user */

   /*
           for (RoleType role : userDB.getRoleTypes()) {
            RoleDTO newRole = new RoleDTO();
            newRole.setSubject(role.toString());
            newRole.setType(role.getClass());
            newRole.setPermission(null);
            newRole.setReferenceId(null);
            results.add(newRole);
        }
        */
    }
}
