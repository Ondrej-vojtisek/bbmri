package bbmri.serviceImpl;

import bbmri.DAO.UserDAO;
import bbmri.entities.Role;
import bbmri.entities.RoleType;
import bbmri.entities.User;
import bbmri.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        try {
            if(user.getId() != null){
                if(userDAO.get(user.getId()) != null){
                    // TODO throw exception - There is already a user with the same ID in the database
                    return null;
                }
//                Long id = user.getId();
//                user.setId(null);
//                userDAO.create(user);
//                user.setId(id);
                System.err.println("User: " + user);

                user.setId(new Long(10));
                System.err.println("User: " + user);

                userDAO.update(user);

                System.err.println("User: " + user);

                return user;
            }
            userDAO.create(user);
            return user;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(User user) {
        try {
            userDAO.remove(user);
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public void remove(Long id) {
        try {
            User userDB = userDAO.get(id);
            if (userDB != null) {
                userDAO.remove(userDB);
            }
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public User update(User user) {
        try {
            User userDB = userDAO.get(user.getId());
            if (userDB == null) {
                return null;
            }
            if (user.getName() != null) userDB.setName(user.getName());
            if (user.getSurname() != null) userDB.setSurname(user.getSurname());
            if (user.getPassword() != null) userDB.setPassword(user.getPassword());

            userDAO.update(userDB);
            return user;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<User> getAll() {
        try {
            List<User> users = userDAO.getAll();
            return users;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public User getById(Long id) {
        try {
            User userDB = userDAO.get(id);
            return userDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public User removeRole(Long userId, RoleType roleType){
          try {
              User userDB = userDAO.get(userId);
              Role role = new Role(roleType.toString());
              if(userDB.getRoles().contains(role)){
                  userDB.getRoles().remove(role);
                  userDAO.update(userDB);
              }
              return userDB;
          } catch (DataAccessException ex) {
              throw ex;
          }
      }

    public User setRole(Long userId, RoleType roleType){
        try {
            User userDB = userDAO.get(userId);
            Role role = new Role(roleType.toString());
            if(!userDB.getRoles().contains(role)){
                userDB.getRoles().add(role);
                userDAO.update(userDB);
            }
            return userDB;
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public User changeAdministrator(Long oldAdminId, Long newAdminId) {
        try {
            removeRole(oldAdminId, RoleType.ADMINISTRATOR);
            setRole(newAdminId, RoleType.ADMINISTRATOR);
            return userDAO.get(oldAdminId);
        } catch (DataAccessException ex) {
            throw ex;
        }

    }

    public Integer getCount() {
        try {
            return userDAO.getCount();
        } catch (DataAccessException ex) {
            throw ex;
        }
    }

    public List<User> getNonAdministratorUsers() {
        try {
            return userDAO.getAllNonAdministratorUsers();
        } catch (DataAccessException ex) {
            throw ex;
        }

    }
}
