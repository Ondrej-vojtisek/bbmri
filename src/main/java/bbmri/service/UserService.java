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

    public User create(User user);

    public void remove(User user);

    public void remove(Long id);

    public User update(User user);

    public List<User> getAll();

    public User getById(Long id);

    public User changeAdministrator(Long oldAdminId, Long newAdminId);

    public Integer getCount();

}
