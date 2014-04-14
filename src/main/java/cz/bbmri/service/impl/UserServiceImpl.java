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
import cz.bbmri.facade.exceptions.AuthorizationException;
import cz.bbmri.service.BiobankAdministratorService;
import cz.bbmri.service.NotificationService;
import cz.bbmri.service.UserService;
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
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("userService")
public class UserServiceImpl extends BasicServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

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


    public boolean create(User user, Locale locale) {
        notNull(user);

        user.setCreated(new Date());
        user.setShibbolethUser(false);
        userDao.create(user);
        setSystemRole(user.getId(), SystemRole.USER);

        UserSetting userSetting = new UserSetting();
        if (locale != null) {
            // Set locale during first sign in
            userSetting.setLocale(locale.getLanguage());
        }

        userSetting.setUser(user);
        userSettingDao.create(userSetting);
        return true;
    }

    public boolean remove(Long id) {
        User userDB = userDao.get(id);
        if (userDB == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }

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

    public boolean update(User user) {
        notNull(user);

        User userDB = userDao.get(user.getId());
        if (userDB == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }

        // TODO: pridat i Shibboleti pole

        if (user.getName() != null) userDB.setName(user.getName());
        if (user.getSurname() != null) userDB.setSurname(user.getSurname());
        if (user.getPassword() != null) userDB.setPassword(user.getPassword());
        if (user.getLastLogin() != null) userDB.setLastLogin(user.getLastLogin());
        if (user.getEmail() != null) userDB.setEmail(user.getEmail());

        userDao.update(userDB);
        return true;
    }

    @Transactional(readOnly = true)
    public List<User> all() {
        return userDao.all();
    }

    @Transactional(readOnly = true)
    public User get(Long id) {
        return userDao.get(id);
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return userDao.count();
    }

    public boolean setSystemRole(Long userId, SystemRole systemRole) {
        notNull(userId);
        notNull(systemRole);

        User userDB = userDao.get(userId);

        if (userDB == null) {
            return false;
        }

        if (userDB.getSystemRoles().contains(systemRole)) {
            return false;
        }
        userDB.getSystemRoles().add(systemRole);
        userDao.update(userDB);
        return true;
    }

    public boolean removeSystemRole(Long userId, SystemRole systemRole) {
        notNull(userId);
        notNull(systemRole);

        User userDB = userDao.get(userId);

        if (userDB == null) {
            return false;
            // TODO: exception
        }

        if (!userDB.getSystemRoles().contains(systemRole)) {
            return false;
            // TODO: exception
        }

        userDB.getSystemRoles().remove(systemRole);
        userDao.update(userDB);
        return true;
    }

    @Transactional(readOnly = true)
    public List<User> getAllByRole(SystemRole systemRole) {
        notNull(systemRole);
        return userDao.getAllWithSystemRole(systemRole);
    }

    @Transactional(readOnly = true)
    public List<User> find(User user, int requiredResults) {
        notNull(user);

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


    ////

    public boolean setAsDeveloper(Long userId, ValidationErrors errors) {
        notNull(userId);

        User userDB = get(userId);
        if (userDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return false;
        }

        boolean result = setSystemRole(userId, SystemRole.DEVELOPER);
        if (result) {

            LocalizableMessage localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.UserFacadeImpl.developerRoleAdded", userDB.getWholeName());

            notificationDao.create(getAllByRole(SystemRole.DEVELOPER),
                    NotificationType.USER_SUPPORT, localizableMessage, null);
        }
        return result;
    }

    public boolean setAsAdministrator(Long userId, ValidationErrors errors) {
        notNull(userId);

        User userDB = get(userId);
        if (userDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return false;
        }

        boolean result = setSystemRole(userId, SystemRole.ADMINISTRATOR);
        if (result) {

            LocalizableMessage localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.UserFacadeImpl.administratorRoleAdded", userDB.getWholeName());

            notificationDao.create(getAllByRole(SystemRole.ADMINISTRATOR),
                    NotificationType.USER_SUPPORT, localizableMessage, null);
        }
        return result;
    }

    public boolean removeAdministratorRole(Long userId, ValidationErrors errors) {
        notNull(userId);
        User userDB = get(userId);
        if (userDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return false;
        }
        if (getAllByRole(SystemRole.ADMINISTRATOR).size() == 1) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.UserFacadeImpl.lastAdministratorRemove"));
            return false;
        }

        boolean result = removeSystemRole(userId, SystemRole.ADMINISTRATOR);

        if (result) {


            LocalizableMessage localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.UserFacadeImpl.administratorRoleRemoved", userDB.getWholeName());

            notificationDao.create(getAllByRole(SystemRole.ADMINISTRATOR),
                    NotificationType.USER_SUPPORT, localizableMessage, null);
        }

        return result;
    }

    public boolean removeDeveloperRole(Long userId, ValidationErrors errors) {
        notNull(userId);
        User userDB = get(userId);
        if (userDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return false;
        }
        if (getAllByRole(SystemRole.DEVELOPER).size() == 1) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.UserFacadeImpl.lastDeveloperRemove"));
            return false;
        }
        boolean result = removeSystemRole(userId, SystemRole.DEVELOPER);

        if (result) {

            LocalizableMessage localizableMessage = new LocalizableMessage("cz.bbmri.facade.impl.UserFacadeImpl.developerRoleRemoved", userDB.getWholeName());

            notificationDao.create(getAllByRole(SystemRole.DEVELOPER),
                    NotificationType.USER_SUPPORT, localizableMessage, null);

        }

        return result;
    }

    public User login(Long id, String password) {
        notNull(id);
        notNull(password);

        User userDB = get(id);
        if (userDB == null) {
            return null;
        }
        if (!userDB.getPassword().equals(password)) {
            return null;
        }

        if (userDB.getUserSetting() == null) {
            UserSetting setting = new UserSetting();
            setting.setUser(userDB);

        }

        userDB.setLastLogin(new Date());
        update(userDB);
        return userDB;
    }

    public Long loginShibbolethUser(User user, Locale locale) throws AuthorizationException {

        if (user == null) {
            logger.debug("Object can't be a null object -> User ");
            return null;
        }

        if (!user.isEmployee()) {
            throw new AuthorizationException("Only employees are authorized to access");
        }

        User userDB = get(user.getEppn(), user.getTargetedId(), user.getPersistentId());

        if (userDB == null) {
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


}
