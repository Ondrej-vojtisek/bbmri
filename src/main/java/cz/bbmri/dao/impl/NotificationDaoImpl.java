package cz.bbmri.dao.impl;

import cz.bbmri.dao.NotificationDao;
import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;
import net.sourceforge.stripes.action.LocalizableMessage;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Implementation for interface handling instances of Notification. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository
public class NotificationDaoImpl extends BaseForDao implements NotificationDao {

    public boolean create(List<User> users, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId){
        notNull(users);
        notNull(notificationType);

        boolean result = true;
        for (User user : users) {

            // TODO: mail notification here
            if (!create(user, notificationType, localizableMessage, objectId)) {
                result = false;
            }
        }
        return result;
    }


    public boolean create(User user, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId) {
        notNull(user);
        notNull(user.getUserSetting());
        notNull(notificationType);
        notNull(localizableMessage);
        notNull(objectId);

        Notification notification = new Notification();
        notification.setCreated(new Date());
        notification.setNotificationType(notificationType);
        notification.setRead(false);
        notification.setUser(user);
        notification.setObjectId(objectId);

        Locale locale;

        if (user.getUserSetting().getLocale() == null) {
            logger.debug("UserSettings Locale is null");
            return false;
        }

        locale = user.getUserSetting().getLocale();
        notification.setMessage(localizableMessage.getMessage(locale));
        em.persist(notification);
        return true;
    }

    public Notification get(Long id) {
           notNull(id);
           return em.find(Notification.class, id);
       }


    public void update(Notification notification) {
        notNull(notification);
        em.merge(notification);
    }

    public void remove(Notification notification) {
        notNull(notification);
        em.remove(notification);
    }

    public List<Notification> getUnread(User user) {
        TypedQuery<Notification> typedQuery = em.createQuery("SELECT p FROM Notification p " +
                "where p.user = :userParam and " +
                "p.read = false " +
                "ORDER BY p.created DESC", Notification.class);
        typedQuery.setParameter("userParam", user);

        return typedQuery.getResultList();
    }
}
