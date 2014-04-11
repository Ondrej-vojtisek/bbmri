package cz.bbmri.service.impl;

import cz.bbmri.dao.NotificationDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.dao.UserSettingDao;
import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.systemAdministration.UserSetting;
import cz.bbmri.service.NotificationService;
import net.sourceforge.stripes.action.LocalizableMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.12.13
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("notificationService")
public class NotificationServiceImpl extends BasicServiceImpl implements NotificationService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserSettingDao userSettingDao;


    private boolean createNotification(User user, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId) {
        notNull(notificationType);

        Date created = new Date();

        Notification notification = new Notification();
        notification.setCreated(created);
        notification.setNotificationType(notificationType);
        notification.setRead(false);
        notification.setUser(user);
        notification.setObjectId(objectId);

        Locale locale = null;
        if(user.getUserSetting() == null){
                UserSetting userSetting = new UserSetting();
                userSetting.setUser(user);
                userSettingDao.create(userSetting);
                user = userDao.get(user.getId());
        }

        locale = user.getUserSetting().getLocale();
        notification.setMessage(localizableMessage.getMessage(locale));
        notificationDao.create(notification);

        return true;
    }

    public boolean create(Long userId, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId) {
        notNull(userId);
        User userDB = userDao.get(userId);

        if (userDB == null) {
            logger.debug("UserDB can't be null");
            return false;
        }

        return createNotification(userDB, notificationType, localizableMessage, objectId);
    }

    public boolean create(List<User> users, NotificationType notificationType, LocalizableMessage localizableMessage, Long objectId) {
        notNull(users);
        notNull(notificationType);

        Date created = new Date();
        boolean result = true;
        for (User user : users) {

            // TODO: mail notification here
            if(!createNotification(user, notificationType, localizableMessage, objectId)){
                result = false;
            }
        }
        return result;
    }

    public boolean markAsRead(Long notificationId) {
        notNull(notificationId);

        Notification notificationDB = notificationDao.get(notificationId);

        if (notificationDB == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }

        notificationDB.setRead(true);
        notificationDao.update(notificationDB);

        return true;
    }

    @Transactional(readOnly = true)
    public List<Notification> getUnread(Long userId) {
        notNull(userId);
        User userDB = userDao.get(userId);
        if (userDB == null) {
            logger.debug("Object retrieved from database is null");
            return null;
        }

        return notificationDao.getUnread(userDB);
    }


    @Transactional(readOnly = true)
    public Integer count() {
        return notificationDao.count();
    }

    @Transactional(readOnly = true)
    public List<Notification> all() {
        return notificationDao.all();
    }

    @Transactional(readOnly = true)
    public Notification get(Long id) {
        return notificationDao.get(id);
    }

    public boolean remove(Long id) {
        notNull(id);
        Notification notification = notificationDao.get(id);
        if (notification == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }
        User user = notification.getUser();
        if (user == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }
        user.getNotifications().remove(notification);
        notificationDao.remove(notification);
        return true;
    }

    public Notification update(Notification notification) {
        notNull(notification);

        Notification notificationDB = notificationDao.get(notification.getId());
        if (notificationDB == null) {
            logger.debug("Object retrieved from database is null");
            return null;
        }

        if (notification.getNotificationType() != null)
            notificationDB.setNotificationType(notification.getNotificationType());
        if (notification.getUser() != null) notificationDB.setUser(notification.getUser());
        if (notification.getCreated() != null) notificationDB.setCreated(notification.getCreated());

        notificationDao.update(notificationDB);
        return notificationDB;
    }

    @Transactional(readOnly = true)
    public List<Notification> allOrderedBy(String orderByParam, boolean desc) {
        return notificationDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Notification> nOrderedBy(String orderByParam, boolean desc, int number) {
        return notificationDao.nOrderedBy(orderByParam, desc, number);
    }


}
