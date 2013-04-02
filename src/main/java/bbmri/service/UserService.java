package bbmri.service;

import bbmri.entities.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 2.11.12
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

     User create(User user);

     void remove(User user);

     void remove(Long id);

     User update(User user);

     List<User> getAll();

     User getById(Long id);

     User changeAdministrator(Long oldAdminId, Long newAdminId);

     Integer getCount();

    List<User> getNonAdministratorUsers();

}
