package cz.bbmri.dao.impl;


import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.Contact;
import cz.bbmri.entity.Shibboleth;
import cz.bbmri.entity.User;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Implementation for class handling instances of User. Implementation is using JPQL.
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

    public boolean isNumeric(String str) {
        try {
            long d = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public User find(String criteria) {

        // Find by id
        if (isNumeric(criteria)) {
            long id = Long.parseLong(criteria);
            User user = get(id);
            return user;
        }

        // Find by email
        List<User> users = getCurrentSession().createCriteria(User.class)
                .createAlias("contact", "contact")
                .add(Restrictions.eq("contact.email", criteria)).list();

        if (!users.isEmpty()) {
            return users.get(0);
        }

        // Find by eppn
        users = getCurrentSession().createCriteria(User.class)
                .createAlias("shibboleth", "shibboleth")
                .add(Restrictions.eq("shibboleth.eppn", criteria)).list();

        if (!users.isEmpty()) {
            return users.get(0);
        }

        return null;
    }

    public List<User> findUsers(String criteria, int requiredResults) {
        System.err.println("Search Criteria: " + criteria);

        Set<User> results = new HashSet<User>();

        List<User> tempResult;



        tempResult = getCurrentSession().createCriteria(User.class)
                .createAlias("shibboleth", "shibboleth")
                .add(Restrictions.ilike("shibboleth." + Shibboleth.PROP_NAME, criteria)).setMaxResults(MAX_SEARCH_RESULTS).list();

        System.err.println("RESULT1: " + tempResult);

        if (tempResult != null) {
            results.addAll(tempResult);
        }

        tempResult = getCurrentSession().createCriteria(User.class)
                .createAlias("shibboleth", "shibboleth")
                .add(Restrictions.ilike("shibboleth." + Shibboleth.PROP_SURNAME, criteria)).setMaxResults(MAX_SEARCH_RESULTS).list();

        System.err.println("RESULT2: " + tempResult);

        if (tempResult != null) {
            results.addAll(tempResult);
        }



        return new ArrayList<User>(results);

    }

}



