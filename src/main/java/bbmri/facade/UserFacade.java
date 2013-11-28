package bbmri.facade;

import bbmri.entities.User;
import bbmri.entities.enumeration.SystemRole;
import bbmri.entities.webEntities.RoleDTO;

import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public interface UserFacade {

    List<RoleDTO> getRoles(Long userId);

    void update(User user);

    void create(User user);

    void remove(Long userId);

    User get(Long userId);

    List<User> all();

    void setAsDeveloper(Long userId);

    void setAsAdministrator(Long userId);

    void removeAdministratorRole(Long userId);

    void removeDeveloperRole(Long userId);

    List<User> getAdministrators();

    List<User> getDevelopers();

    Set<SystemRole> getSystemRoles(Long userId);

    User login(Long id, String password);

    List<User> find(User user, int requiredResults);

    User get(String eppn);

    Long loginShibbolethUser(User user);


    /*
    with permission check
    void setNewSystemAdministrator(Long userId);

    with permission check
    void removeSystemAdministrator(Long userId);

    List<User> findUser(String firstname, String surname, String email);
    */
}
