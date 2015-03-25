package cz.bbmri.dao.impl;


import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *  Implementation for class handling instances of User. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository("userDAO")
@Transactional(readOnly = true)
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO {

    public User get(Long id) {

        return (User) getCurrentSession().get(User.class, id);
    }


    @Transactional(readOnly = false)
    public void delete(User user) {

        // Perform the desired operation
        getCurrentSession().delete(user);

        // Result
        return;
    }
}



