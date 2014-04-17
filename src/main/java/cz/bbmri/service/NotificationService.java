package cz.bbmri.service;

import cz.bbmri.entities.Notification;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
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
