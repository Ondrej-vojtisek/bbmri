package bbmri.DAO;

import bbmri.entities.User;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public interface UserDAO {

    void create(User user);

    void remove(User user);

    void update(User user);

    List<User> getAll();

    User get(Long id);

    Integer getCount();
}
