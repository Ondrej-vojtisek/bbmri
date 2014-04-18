package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;

import java.util.List;

/**
 * Interface to handle instances of User stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface UserDao extends BasicDao<User> {

    /**
     * Find similar user to one given as argument.
     *
     * @param user - search criteria
     * @return list of users similar to given one
     */
    List<User> findUser(User user);

    /**
     * Find user by its identifier from eduId federation. All arguments can be unique identifier. Not all identity
     * providers uses the same one.
     * Identifiers are tested gradually one by one. First is tested eppn.
     *
     * @param eppn - id used in eduId
     * @param targetedId - id used in eduId
     * @param persitentId - id used in eduId
     * @return User identifier by at least one of given arguments or null
     */
    User get(String eppn, String targetedId, String persitentId);

    /**
     * Return all users with given system role. For instance all DEVELOPERS.
     *
     * @param systemRole
     * @return list of all users with the system role
     */
    List<User> getAllWithSystemRole(SystemRole systemRole);
}
