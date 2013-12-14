package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankAdministratorDao;
import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ProjectAdministratorDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.UserService;
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
@Service("userService")
public class UserServiceImpl extends BasicServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private UserDao userDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;

    @Autowired
    private ProjectAdministratorDao projectAdministratorDao;

    public User create(User user) {
        user.setCreated(new Date());
        user.getSystemRoles().add(SystemRole.USER);
        userDao.create(user);
        return user;
    }

    public boolean remove(Long id) {
        User userDB = userDao.get(id);
        if (userDB == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }

        Set<BiobankAdministrator> biobankAdministrators = userDB.getBiobankAdministrators();
        if (biobankAdministrators != null) {
            for (BiobankAdministrator ba : biobankAdministrators) {
                ba.setUser(null);
                ba.setBiobank(null);
                biobankAdministratorDao.remove(ba);
            }
        }

        List<ProjectAdministrator> projectAdministrators = userDB.getProjectAdministrators();
        if (projectAdministrators != null) {
            for (ProjectAdministrator pa : projectAdministrators) {
                pa.setUser(null);
                pa.setProject(null);
                projectAdministratorDao.remove(pa);
            }
        }


        userDao.remove(userDB);
        return true;
    }

    public User update(User user) {
        notNull(user);

        User userDB = userDao.get(user.getId());
        if (userDB == null) {
            logger.debug("Object retrieved from database is null");
            return null;
        }

        // TODO: pridat i Shibboleti pole

        if (user.getName() != null) userDB.setName(user.getName());
        if (user.getSurname() != null) userDB.setSurname(user.getSurname());
        if (user.getPassword() != null) userDB.setPassword(user.getPassword());
        if (user.getLastLogin() != null) userDB.setLastLogin(user.getLastLogin());

        userDao.update(userDB);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> all() {
        return userDao.all();
    }

    @Transactional(readOnly = true)
    public User get(Long id) {
        return userDao.get(id);
    }

    public User removeRole(Long userId, SystemRole systemRole) {
        User userDB = userDao.get(userId);

        if (userDB.getSystemRoles().contains(systemRole)) {
            userDB.getSystemRoles().remove(systemRole);
        } else {
            // TODO: exception - user already has the permission
        }

        userDao.update(userDB);
        return userDB;
    }

    public User setRole(Long userId, SystemRole systemRole) {
        User userDB = userDao.get(userId);

        if (!userDB.getSystemRoles().contains(systemRole)) {
            userDB.getSystemRoles().add(systemRole);
        } else {
            // TODO exception - user already has the permission
        }

        userDao.update(userDB);
        return userDB;
    }

    public User changeAdministrator(Long oldAdminId, Long newAdminId) {
        setRole(newAdminId, SystemRole.ADMINISTRATOR);
        removeRole(oldAdminId, SystemRole.ADMINISTRATOR);
        return userDao.get(oldAdminId);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public User get(String eppn){
        notNull(eppn);
        return userDao.get(eppn);
    }
}
