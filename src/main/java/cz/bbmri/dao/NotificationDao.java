package cz.bbmri.dao;

import cz.bbmri.entity.Notification;
import cz.bbmri.entity.NotificationType;
import cz.bbmri.entity.User;
import net.sourceforge.stripes.action.LocalizableMessage;

import java.util.List;
import java.util.Set;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface NotificationDAO extends AbstractDAO<Notification, Long> {

    /**
         * Send notification message to all listed users. Message will be localized by locale setting of each user.
         *
         * @param users              - list of recipients
         * @param notificationType   - Defines which event will be triggered from notification-detail on .jsp
         * @param localizableMessage - message key prepared for translation
         * @param objectId           - identifier of object which is a subject of notification
         * @return true/false for success of fail
         */
        boolean create(Set<User> users, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);

        /**
         * Send notification message to one single user. Message will be localized by locale setting of the user
         *
         * @param user               - recipient
         * @param notificationType   - Defines which event will be triggered from notification-detail on .jsp
         * @param localizableMessage - message key prepared for translation
         * @param objectId           - identifier of object which is a subject of notification
         * @return true/false for success of fail
         */
        boolean create(User user, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);

        /**
         * Remove notification from database. This is will typically happen after some expiration date e.g. remove all red
         * notifications older than 6 months.
         *
         * @param notification - object which will be deleted
         */
        void remove(Notification notification);

        /**
         * Return list of unread notification of user.
         *
         * @param user - recipient of notifications
         * @return list of unread notifications for user
         */
        List<Notification> getUnread(User user);

}
