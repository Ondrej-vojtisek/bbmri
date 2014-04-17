package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface UserDao extends BasicDao<User> {

    List<User> findUser(User user);

    User get(String eppn, String targetedId, String persitentId);

    List<User> getAllWithSystemRole(SystemRole systemRole);
}
