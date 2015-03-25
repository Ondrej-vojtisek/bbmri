package cz.bbmri.dao;

import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public abstract interface AbstractCompositeDAO<T> {

    List<T> all();

    T save(T t);

}
