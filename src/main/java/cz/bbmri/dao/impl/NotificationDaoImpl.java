package cz.bbmri.dao.impl;

import cz.bbmri.dao.NotificationDao;
import cz.bbmri.entities.Notification;
import cz.bbmri.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.12.13
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class NotificationDaoImpl extends BasicDaoImpl<Notification> implements NotificationDao {

   public List<Notification> getUnread(User user){
       Query query = em.createQuery("SELECT p FROM Notification p " +
               "where p.user = :userParam and " +
               "p.read = false " +
               "ORDER BY p.created DESC" );
              query.setParameter("userParam", user);

        return query.getResultList();
   }
}
