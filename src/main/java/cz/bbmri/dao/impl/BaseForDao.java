package cz.bbmri.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Class providing base for all others DAO class implementations.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
abstract public class BaseForDao {

    /* interaction with the persistent context */
    @PersistenceContext
    EntityManager em;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * Method for handling null object given as parameter in all methods in all DAO implementations
     *
     * @param o - tested object for "null"
     * @throws IllegalArgumentException
     */
    static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object can't be a null object");
        }
    }

}
