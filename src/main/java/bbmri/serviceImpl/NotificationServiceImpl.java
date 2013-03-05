package bbmri.serviceImpl;

import bbmri.DAO.NotificationDAO;
import bbmri.entities.Notification;
import bbmri.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 5.3.13
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */

@Transactional
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDAO notificationDAO;

    public Notification create(Notification notification) {
        try {
            notificationDAO.create(notification);
            return notification;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        try {
            Notification notification = notificationDAO.get(id);
            if (notification != null) {
                notificationDAO.remove(notification);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }

    }

    public Notification update(Notification notification) {
        try {
            notificationDAO.update(notification);
            return notification;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Notification> getAll() {
        try {
            return notificationDAO.getAll();
        } catch (DataAccessException ex) {
            throw ex;
        }

    }

    public Integer getCount() {
        try {
            return notificationDAO.getCount();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<Notification> getAllNewByRecipient(Long recipientId) {
            try {
                return notificationDAO.getAllByRecipient(recipientId);
            } catch (DataAccessException ex) {
                throw ex;
            }
        }

    public void setAllNewByRecipientToVisited(Long recipientId) {
            try {
                List<Notification> notifications = notificationDAO.getAllByRecipient(recipientId);
                if(notifications != null){
                    for(int i = 0; i < notifications.size(); i++){
                        Notification notification = notifications.get(i);
                        if(!notification.isVisited()){
                            notification.setVisited(true);
                            notificationDAO.update(notification);
                        }
                    }
                }
            } catch (DataAccessException ex) {
                throw ex;
            }
        }
}
