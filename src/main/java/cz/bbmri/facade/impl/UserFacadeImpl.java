package cz.bbmri.facade.impl;

import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.Notification;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.webEntities.RoleDTO;
import cz.bbmri.facade.UserFacade;
import cz.bbmri.facade.exceptions.AuthorizationException;
import cz.bbmri.service.BiobankAdministratorService;
import cz.bbmri.service.NotificationService;
import cz.bbmri.service.UserService;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.10.13
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */

@Controller("userFacade")
public class UserFacadeImpl extends BasicFacade implements UserFacade {

    private static final int MAXIMUM_FIND_RESULTS = 5;

    @Autowired
    private UserService userService;

    @Autowired
    private BiobankAdministratorService biobankAdministratorService;

    @Autowired
    private NotificationService notificationService;

    public List<RoleDTO> getRoles(Long userId) {
        notNull(userId);
        List<RoleDTO> results = new ArrayList<RoleDTO>();

        User userDB = userService.eagerGet(userId, false, true, true, false);

        if (userDB == null) {
            return null;
        }

        /* Add all biobanks of user */
        Set<BiobankAdministrator> baList = userDB.getBiobankAdministrators();
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

    public boolean update(User user) {
        notNull(user);
        return userService.update(user) != null;
    }

    public List<User> all() {
        return userService.all();
    }

    public boolean create(User user) {
        notNull(user);
        user.setShibbolethUser(false);
        user = userService.create(user);

        if (user == null) {
            return false;
        }

        userService.setSystemRole(user.getId(), SystemRole.USER);
        return true;
    }

    public boolean remove(Long userId) {
        notNull(userId);
        return userService.remove(userId);
    }

    public User get(Long userId) {
        notNull(userId);
        return userService.get(userId);
    }

    public boolean setAsDeveloper(Long userId, ValidationErrors errors) {
        notNull(userId);

        User userDB = userService.get(userId);
        if (userDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return false;
        }

        boolean result = userService.setSystemRole(userId, SystemRole.DEVELOPER);
        if (result) {
            String msg = "Developer permission was given to user: " + userDB.getWholeName() + ".";

            notificationService.create(getDevelopers(),
                    NotificationType.USER_SUPPORT, msg, null);
        }
        return result;
    }

    public boolean setAsAdministrator(Long userId, ValidationErrors errors) {
        notNull(userId);

        User userDB = userService.get(userId);
        if (userDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return false;
        }

        boolean result = userService.setSystemRole(userId, SystemRole.DEVELOPER);
        if (result) {
            String msg = "Administrator permission was given to user: " + userDB.getWholeName() + ".";

            notificationService.create(getAdministrators(),
                    NotificationType.USER_SUPPORT, msg, null);
        }
        return result;
    }

//    public void removeSystemRole(Long userId, SystemRole systemRole) {
//        notNull(userId);
//        notNull(systemRole);
//        userService.removeSystemRole(userId, systemRole);
//    }

    public boolean removeAdministratorRole(Long userId, ValidationErrors errors) {
        notNull(userId);
        User userDB = userService.get(userId);
        if (userDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return false;
        }
        if (getAdministrators().size() == 1) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.UserFacadeImpl.lastAdministratorRemove"));
            return false;
        }

        boolean result = userService.removeSystemRole(userId, SystemRole.ADMINISTRATOR);

        if (result) {

            String msg = "Administrator permission was taken from user: " + userDB.getWholeName() + ".";

            notificationService.create(getAdministrators(),
                    NotificationType.USER_SUPPORT, msg, null);
        }

        return result;
    }

    public boolean removeDeveloperRole(Long userId, ValidationErrors errors) {
        notNull(userId);
        User userDB = userService.get(userId);
        if (userDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.databaseRecordNotFound"));
            return false;
        }
        if (getDevelopers().size() == 1) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.UserFacadeImpl.lastDeveloperRemove"));
            return false;
        }
        boolean result = userService.removeSystemRole(userId, SystemRole.DEVELOPER);

        if (result) {

            String msg = "Developer permission was taken from user: " + userDB.getWholeName() + ".";

            notificationService.create(getDevelopers(),
                    NotificationType.USER_SUPPORT, msg, null);

        }

        return result;
    }


    public List<User> getAdministrators() {
        return userService.getAllByRole(SystemRole.ADMINISTRATOR);
    }

    public List<User> getDevelopers() {
        return userService.getAllByRole(SystemRole.DEVELOPER);
    }

    public Set<SystemRole> getSystemRoles(Long userId) {
        notNull(userId);
        User userDB = userService.get(userId);
        if (userDB == null) {
            return null;
            // TODO: exception
        }
        return userDB.getSystemRoles();
    }

    public User login(Long id, String password) {
        notNull(id);
        notNull(password);

        User userDB = userService.get(id);
        if (userDB == null) {
            return null;
        }
        if (!userDB.getPassword().equals(password)) {
            return null;
        }

        userDB.setLastLogin(new Date());
        userService.update(userDB);
        return userDB;
    }

    public List<User> find(User user, int requiredResults) {
        if (user == null) {
            return null;
        }
        if (requiredResults < 1) {
            requiredResults = MAXIMUM_FIND_RESULTS;
        }

        return userService.find(user, requiredResults);

    }

    public User get(String eppn) {
        notNull(eppn);
        return userService.get(eppn);
    }

    public Long loginShibbolethUser(User user) throws AuthorizationException {

        if (user == null) {
            throw new IllegalArgumentException("Object can't be a null object> User: " + user);
        }

        if (!user.isEmployee()) {
            throw new AuthorizationException("Only employees are authorized to access");
        }

        User userDB = userService.get(user.getEppn());

        if (userDB == null) {
            userDB = userService.create(user);
            user.setId(userDB.getId());
        } else {
            /* If user changed its credentials in system of IdentityProvider then we want to
            * make local user stored in database up-to-date. */
            user.setId(userDB.getId());
        }

        user.setLastLogin(new Date());
        userService.update(userDB);
        return userDB.getId();
    }

    public List<Notification> getUnreadNotifications(Long loggedUserId) {
        notNull(loggedUserId);
        return notificationService.getUnread(loggedUserId);
    }

    public boolean markAsRead(List<Long> notificationsId) {
        notNull(notificationsId);
        if (notificationsId.isEmpty()) {
            return false;
        }
        for (Long id : notificationsId) {
            notificationService.markAsRead(id);
        }

        return true;
    }

    public boolean deleteNotifications(List<Long> notificationsId) {
        notNull(notificationsId);
        if (notificationsId.isEmpty()) {
            return false;
        }
        for (Long id : notificationsId) {
            notificationService.remove(id);
        }

        return true;
    }

    public List<User> allOrderedBy(String orderByParam, boolean desc){
        return userService.allOrderedBy(orderByParam, desc);
    }
}
