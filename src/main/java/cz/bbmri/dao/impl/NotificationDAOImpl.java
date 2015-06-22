package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.NotificationDAO;
import cz.bbmri.entity.*;
import net.sourceforge.stripes.action.LocalizableMessage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("notificationDAO")
@Transactional
public class NotificationDAOImpl extends GenericDAOImpl<Notification> implements NotificationDAO {

    public Notification get(Long id) {
        return (Notification) getCurrentSession().get(Notification.class, id);
    }


    public List<Notification> getUnread(User user) {

        Criterion criterion = Restrictions.eq(Notification.PROP_READ, false);
        Criterion criterionUser = Restrictions.eq(Notification.PROP_USER, user);

        // Retrieve a list of existing users matching the criterion above the list retrieval
        List<Notification> list = getCurrentSession().createCriteria(Notification.class)
                .add(criterion).add(criterionUser).list();


        return list;

    }

    public boolean create(Set<User> users, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId) {
        notNull(users);
        notNull(notificationType);

        boolean result = true;

        // TEMPORARY for testing
        // Send each notification also to developer
        Set<User> recipients = new HashSet<User>(users);
        // Get current instance of Developer from database
        Role developer = (Role) getCurrentSession().get(Role.class, Role.DEVELOPER.getId());
        for (User user : developer.getUser()) {
            recipients.add(user);
        }

        for (User user : recipients) {

            if (!create(user, notificationType, localizableMessage, objectId)) {
                result = false;
            }
        }
        return result;
    }


    public boolean create(User user, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId) {
        notNull(user);

        // Fix for situation when the user is created by SQL without settings
        // In situation when user is approaching from eduId, this won't occur
        if (user.getSettings() == null) {
            Settings setting = new Settings();
            setting.setUser(user);
            getCurrentSession().saveOrUpdate(setting);
        }
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

        if (user.getSettings().getLocale() == null) {
            logger.debug("UserSettings Locale is null");
            return false;
        }

        locale = user.getSettings().getLocaleSettings();
        notification.setMessage(localizableMessage.getMessage(locale));
        save(notification);
        return true;
    }


}
