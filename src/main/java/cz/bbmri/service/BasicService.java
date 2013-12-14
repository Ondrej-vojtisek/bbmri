package cz.bbmri.service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public interface BasicService<T> {

    List<T> all();

    T get(Long id);

    boolean remove(Long id);

    T update(T t);

    Integer count();
}
