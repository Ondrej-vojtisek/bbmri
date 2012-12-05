package bbmri.serviceImpl;

import bbmri.DAO.UserDAO;
import bbmri.entities.User;
import bbmri.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements UserService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    @Autowired
    private UserDAO userDAO;

    public User create(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        userDAO.create(user, em);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public void remove(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        userDAO.remove(user, em);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = userDAO.get(id, em);
        if (userDB != null) {
            userDAO.remove(userDB, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public User update(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = userDAO.get(user.getId(), em);
        if (userDB == null) {
            em.close();
            return null;
        }
        if (user.getName() != null) userDB.setName(user.getName());
        if (user.getSurname() != null) userDB.setSurname(user.getSurname());
        if (user.getPassword() != null) userDB.setPassword(user.getPassword());

        userDAO.update(userDB, em);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public List<User> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<User> users = userDAO.getAll(em);
        em.getTransaction().commit();
        em.close();
        return users;
    }

    public User getById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = userDAO.get(id, em);
        em.getTransaction().commit();
        em.close();
        return userDB;
    }

    public User changeAdministrator(Long oldAdminId, Long newAdminId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userOld = userDAO.get(oldAdminId, em);
        User userNew = userDAO.get(newAdminId, em);
        userOld.setAdministrator(false);
        userNew.setAdministrator(true);
        em.getTransaction().commit();
        em.close();
        return userOld;

    }
}
