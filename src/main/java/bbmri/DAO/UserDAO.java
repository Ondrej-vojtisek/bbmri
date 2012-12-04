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

    void create(User user, EntityManager em);

    void remove(User user, EntityManager em);

    void update(User user, EntityManager em);

    List<User> getAll(EntityManager em);

    User get(Long id, EntityManager em);

}
