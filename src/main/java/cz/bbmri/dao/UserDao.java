package cz.bbmri.dao;

import cz.bbmri.entity.User;

import java.util.List;

/**
 * Interface to handle instances of User stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface UserDAO extends AbstractDAO<User, Long> {

    void delete(User user);

    /**
     * Find user by its internal id, email or shibboleth eppn in this specified order.
     * All these attributes are considered unique in database. First match is returned.
     *
     * @param criteria - id, email or eepn
     * @return user matching given criteria
     */
    User find(String criteria);

    List<User> findUsers(String criteria, int requiredResults);

}
