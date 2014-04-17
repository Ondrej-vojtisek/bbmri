package cz.bbmri.dao.simple;

import java.util.List;

/**
 * Generic DAO (Data Access Object) interface which is used to define basic operation on each defined entities.
 * It is used to achieve basic CRUD operations without relationship between objects and basic SQL queries. Type T
 * represents entity (e.g. User, Biobank, Project etc, ...) which the implementation will manage.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */

public interface BasicDao<T> {

    /**
     * Retrieves all objects of given type stored in database - SELECT * FROM T;
     *
     *
     * @return list containing all objects of type T currently present in database
     */
      List<T> all();

    /**
     * Retrieves object of type T which has identifier id. Id is generated automatically.
     *
     * @param id - unique identifier of object which we want to retrieve from DB
     * @return object of given type T
     */
      T get(Long id);

    /**
     * Insert instance of object T into database.
     *
     * @param t - instance of T. Object which will be inserted into database
     */
      void create(T t);

    /**
     * Delete given instance of object T from database.
     *
     * @param t - given instance which will be deleted
     */
      void remove(T t);

    /**
     * If there is an instance of T with the same identifier in database, then this instance is replaced with given one.
     * If not the given instance is inserted into database.
     *
     * @param t - instance of T which will be inserted into DB
     */
      void update(T t);

    /**
     * Return count of all instances of object T which are stored in database.
     *
     * @return count of all instances
     */
      Integer count();

    /**
     * Return all instances of type T ordered by given parameter. Boolean changes if it is ordered DESC or ASC
     *
     * @param orderByParam - name of object atribute e.g. "name" which will be used for order
     * @param desc - flag determining if order will be DESC or default ASC
     * @return List of all instances of type T ordered by given parameter.
     */
    List<T> allOrderedBy(String orderByParam, boolean desc);

}
