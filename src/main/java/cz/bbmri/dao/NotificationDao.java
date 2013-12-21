package cz.bbmri.dao;

import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.12.13
 * Time: 12:57
 * To change this template use File | Settings | File Templates.
 */
public interface NotificationDao extends BasicDao<Notification>{

    List<Notification> getUnread(User user);
}
