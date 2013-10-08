package bbmri.facade;

import bbmri.entities.Role;
import bbmri.entities.User;
import bbmri.entities.webEntities.RoleDTO;

import java.util.List;

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

/*
    List<User> getAdministrators();

    List<User> getDevelopers();
*/

    /*
    with permission check
    void setNewSystemAdministrator(Long userId);

    with permission check
    void removeSystemAdministrator(Long userId);

    List<User> findUser(String firstname, String surname, String email);
    */
}
