package cz.bbmri.facade;

import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.webEntities.RoleDTO;
import cz.bbmri.facade.exceptions.AuthorizationException;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public interface UserFacade {

    List<RoleDTO> getRoles(Long userId);

    boolean update(User user);

    boolean create(User user);

    boolean remove(Long userId);

    User get(Long userId);

    List<User> all();

    boolean setAsDeveloper(Long userId, ValidationErrors errors);

    boolean setAsAdministrator(Long userId, ValidationErrors errors);

    boolean removeAdministratorRole(Long userId, ValidationErrors errors);

    boolean removeDeveloperRole(Long userId, ValidationErrors errors);

    List<User> getAdministrators();

    List<User> getDevelopers();

    Set<SystemRole> getSystemRoles(Long userId);

    User login(Long id, String password);

    List<User> find(User user, int requiredResults);

    User get(String eppn);

    Long loginShibbolethUser(User user) throws AuthorizationException;

    List<Notification> getUnreadNotifications(Long loggedUserId);

    boolean markAsRead(List<Long> notificationsId);

    boolean deleteNotifications(List<Long> notificationsId);

    List<User> allOrderedBy(String orderByParam, boolean desc);

    /*
    with permission check
    void setNewSystemAdministrator(Long userId);

    with permission check
    void removeSystemAdministrator(Long userId);

    List<User> findUser(String firstname, String surname, String email);
    */
}
