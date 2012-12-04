package bbmri.serviceImpl;

import bbmri.DAO.UserDAO;
import bbmri.DAOimpl.UserDAOImpl;
import bbmri.entities.User;
import bbmri.service.LoginService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class LoginServiceImpl implements LoginService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
    UserDAO userDAO;

    private UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }

    // temporal prosthesis
    public User login(Long id, String password) {
        if (password == null || id < 0) {
            return null;
        }
        boolean result = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = getUserDAO().get(id, em);
        if (userDB != null && userDB.getPassword() != null) {
            if ((userDB.getPassword()).equals(password)) {
                result = true;
                userDB.setOnline(true);
                getUserDAO().update(userDB, em);
                em.getTransaction().commit();
            }

        }
        em.close();
        if (result) {
            return userDB;
        }
        return null;
    }

    public void logout(User user) {
        if (user == null) {
            return;
        }
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        user.setOnline(false);
        getUserDAO().update(user, em);
        em.getTransaction().commit();
        em.close();
    }
}
