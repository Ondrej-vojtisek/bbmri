package cz.bbmri.dao;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public abstract interface AbstractDAO<T, ID> {

    T get(ID id);

    List<T> all();

    T save(T t);

//    boolean refresh(T t);
//
//    boolean evict(T t);

    /**
     * Return all instances of type T ordered by given parameter. Desc param changes if it is ordered DESC or ASC
     *
     * @param orderByParam - select column by which will the result be sorted
     * @param desc         - flag determining if order will be DESC (true) or default ASC (false)
     * @return List of all instances of type T ordered by given parameter.
     */
    List<T> allOrderedBy(String orderByParam, boolean desc);

}
