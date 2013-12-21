package cz.bbmri.service;

import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.12.13
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
public interface NotificationService extends BasicService<Notification>{

    boolean create(List<User> users, NotificationType notificationType, String message, Long objectId);

    List<Notification> getUnread(Long userId);

    boolean markAsRead(Long notificationId);

}
