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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

        Set<ProjectAdministrator> paList = userDB.getProjectAdministrators();

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
            String msg = "Developer permission was given to user: " + userDB.getWholeName();

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

        boolean result = userService.setSystemRole(userId, SystemRole.ADMINISTRATOR);
        if (result) {
            String msg = "Administrator permission was given to user: " + userDB.getWholeName();

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

    public User get(String eppn, String targetedId, String persitentId) {
        notNull(eppn);
        return userService.get(eppn, targetedId, persitentId);
    }

    public Long loginShibbolethUser(User user) throws AuthorizationException {

        if (user == null) {
            logger.debug("Object can't be a null object -> User ");
            return null;
        }

        if (!user.isEmployee()) {
            throw new AuthorizationException("Only employees are authorized to access");
        }

        User userDB = userService.get(user.getEppn(), user.getTargetedId(), user.getPersistentId());

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

    public List<User> allOrderedBy(String orderByParam, boolean desc) {
        return userService.allOrderedBy(orderByParam, desc);
    }

    public String getJSON(Long userId) {
        JSONObject output = new JSONObject();
        JSONObject timeline = new JSONObject();
        try {
            timeline.put("headline", "Headline");
            timeline.put("type", "default");
            timeline.put("startDate", "2011");
            // timeline.put("text", "<p>Intro body text goes here, some HTML is ok</p>");

            JSONObject asset = new JSONObject();
            // asset.put("media", "http://twitter.com/ArjunaSoriano/status/164181156147900416");
            // asset.put("credit", "Credit Name Goes Here ORI");
            // asset.put("caption", "Caption text goes here ORI");
            timeline.put("asset", asset);

        } catch (JSONException ex) {
            logger.debug("ERA JSON exception");
            return null;
        }

        JSONArray dateArray = new JSONArray();
        try {

            JSONObject date = new JSONObject();
            date.put("startDate", "2011,12,10");
        //    date.put("endDate", "2011,12,30");
            date.put("headline", "Prvni udalost");
            date.put("text", "<p> Pri prvni udalosti doslo ... </p>");
            date.put("tag", "This is Optional tag");
            // date.put("classname", "optionaluniqueclassnamecanbeaddedhere");

            JSONObject asset = new JSONObject();
            //  asset.put("media", "http://twitter.com/ArjunaSoriano/status/164181156147900416");
            asset.put("thumbnail", "optional-32x32px.jpg");
            asset.put("credit", "Credit Name Goes Here");
            asset.put("caption", "Caption text goes here");
            date.put("asset", asset);
            dateArray.put(date);


            JSONObject date2 = new JSONObject();
            date2.put("startDate", "2011,12,24");
          //  date2.put("endDate", "2011,1,5");
            date2.put("headline", "Druha udalost");
            date2.put("text", "<p> Pri druhe udalosti doslo ... </p>");
            date2.put("tag", "This is Optional tag");
            // date.put("classname", "optionaluniqueclassnamecanbeaddedhere");

            JSONObject asset2 = new JSONObject();
            //  asset.put("media", "http://twitter.com/ArjunaSoriano/status/164181156147900416");
            asset2.put("thumbnail", "optional-32x32px.jpg");
            asset2.put("credit", "Credit Name Goes Here");
            asset2.put("caption", "Caption text goes here");
            date2.put("asset", asset2);
            dateArray.put(date2);

            JSONObject date3 = new JSONObject();
            date3.put("startDate", "2012,3,9");
         //   date3.put("endDate", "2012,10,8");
            date3.put("headline", "Treti udalost");
            date3.put("text", "<p> Pri druhe udalosti doslo ... </p>");
            date3.put("tag", "This is Optional tag");
            // date.put("classname", "optionaluniqueclassnamecanbeaddedhere");

            JSONObject asset3 = new JSONObject();
            //  asset.put("media", "http://twitter.com/ArjunaSoriano/status/164181156147900416");
            asset3.put("thumbnail", "optional-32x32px.jpg");
            asset3.put("credit", "Credit Name Goes Here");
            asset3.put("caption", "Caption text goes here");
            date3.put("asset", asset3);
            dateArray.put(date3);

            timeline.put("date", dateArray);

            output.put("timeline", timeline);

        } catch (JSONException ex) {
            logger.debug("DATE JSON exception");
            return null;
        }

        return output.toString();
    }

}
