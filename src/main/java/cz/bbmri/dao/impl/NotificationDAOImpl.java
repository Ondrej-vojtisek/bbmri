package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.NotificationDAO;
import cz.bbmri.entity.Notification;
import cz.bbmri.entity.NotificationType;
import cz.bbmri.entity.User;
import net.sourceforge.stripes.action.LocalizableMessage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        // Retrieve a list of existing users matching the criterion above the list retrieval
        List<Notification> list = getCurrentSession().createCriteria(Notification.class)
                .add(criterion).list();


        return list;

    }

    public boolean create(List<User> users, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId){
          notNull(users);
          notNull(notificationType);

          boolean result = true;
          for (User user : users) {

              if (!create(user, notificationType, localizableMessage, objectId)) {
                  result = false;
              }
          }
          return result;
      }


      public boolean create(User user, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId) {
          notNull(user);
          notNull(user.getSettings());
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