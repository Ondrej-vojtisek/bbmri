package bbmri.service;

import bbmri.entities.Notification;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 5.3.13
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public interface NotificationService {

    Notification create(Notification notification);

    void remove(Long id);

    Notification update(Notification notification);

    List<Notification> getAll();

    Integer getCount();

    List<Notification> getAllNewByRecipient(Long recipientId);

    void setAllNewByRecipientToVisited(Long recipientId);
}
