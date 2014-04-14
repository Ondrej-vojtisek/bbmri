package cz.bbmri.dao;

import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;
import net.sourceforge.stripes.action.LocalizableMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.12.13
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */
public interface NotificationDao {

    void update(Notification notification);

    Notification get(Long id);

    boolean create(List<User> users, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);

    boolean create(User user, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);

    void remove(Notification notification);

    List<Notification> getUnread(User user);
}
