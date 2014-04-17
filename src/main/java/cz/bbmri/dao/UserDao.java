package cz.bbmri.dao;

import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao extends BasicDao<User, Long>{

    List<User> findUser(User user);

    User get(String eppn, String targetedId, String persitentId);

    List<User> getAllWithSystemRole(SystemRole systemRole);
}
