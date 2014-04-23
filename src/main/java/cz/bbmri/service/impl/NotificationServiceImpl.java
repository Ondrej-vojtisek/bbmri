package cz.bbmri.service.impl;

import cz.bbmri.dao.NotificationDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;
import cz.bbmri.service.NotificationService;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Transactional
@Service("notificationService")
public class NotificationServiceImpl extends BasicServiceImpl implements NotificationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotificationDao notificationDao;

    public boolean markAsRead(Long notificationId) {

        if(isNull(notificationId, "notificationId", null)) return false;

        Notification notificationDB = notificationDao.get(notificationId);

        if(isNull(notificationDB, "notificationDB", null)) return false;

        notificationDB.setRead(true);
        notificationDao.update(notificationDB);

        return true;
    }

    @Transactional(readOnly = true)
    public List<Notification> getUnread(Long userId) {
        if(isNull(userId, "userId", null)) return null;

        User userDB = userDao.get(userId);
        if(isNull(userDB, "userDB", null)) return null;

        return notificationDao.getUnread(userDB);
    }

    @Transactional(readOnly = true)
    public Notification get(Long id) {
        return notificationDao.get(id);
    }

    public boolean remove(Long id) {
        if(isNull(id, "id", null)) return false;

        Notification notificationDB = notificationDao.get(id);
        if(isNull(notificationDB, "notificationDB", null)) return false;

        User user = notificationDB.getUser();
        if(isNull(user, "user", null)) return false;

        user.getNotifications().remove(notificationDB);
        notificationDao.remove(notificationDB);
        return true;
    }

    public boolean markAsRead(List<Long> notificationsId, ValidationErrors errors) {
        notNull(errors);

        if(isNull(notificationsId, "notificationsId", null)) return false;

        if (notificationsId.isEmpty()) {
            noEffect(errors);
            return false;
        }

        for (Long id : notificationsId) {
            markAsRead(id);
        }

        return true;
    }

    public boolean deleteNotifications(List<Long> notificationsId) {
        if(isNull(notificationsId, "notificationsId", null)) return false;

        if (notificationsId.isEmpty()) {
            return false;
        }
        for (Long id : notificationsId) {
            remove(id);
        }

        return true;
    }

}
