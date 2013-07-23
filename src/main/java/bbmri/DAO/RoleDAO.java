package bbmri.DAO;

import bbmri.entities.Role;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 22.7.13
 * Time: 12:55
 * To change this template use File | Settings | File Templates.
 */
public interface RoleDAO {

    void create(Role role);

    void remove(Role role);

    void update(Role role);

    List<Role> getAll();

    Role get(Long id);

    Integer getCount();
}
