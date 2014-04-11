package cz.bbmri.service;

import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.User;

import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public interface UserService extends BasicService<User>{

    User create(User user, Locale locale);

    User changeAdministrator(Long oldAdminId, Long newAdminId);

    User setRole(Long userId, SystemRole systemRole);

    User removeRole(Long userId, SystemRole systemRole);

    /* Dummy - tohle musi byt udelano jinym zpusobem. Kontrolou zda dany uzivatel muze byt administrator */
    List<User> getNonAdministratorUsers();

    boolean setSystemRole(Long userId, SystemRole systemRole);

    boolean removeSystemRole(Long userId, SystemRole systemRole);

    List<User> getAllByRole(SystemRole systemRole);

    List<User> find(User user, int requiredResults);

    User get(String eppn, String targetedId, String persistentId);

}