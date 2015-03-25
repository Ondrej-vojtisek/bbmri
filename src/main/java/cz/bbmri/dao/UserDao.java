package cz.bbmri.dao;

import cz.bbmri.entity.User;

/**
 * Interface to handle instances of User stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface UserDAO extends AbstractDAO<User, Long> {

    void delete(User user);

    // TODO
//    List<User> findUser(String name, String surname, String email, int requiredResults);

//    /**
//     * Find user by its identifier from eduId federation. All arguments can be unique identifier. Not all identity
//     * providers uses the same one.
//     * Identifiers are tested gradually one by one. First is tested eppn.
//     *
//     * @param eppn - id used in eduId
//     * @param targetedId - id used in eduId
//     * @param persitentId - id used in eduId
//     * @return User identifier by at least one of given arguments or null
//     */
//    User get(String eppn, String targetedId, String persitentId);

//    /**
//     * Return all users with given system role. For instance all DEVELOPERS.
//     *
//     * @param role
//     * @return list of all users with the system role
//     */
//    List<User> getAllWithSystemRole(Role role);
}
