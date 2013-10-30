package bbmri.service.impl;

import bbmri.dao.BiobankAdministratorDao;
import bbmri.dao.BiobankDao;
import bbmri.dao.UserDao;
import bbmri.entities.BiobankAdministrator;
import bbmri.entities.User;
import bbmri.entities.enumeration.SystemRole;
import bbmri.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    private BiobankDao biobankDao;

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;


    public User create(User user) {
        user.setCreated(new Date());
        userDao.create(user);
        return user;
    }

    public void remove(Long id) {
        User userDB = userDao.get(id);
        if (userDB == null) {
            return;
            // TODO: exception
        }

        Set<BiobankAdministrator> biobankAdministrators = userDB.getBiobankAdministrators();
        if (biobankAdministrators != null) {
            for (BiobankAdministrator ba : biobankAdministrators) {
                ba.setUser(null);
                ba.setBiobank(null);
                biobankAdministratorDao.remove(ba);
            }
        }
        userDao.remove(userDB);
    }

    public User update(User user) {
        notNull(user);

        User userDB = userDao.get(user.getId());
        if (userDB == null) {
            return null;
        }


        if (user.getName() != null) userDB.setName(user.getName());
        if (user.getSurname() != null) userDB.setSurname(user.getSurname());
        if (user.getPassword() != null) userDB.setPassword(user.getPassword());
        if (user.getLastLogin() != null) userDB.setLastLogin(user.getLastLogin());

        userDao.update(userDB);
        return user;
    }

    public List<User> all() {
        return userDao.all();
    }

    public User get(Long id) {
        return userDao.get(id);
    }

    public User removeRole(Long userId, SystemRole systemRole) {
        User userDB = userDao.get(userId);

        if (userDB.getSystemRoles().contains(systemRole)) {
            userDB.getSystemRoles().remove(systemRole);
        } else {
            // TODO: exception
        }

        userDao.update(userDB);
        return userDB;
    }

    public User setRole(Long userId, SystemRole systemRole) {
        User userDB = userDao.get(userId);

        if (!userDB.getSystemRoles().contains(systemRole)) {
            userDB.getSystemRoles().add(systemRole);
        } else {
            // TODO exception
        }

        userDao.update(userDB);
        return userDB;
    }

    public User changeAdministrator(Long oldAdminId, Long newAdminId) {
        setRole(newAdminId, SystemRole.ADMINISTRATOR);
        removeRole(oldAdminId, SystemRole.ADMINISTRATOR);
        return userDao.get(oldAdminId);
    }

    public Integer count() {
        return userDao.count();
    }

    public List<User> getNonAdministratorUsers() {
        List<User> users = userDao.all();
        List<User> results = new ArrayList<User>();
        for (User user : users) {
            if (user.getBiobankAdministrators().isEmpty()) {
                results.add(user);
            }
        }

        return results;
    }

    public User eagerGet(Long id, boolean judgedProjects, boolean project, boolean biobank) {
        notNull(id);
        User userDB = userDao.get(id);

        /*Not only comments - this force hibernate to load mentioned relationship from db. Otherwise it wont be accessible from presentational layer of application.*/

        if (biobank) {
            logger.debug("" + userDB.getBiobankAdministrators());
        }

        if (judgedProjects) {
            logger.debug("" + userDB.getJudgedProjects());
        }

        if (project) {
            logger.debug("" + userDB.getProjectAdministrators());
        }
        return userDB;

    }

    public void setSystemRole(Long userId, SystemRole systemRole) {
        notNull(userId);
        notNull(systemRole);

        User userDB = userDao.get(userId);

        if (userDB == null) {
            return;
            // TODO: exception
        }

        if (userDB.getSystemRoles().contains(systemRole)) {
            return;
            // TODO: exception
        }
        userDB.getSystemRoles().add(systemRole);
        userDao.update(userDB);
    }

    public void removeSystemRole(Long userId, SystemRole systemRole) {
        notNull(userId);
        notNull(systemRole);

        User userDB = userDao.get(userId);

        if (userDB == null) {
            return;
            // TODO: exception
        }

        if (!userDB.getSystemRoles().contains(systemRole)) {
            return;
            // TODO: exception
        }
        userDB.getSystemRoles().remove(systemRole);
        userDao.update(userDB);
    }

    public List<User> getAllByRole(SystemRole systemRole) {
        notNull(systemRole);
        List<User> results = new ArrayList<User>();
        for (User user : userDao.all()) {
            if (user.getSystemRoles().contains(systemRole)) {
                results.add(user);
            }
        }
        return results;
    }

    public List<User> find(User user, int requiredResults) {
        logger.debug("FIND_SERVICE");

        List<User> users = userDao.findUser(user);
        if (users == null) {
            return null;
        }
        if (requiredResults > users.size()) {
            return users;
        }
        return userDao.findUser(user).subList(0, requiredResults);
    }
}
