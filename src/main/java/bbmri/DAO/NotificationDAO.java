package bbmri.DAO;

import bbmri.entities.Notification;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 5.3.13
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
public interface NotificationDAO {

    void create(Notification notification);

    void remove(Notification notification);

    void update(Notification notification);

    List<Notification> getAll();

    List<Notification> getAllByRecipient(Long recipientId);

    Notification get(Long id);

    Integer getCount();
}
