package bbmri.dao;

import bbmri.entities.Biobank;
import bbmri.entities.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao extends BasicDao<User>{

   /*
    List<User> findUserByEmail(String email);

    List<User> findUser(String firstname, String surname);
   */

    List<User> findUser(User user);
}
