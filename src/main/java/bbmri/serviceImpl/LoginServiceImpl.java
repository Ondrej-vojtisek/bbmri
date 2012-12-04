package bbmri.serviceImpl;

import bbmri.DAO.UserDAO;
import bbmri.DAOimpl.UserDAOImpl;
import bbmri.entities.User;
import bbmri.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class LoginServiceImpl implements LoginService {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    @Autowired
    private UserDAO userDAO;

    // temporal prosthesis
    public User login(Long id, String password) {
        if (password == null || id == null) {
            return null;
        }
        boolean result = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User userDB = userDAO.get(id, em);
        if (userDB != null && userDB.getPassword() != null) {
            if ((userDB.getPassword()).equals(password)) {
                result = true;
                userDB.setOnline(true);
                userDAO.update(userDB, em);
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
        userDAO.update(user, em);
        em.getTransaction().commit();
        em.close();
    }
}
