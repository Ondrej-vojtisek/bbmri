package bbmri.facade.impl;

import bbmri.entities.BiobankAdministrator;
import bbmri.entities.ProjectAdministrator;
import bbmri.entities.Role;
import bbmri.entities.User;
import bbmri.entities.webEntities.RoleDTO;
import bbmri.facade.UserFacade;
import bbmri.service.BiobankAdministratorService;
import bbmri.service.RoleService;
import bbmri.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private RoleService roleService;

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
        /* Add general roles of user */
        for (Role role : userDB.getRoles()) {
            RoleDTO newRole = new RoleDTO();
            newRole.setSubject(role.getName());
            newRole.setType(role.getClass());
            newRole.setPermission(null);
            newRole.setReferenceId(null);

            results.add(newRole);
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

    public List<User> all(){
        return userService.all();
    }

    public void create(User user){
        notNull(user);
        userService.create(user);
    }

    public void remove(Long userId){
        notNull(userId);
        userService.remove(userId);
    }

    public User get(Long userId){
            notNull(userId);
            return userService.get(userId);
        }
}
