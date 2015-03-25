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

}
