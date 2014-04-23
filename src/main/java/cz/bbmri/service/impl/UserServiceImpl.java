package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.Notification;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.systemAdministration.UserSetting;
import cz.bbmri.service.UserService;
import cz.bbmri.service.exceptions.AuthorizationException;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Transactional
@Service("userService")
public class UserServiceImpl extends BasicServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserDao userDao;

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;

    @Autowired
    private ProjectAdministratorDao projectAdministratorDao;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserSettingDao userSettingDao;

    // Create test user from front-end
    public boolean create(User user, Locale locale) {
        if (isNull(user, "user", null)) return false;

        user.setCreated(new Date());
        user.setShibbolethUser(false);
        user.getSystemRoles().add(SystemRole.USER);
        userDao.create(user);

        initiateUserSetting(user, locale);
        return true;
    }

    public boolean remove(Long id) {
        if (isNull(id, "id", null)) return false;
        User userDB = userDao.get(id);
        if (isNull(userDB, "userDB", null)) return false;

        Set<BiobankAdministrator> biobankAdministrators = userDB.getBiobankAdministrators();
        if (biobankAdministrators != null) {
            for (BiobankAdministrator ba : biobankAdministrators) {
                ba.setUser(null);
                ba.setBiobank(null);
                biobankAdministratorDao.remove(ba);
            }
        }

        Set<ProjectAdministrator> projectAdministrators = userDB.getProjectAdministrators();
        if (projectAdministrators != null) {
            for (ProjectAdministrator pa : projectAdministrators) {
                pa.setUser(null);
                pa.setProject(null);
                projectAdministratorDao.remove(pa);
            }
        }

        List<Notification> notifications = userDB.getNotifications();
        if (notifications != null) {
            for (Notification notification : notifications) {
                notification.setUser(null);
                notificationDao.remove(notification);
            }
        }

        if (userDB.getUserSetting() != null) {
            userSettingDao.remove(userDB.getUserSetting());
        }

        userDao.remove(userDB);
        return true;
    }

    public boolean updateShibbolethUser(User user) {
        if (isNull(user, "user", null)) return false;

        User userDB = userDao.get(user.getId());
        if (isNull(userDB, "userDB", null)) return false;

        // eppn, targetedId, persistentId are considered as identifiers -> impossible to change them

        if (user.getName() != null) userDB.setName(user.getName());
        if (user.getSurname() != null) userDB.setSurname(user.getSurname());
        if (user.getOrganization() != null) userDB.setOrganization(user.getOrganization());
        if (user.getDisplayName() != null) userDB.setDisplayName(user.getDisplayName());
        if (user.getAffiliation() != null) userDB.setAffiliation(user.getAffiliation());
        if (user.getLastLogin() != null) userDB.setLastLogin(user.getLastLogin());

        // Set mail only if user has no email set
        // Don't replace already inserted email -> this allows user to change contact info using web interface
        if (user.getEmail() != null){
            // mail is not set
            if(userDB.getEmail() == null){
                userDB.setEmail(user.getEmail());
            }else{
               // mail is empty
               if(userDB.getEmail().isEmpty()){
                   userDB.setEmail(user.getEmail());
               }
            }
        }

        userDao.update(userDB);
        return true;
    }

    // Only fields changeable from web interface
    public User update(User user) {
        if (isNull(user, "user", null)) return null;

        User userDB = userDao.get(user.getId());
        if (isNull(userDB, "userDB", null)) return null;

        if (user.getName() != null) userDB.setName(user.getName());
        if (user.getSurname() != null) userDB.setSurname(user.getSurname());
        if (user.getPassword() != null) userDB.setPassword(user.getPassword());
        if (user.getLastLogin() != null) userDB.setLastLogin(user.getLastLogin());
        if (user.getEmail() != null) userDB.setEmail(user.getEmail());

        userDao.update(userDB);
        return userDB;
    }

    @Transactional(readOnly = true)
    public List<User> all() {
        return userDao.all();
    }

    @Transactional(readOnly = true)
    public User get(Long id) {
        if (isNull(id, "id", null)) return null;
        return userDao.get(id);
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return userDao.count();
    }

    public boolean setSystemRole(Long userId, SystemRole systemRole, ValidationErrors errors) {
        notNull(errors);

        if (isNull(userId, "userId", errors)) return false;
        if (isNull(systemRole, "systemRole", errors)) return false;

        User userDB = get(userId);
        if (isNull(userDB, "userDB", errors)) return false;

        // not ADMINISTRATOR and neither DEVELOPER
        if (!systemRole.equals(SystemRole.ADMINISTRATOR) && !systemRole.equals(SystemRole.DEVELOPER)) {
            logger.debug("Other system role can't be handled this way");
            noEffect(errors);
            return false;
        }

        if (userDB.getSystemRoles().contains(systemRole)) {
            logger.debug("User already has this role");
            return false;
        }

        userDB.getSystemRoles().add(systemRole);
        userDao.update(userDB);

        LocalizableMessage localizableMessage = null;
        if (systemRole.equals(SystemRole.DEVELOPER)) {
            localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.UserFacadeImpl.developerRoleAdded", userDB.getWholeName());
        }

        if (systemRole.equals(SystemRole.ADMINISTRATOR)) {
            localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.UserFacadeImpl.administratorRoleAdded", userDB.getWholeName());
        }
        if (localizableMessage != null) {
            notificationDao.create(getAllByRole(SystemRole.DEVELOPER),
                    NotificationType.USER_SUPPORT, localizableMessage, null);
        }
        return true;
    }

    public boolean removeSystemRole(Long userId, SystemRole systemRole, ValidationErrors errors) {
        notNull(errors);

        if (isNull(userId, "userId", null)) return false;
        if (isNull(systemRole, "systemRole", null)) return false;

        // not ADMINISTRATOR and neither DEVELOPER
        if (!systemRole.equals(SystemRole.ADMINISTRATOR) && !systemRole.equals(SystemRole.DEVELOPER)) {
            logger.debug("Other system role can't be handled this way");
            noEffect(errors);
            return false;
        }

        User userDB = userDao.get(userId);
        if (isNull(userDB, "userDB", null)) return false;

        if (!userDB.getSystemRoles().contains(systemRole)) {
            logger.debug("User does not contain the systemRole");
            return false;
        }

        // cant remove last developer or administrator
        if (getAllByRole(systemRole).size() == 1) {
            if (systemRole.equals(SystemRole.DEVELOPER)) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.UserFacadeImpl.lastDeveloperRemove"));
            }
            if (systemRole.equals(SystemRole.ADMINISTRATOR)) {
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.UserFacadeImpl.lastAdministratorRemove"));
            }
            return false;
        }

        userDB.getSystemRoles().remove(systemRole);
        userDao.update(userDB);
        LocalizableMessage localizableMessage = null;

        // inform other administrators and developers
        if (systemRole.equals(SystemRole.DEVELOPER)) {
            localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.UserFacadeImpl.developerRoleRemoved", userDB.getWholeName());
        }
        if (systemRole.equals(SystemRole.ADMINISTRATOR)) {
            localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.UserFacadeImpl.administratorRoleRemoved", userDB.getWholeName());
        }
        if (localizableMessage != null) {
            notificationDao.create(getAllByRole(systemRole),
                    NotificationType.USER_SUPPORT, localizableMessage, null);
        }

        return true;
    }

    @Transactional(readOnly = true)
    public List<User> getAllByRole(SystemRole systemRole) {
        if (isNull(systemRole, "systemRole", null)) return null;
        return userDao.getAllWithSystemRole(systemRole);
    }

    @Transactional(readOnly = true)
    public List<User> find(User user, int requiredResults) {
        if (isNull(user, "user", null)) return null;

        if (requiredResults < 1) {
            requiredResults = Constant.MAXIMUM_FIND_RESULTS;
        }

        List<User> users = userDao.findUser(user);
        if (users == null) {
            return null;
        }
        if (requiredResults > users.size()) {
            return users;
        }
        return userDao.findUser(user).subList(0, requiredResults);
    }

    @Transactional(readOnly = true)
    public User get(String eppn, String targetedId, String persistentId) {
        return userDao.get(eppn, targetedId, persistentId);
    }

    @Transactional(readOnly = true)
    public List<User> allOrderedBy(String orderByParam, boolean desc) {
        return userDao.allOrderedBy(orderByParam, desc);
    }

    public User login(Long id, String password, Locale locale) {
        if (isNull(id, "id", null)) return null;

        User userDB = get(id);
        if (isNull(userDB, "userDB", null)) return null;
        if (!userDB.getPassword().equals(password)) {
            return null;
        }

        // hack to initiace userSetting for all test users during login
        initiateUserSetting(userDB, locale);

        userDB.setLastLogin(new Date());
        update(userDB);
        return userDB;
    }

    public Long loginShibbolethUser(User user, Locale locale) throws AuthorizationException {
        if (isNull(user, "user", null)) return null;

        if (!user.isEmployee()) {
            throw new AuthorizationException("Only employees are authorized to access");
        }

        // Shibboleth identifiers of user
        User userDB = get(user.getEppn(), user.getTargetedId(), user.getPersistentId());

        if (userDB == null) {
            // new user to system
            create(user, locale);
        } else {
                /* If user changed its credentials in system of IdentityProvider then we want to
                * make local user stored in database up-to-date. */
            user.setId(userDB.getId());
        }

        user.setLastLogin(new Date());
        update(user);
        return user.getId();
    }

    /**
     * Initiate user setting for those who doesn't have user setting initiated.
     *
     * @param user - instance of user without setting initiated
     * @param locale - current browser language setting - considered as default
     */
    private void initiateUserSetting(User user, Locale locale) {
        if (isNull(user, "user", null)) return;
        // Setting created
        if (user.getUserSetting() == null) {
            UserSetting setting = new UserSetting();
            setting.setUser(user);
            if (locale != null) {
                setting.setLocale(locale.getLanguage());
            }
            userSettingDao.create(setting);
        }
    }


}
