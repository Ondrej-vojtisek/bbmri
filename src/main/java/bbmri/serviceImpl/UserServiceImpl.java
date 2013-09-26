package bbmri.serviceImpl;

import bbmri.dao.BiobankAdministratorDao;
import bbmri.dao.BiobankDao;
import bbmri.dao.RoleDao;
import bbmri.dao.UserDao;
import bbmri.entities.*;
import bbmri.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
public class UserServiceImpl extends BasicServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;


    public User create(User user) {
        userDao.create(user);
        return user;
    }

    public void remove(Long id) {
        User userDB = userDao.get(id);
        if (userDB == null) {
            return;
            // TODO: exception
        }

        if (userDB.getBiobankAdministrator() != null) {
            BiobankAdministrator ba = biobankAdministratorDao.get(userDB.getBiobankAdministrator().getId());
            if (ba != null) {
                userDB.setBiobankAdministrator(null);
                userDao.update(userDB);
                biobankAdministratorDao.remove(ba);

            }
        }
        userDao.remove(userDB);
    }

    public User update(User user) {
        User userDB = userDao.get(user.getId());
        if (userDB == null) {
            return null;
        }
        if (user.getName() != null) userDB.setName(user.getName());
        if (user.getSurname() != null) userDB.setSurname(user.getSurname());
        if (user.getPassword() != null) userDB.setPassword(user.getPassword());

        userDao.update(userDB);
        return user;
    }

    public List<User> all() {
        List<User> users = userDao.all();
        return users;
    }

    public User get(Long id) {
        User userDB = userDao.get(id);
        return userDB;
    }

    public User removeRole(Long userId, RoleType roleType) {
        User userDB = userDao.get(userId);
        Role role = new Role(roleType.toString());
        if (userDB.getRoles().contains(role)) {
            Role roleDB = roleDao.get(new Long(2));
            roleDB.getUser().remove(userDB);
            roleDao.update(roleDB);
        }

        userDao.update(userDB);
        return userDB;
    }

    public User setRole(Long userId, RoleType roleType) {
        User userDB = userDao.get(userId);
        Role role = new Role(roleType.toString());
        if (!userDB.getRoles().contains(role)) {
            Role roleDB = roleDao.get(new Long(2));
            roleDB.getUser().add(userDB);
            roleDao.update(roleDB);
        }
        userDao.update(userDB);
        return userDB;
    }

    public User changeAdministrator(Long oldAdminId, Long newAdminId) {
        setRole(newAdminId, RoleType.ADMINISTRATOR);
        removeRole(oldAdminId, RoleType.ADMINISTRATOR);
        return userDao.get(oldAdminId);
    }

    public Integer count() {
        return userDao.count();
    }

    public List<User> getNonAdministratorUsers() {
        List<User> users = userDao.all();
        List<User> results = new ArrayList<User>();
        for (User user : users) {
            if (user.getBiobankAdministrator() == null) {
                results.add(user);
            }
        }

        return results;
    }

    public User eagerGet(Long id, boolean judgedProjects, boolean project) {
        notNull(id);
        User userDB = userDao.get(id);

        /*Not only comments - this force hibernate to load mentioned relationship from db. Otherwise it wont be accessible from presentational layer of application.*/

        if (judgedProjects) {
            logger.debug("" + userDB.getJudgedProjects());
        }

        if (project) {
            logger.debug("" + userDB.getProjects());
        }
        return userDB;

    }
}
