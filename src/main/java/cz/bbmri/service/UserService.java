package cz.bbmri.service;

import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.Notification;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.User;
import cz.bbmri.facade.exceptions.AuthorizationException;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    List<User> all();

    User get(Long id);

    boolean remove(Long id);

    boolean update(User t);

    Integer count();

    List<User> allOrderedBy(String orderByParam, boolean desc);

    boolean create(User user, Locale locale);

    boolean setSystemRole(Long userId, SystemRole systemRole);

    boolean removeSystemRole(Long userId, SystemRole systemRole);

    List<User> getAllByRole(SystemRole systemRole);

    List<User> find(User user, int requiredResults);

    User get(String eppn, String targetedId, String persistentId);

    boolean setAsDeveloper(Long userId, ValidationErrors errors);

    boolean setAsAdministrator(Long userId, ValidationErrors errors);

    boolean removeAdministratorRole(Long userId, ValidationErrors errors);

    boolean removeDeveloperRole(Long userId, ValidationErrors errors);

    User login(Long id, String password);

    Long loginShibbolethUser(User user, Locale locale) throws AuthorizationException;

}