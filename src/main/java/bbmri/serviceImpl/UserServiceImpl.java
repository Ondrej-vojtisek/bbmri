package bbmri.serviceImpl;

import bbmri.DAO.UserDAO;
import bbmri.DAOimpl.UserDAOImpl;
import bbmri.entities.User;
import bbmri.service.UserService;

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
public class UserServiceImpl implements UserService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    UserDAO userDAO;

    private UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }

    public User create(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getUserDAO().create(user, em);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public void remove(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        getUserDAO().remove(user, em);
        em.getTransaction().commit();
        em.close();
    }

    public void remove(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = getUserDAO().get(id, em);
        if (userDB != null) {
            getUserDAO().remove(userDB, em);
        }
        em.getTransaction().commit();
        em.close();
    }

    public User update(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = getUserDAO().get(user.getId(), em);
        if (userDB == null) {
            em.close();
            return null;
        }
        if (user.getName() != null) userDB.setName(user.getName());
        if (user.getSurname() != null) userDB.setSurname(user.getSurname());
        if (user.getPassword() != null) userDB.setPassword(user.getPassword());

        getUserDAO().update(userDB, em);
        em.getTransaction().commit();
        em.close();
        return user;
    }

    public List<User> getAll() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<User> users = getUserDAO().getAll(em);
        em.getTransaction().commit();
        em.close();
        return users;
    }

    public User getById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = getUserDAO().get(id, em);
        em.getTransaction().commit();
        em.close();
        return userDB;
    }

    public User changeAdministrator(Long oldAdminId, Long newAdminId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userOld = getUserDAO().get(oldAdminId, em);
        User userNew = getUserDAO().get(newAdminId, em);
        userOld.setAdministrator(false);
        userNew.setAdministrator(true);
        em.getTransaction().commit();
        em.close();
        return userOld;

    }
}
