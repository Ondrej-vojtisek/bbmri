package cz.bbmri.service;

import cz.bbmri.entities.Notification;

import java.util.List;

/**
 * API for handling Notifications
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface NotificationService /*extends BasicService<Notification> */ {

 //   boolean create(Long userId, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);

 //   boolean create(List<User> users, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);
    //TODO
    List<Notification> getUnread(Long userId);
    //TODO
    boolean markAsRead(Long notificationId);
     //TODO
    List<Notification> getUnreadNotifications(Long loggedUserId);
    //TODO
    boolean markAsRead(List<Long> notificationsId);
    //TODO
    boolean deleteNotifications(List<Long> notificationsId);
     //TODO
    Notification get(Long id);
    //TODO
    boolean remove(Long id);
     //TODO
    Notification update(Notification notification);

}
