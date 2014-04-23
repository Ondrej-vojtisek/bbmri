package cz.bbmri.service;

import cz.bbmri.entities.Notification;
import cz.bbmri.service.simpleService.Get;
import cz.bbmri.service.simpleService.Remove;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * API for handling Notifications
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface NotificationService extends Get<Notification>, Remove  /*extends BasicService<Notification> */ {

    /**
     * Return list of all unread notification for given recipient (user)
     *
     * @param userId - ID of user
     * @return list of unread notifications
     */
    List<Notification> getUnread(Long userId);

    /**
     * Mark given notification(identified by its ID) as read
     *
     * @param notificationId - ID of notification
     * @return true/false
     */
    boolean markAsRead(Long notificationId);

    /**
     * Mark all given notifications as read
     *
     * @param notificationsId - list of notification identifiers
     * @param errors         - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean markAsRead(List<Long> notificationsId, ValidationErrors errors);

    /**
     * Delete all given notifications
     *
     * @param notificationsId - list of notification identifiers
     * @return true/false
     */
    boolean deleteNotifications(List<Long> notificationsId);

}
