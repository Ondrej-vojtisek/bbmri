package bbmri.serviceImpl;

import bbmri.DAO.UserDAO;
import bbmri.entities.User;
import bbmri.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    public User create(User user) {
        userDAO.create(user);
        return user;
    }

    public void remove(User user) {
        userDAO.remove(user);
    }

    public void remove(Long id) {
        User userDB = userDAO.get(id);
        if (userDB != null) {
            userDAO.remove(userDB);
        }
    }

    public User update(User user) {
        User userDB = userDAO.get(user.getId());
        if (userDB == null) {
            return null;
        }
        if (user.getName() != null) userDB.setName(user.getName());
        if (user.getSurname() != null) userDB.setSurname(user.getSurname());
        if (user.getPassword() != null) userDB.setPassword(user.getPassword());

        userDAO.update(userDB);
        return user;
    }

    public List<User> getAll() {
        List<User> users = userDAO.getAll();
        return users;
    }

    public User getById(Long id) {
        User userDB = userDAO.get(id);
        return userDB;
    }

    public User changeAdministrator(Long oldAdminId, Long newAdminId) {
        User userOld = userDAO.get(oldAdminId);
        User userNew = userDAO.get(newAdminId);
        userOld.setAdministrator(false);
        userNew.setAdministrator(true);
        return userOld;

    }
}
