package bbmri.DAOimpl;

import bbmri.DAO.NotificationDAO;
import bbmri.entities.Notification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 5.3.13
 * Time: 23:20
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class NotificationDAOImpl implements NotificationDAO {

    @PersistenceContext
       private EntityManager em;

    public void create(Notification notification){
        DAOUtils.notNull(notification);
        em.persist(notification);
    }

    public void remove(Notification notification){
        DAOUtils.notNull(notification);
        em.remove(notification);
    }

    public void update(Notification notification){
        DAOUtils.notNull(notification);
               em.merge(notification);
    }

    public List<Notification> getAll(){
        Query query = em.createQuery("SELECT p FROM Notification p");
        return query.getResultList();
    }

    public List<Notification> getAllByRecipient(Long recipientId){
        DAOUtils.notNull(recipientId);
        Query query = em.createQuery("SELECT p FROM Notification p where p.recipientId = :recipientId");
        return query.getResultList();
    }

    public Notification get(Long id){
        DAOUtils.notNull(id);
        return em.find(Notification.class, id);
    }

    public Integer getCount(){
        Query query = em.createQuery("SELECT COUNT (p) FROM Notification p");
        return Integer.parseInt(query.getSingleResult().toString());
    }
}
