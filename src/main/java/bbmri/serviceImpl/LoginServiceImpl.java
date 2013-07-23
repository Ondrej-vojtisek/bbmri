package bbmri.serviceImpl;

import bbmri.DAO.UserDAO;
import bbmri.entities.User;
import bbmri.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDAO userDAO;

    // temporal prosthesis
    public User login(Long id, String password) {
        if (password == null || id == null) {
            return null;
        }
        boolean result = false;
        try {
            User userDB = userDAO.get(id);
            if (userDB != null && userDB.getPassword() != null) {
                if ((userDB.getPassword()).equals(password)) {
                    result = true;
                    userDAO.update(userDB);
                }
            }
            if (result) {
                return userDB;
            }
            return null;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void logout(User user) {
        if (user == null) {
            return;
        }
        try {
            User userDB = userDAO.get(user.getId());
            userDAO.update(userDB);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }
}
