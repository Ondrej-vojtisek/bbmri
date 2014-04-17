package cz.bbmri.service;

import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.exceptions.AuthorizationException;
import cz.bbmri.service.simpleService.*;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;
import java.util.Locale;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface UserService extends All<User>, Get<User>, Remove, Update<User>, Count {

    boolean updateShibbolethUser(User user);

    List<User> allOrderedBy(String orderByParam, boolean desc);

    boolean create(User user, Locale locale);

    boolean setSystemRole(Long userId, SystemRole systemRole, ValidationErrors errors);

    boolean removeSystemRole(Long userId, SystemRole systemRole, ValidationErrors errors);

    List<User> getAllByRole(SystemRole systemRole);

    List<User> find(User user, int requiredResults);

    User get(String eppn, String targetedId, String persistentId);

    User login(Long id, String password, Locale locale);

    Long loginShibbolethUser(User user, Locale locale) throws AuthorizationException;

}