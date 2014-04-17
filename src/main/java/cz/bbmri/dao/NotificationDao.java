package cz.bbmri.dao;

import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;
import net.sourceforge.stripes.action.LocalizableMessage;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface NotificationDao {

    void update(Notification notification);

    Notification get(Long id);

    boolean create(List<User> users, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);

    boolean create(User user, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId);

    void remove(Notification notification);

    List<Notification> getUnread(User user);
}
