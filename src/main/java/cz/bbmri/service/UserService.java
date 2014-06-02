package cz.bbmri.service;

import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.exceptions.AuthorizationException;
import cz.bbmri.service.simpleService.*;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;
import java.util.Locale;

/**
 * API for handling Users
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface UserService extends All<User>, AllOrderedBy<User>, Find<User>,
        Get<User>, Remove, Update<User>, Count {

    /**
     * Update stored data of user instance which is using Shibboleth to authenticate
     *
     * @param user - instace of user
     * @return true/false
     */
    boolean updateShibbolethUser(User user);

    /**
     * Store instance of user in DB. Set given locale as his default
     *
     * @param user   - new instance of user
     * @param locale - locale retrieved from user browser setting
     * @return true/false
     */
    boolean create(User user, Locale locale);

    /**
     * Set system role (developer, administrator) to given user
     *
     * @param userId       - ID of user
     * @param systemRole   - which system role will be set
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - id of user who initiated event
     * @return true/false
     */
    boolean setSystemRole(Long userId, SystemRole systemRole, ValidationErrors errors, Long loggedUserId);

    /**
     * Remove system role (developer, administrator) from user.
     *
     * @param userId       - ID of user
     * @param systemRole   - role which will be removed
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - id of user who initiated even
     * @return true/false
     */
    boolean removeSystemRole(Long userId, SystemRole systemRole, ValidationErrors errors, Long loggedUserId);

    /**
     * Return all users with given role
     *
     * @param systemRole
     * @return list of users
     */
    List<User> getAllByRole(SystemRole systemRole);

    /**
     * Find user by identifiers used in shibboleth
     *
     * @param eppn
     * @param targetedId
     * @param persistentId
     * @return user identified by given identifiers or null
     */
    User get(String eppn, String targetedId, String persistentId);

    /**
     * Sign-in for local users. Only for localhost testing. If locale doesn't match the one stored in user setting,
     * than change it.
     *
     * @param id       - ID of user
     * @param password - password of user (hashed)
     * @param locale   - locale setting of user browser
     * @return instance of user if login was correct, null otherwise
     */
    User login(Long id, String password, Locale locale);

    /**
     * Login for users accessing server (not localhost) using shibboleth. Authorization is checked, information about user
     * are updated.
     *
     * @param user   - user accessing web
     * @param locale - user browser setting
     * @return identifier of user
     * @throws AuthorizationException
     */
    Long loginShibbolethUser(User user, Locale locale) throws AuthorizationException;

}