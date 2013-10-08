package bbmri.service.impl;

import bbmri.dao.UserDao;
import bbmri.entities.User;
import bbmri.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserDao userDao;

    // temporal prosthesis
    public User login(Long id, String password) {

        if (password == null || id == null) {
            return null;
        }
        boolean result = false;
        User userDB = userDao.get(id);
        if (userDB != null && userDB.getPassword() != null) {
            if ((userDB.getPassword()).equals(password)) {
                result = true;
                userDao.update(userDB);
            }
        }
        if (result) {
            return userDB;
        }
        return null;

    }

    /*
    public void logout(User user) {
        if (user == null) {
            return;
        }

        User userDB = userDao.get(user.getId());
        userDao.update(userDB);
    }
    */
}
