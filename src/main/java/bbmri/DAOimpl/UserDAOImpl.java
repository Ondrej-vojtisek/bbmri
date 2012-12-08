package bbmri.DAOimpl;


import bbmri.DAO.UserDAO;
import bbmri.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager em;


    public void create(User user, EntityManager em) {
        em.persist(user);
    }

    public void remove(User user, EntityManager em) {
        em.remove(user);
    }

    public void update(User user, EntityManager em) {
        em.merge(user);
    }

    public List<User> getAll(EntityManager em) {
        Query query = em.createQuery("SELECT p FROM User p");
        return query.getResultList();
    }

    public User get(Long id, EntityManager em) {
        return em.find(User.class, id);
    }

}



