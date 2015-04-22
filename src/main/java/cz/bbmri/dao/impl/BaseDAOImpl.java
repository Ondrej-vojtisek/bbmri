package cz.bbmri.dao.impl;

import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

/**
 * Base implementation of the database object classs.
 *
 * @author Jan Sochor
 * @version 0.1
 */
public abstract class BaseDAOImpl {

	@Autowired // Default database connection factory
	private LocalSessionFactoryBean sessionFactoryBean;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	/**
	 * Retrieves current database session generated
	 * for processing obtained database requests.
	 *
	 * @return Current database session
	 */
	public Session getCurrentSession() {

		// Retrieve session factory instance currently being utilized
		SessionFactory sessionFactory = sessionFactoryBean.getObject();

		// Return the current session instance 
		return sessionFactory.getCurrentSession();
	}

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
