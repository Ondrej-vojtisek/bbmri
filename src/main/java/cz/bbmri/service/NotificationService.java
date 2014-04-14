package cz.bbmri.service;

import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;
import net.sourceforge.stripes.action.LocalizableMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.12.13
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
public interface NotificationService /*extends BasicService<Notification> */ {

 //   boolean create(Long userId, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);

 //   boolean create(List<User> users, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);

    List<Notification> getUnread(Long userId);

    boolean markAsRead(Long notificationId);

    List<Notification> getUnreadNotifications(Long loggedUserId);

    boolean markAsRead(List<Long> notificationsId);

    boolean deleteNotifications(List<Long> notificationsId);

    Notification get(Long id);

    boolean remove(Long id);

    Notification update(Notification notification);

}
