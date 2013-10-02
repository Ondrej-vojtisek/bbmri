package bbmri.service;

import bbmri.entities.RoleType;
import bbmri.entities.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public interface UserService extends BasicService<User>{

    User create(User user);

    User changeAdministrator(Long oldAdminId, Long newAdminId);

    User setRole(Long userId, RoleType roleType);

    User removeRole(Long userId, RoleType roleType);

    User eagerGet(Long id, boolean judgedProjects, boolean project, boolean biobank);

    /* Dummy - tohle musi byt udelano jinym zpusobem. Kontrolou zda dany uzivatel muze byt administrator */
    List<User> getNonAdministratorUsers();


   /*
    To implement:

    List<User> findUserByName (String firstName, String surname);

    List<User> findUserByEmail (String email);

    - also with check if new user can be administrator
    void setNewAdministrator(Long userId)
   */
}
