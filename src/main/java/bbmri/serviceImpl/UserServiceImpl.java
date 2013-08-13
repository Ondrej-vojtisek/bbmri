package bbmri.serviceImpl;

import bbmri.dao.RoleDao;
import bbmri.dao.UserDao;
import bbmri.entities.Role;
import bbmri.entities.RoleType;
import bbmri.entities.User;
import bbmri.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
        private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    public User create(User user) {
        try {
            userDao.create(user);
            return user;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(User user) {
        try {
            userDao.remove(user);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        try {
            User userDB = userDao.get(id);
            if (userDB != null) {
                userDao.remove(userDB);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public User update(User user) {
        try {
            User userDB = userDao.get(user.getId());
            if (userDB == null) {
                return null;
            }
            if (user.getName() != null) userDB.setName(user.getName());
            if (user.getSurname() != null) userDB.setSurname(user.getSurname());
            if (user.getPassword() != null) userDB.setPassword(user.getPassword());

            userDao.update(userDB);
            return user;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<User> getAll() {
        try {
            List<User> users = userDao.all();
            return users;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public User getById(Long id) {
        try {
            User userDB = userDao.get(id);
            return userDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public User removeRole(Long userId, RoleType roleType){
          try {
              User userDB = userDao.get(userId);
              Role role = new Role(roleType.toString());
              if(userDB.getRoles().contains(role)){
                  Role roleDB = roleDao.get(new Long(2));
                  roleDB.getUser().remove(userDB);
                  roleDao.update(roleDB);
              }

              userDao.update(userDB);
              return userDB;
          } catch (DataAccessException ex) {
              throw ex;
          }
      }

    public User setRole(Long userId, RoleType roleType){
        try {
            User userDB = userDao.get(userId);
            Role role = new Role(roleType.toString());
            if(!userDB.getRoles().contains(role)){
                Role roleDB = roleDao.get(new Long(2));
                roleDB.getUser().add(userDB);
                roleDao.update(roleDB);
            }
            userDao.update(userDB);
            return userDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public User changeAdministrator(Long oldAdminId, Long newAdminId) {
        try {
            setRole(newAdminId, RoleType.ADMINISTRATOR);
            removeRole(oldAdminId, RoleType.ADMINISTRATOR);
            return userDao.get(oldAdminId);
        } catch (DataAccessException ex) {
            throw ex;
        }

    }

    public Integer getCount() {
        try {
            return userDao.count();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<User> getNonAdministratorUsers() {
        try {
            List<User> users = userDao.all();
            List<User> results = new ArrayList<User>();
            for(User user : users){
                if(user.getBiobank() == null){
                    results.add(user);
                }
            }

            return results;
        } catch (DataAccessException ex) {
            throw ex;
        }

    }

    public List<User> getAdministratorsOfBiobank(Long biobankId) {
            try {
                List<User> users = userDao.all();
                List<User> results = new ArrayList<User>();
                for(User user : users){
                    if(user.getBiobank().getId() == biobankId){
                        results.add(user);
                    }
                }

                return results;
            } catch (DataAccessException ex) {
                throw ex;
            }

        }
}
