package cz.bbmri.service;

import cz.bbmri.service.simple.SimpleService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.8.13
 * Time: 23:05
 * To change this template use File | Settings | File Templates.
 */
public interface BasicService<T> extends SimpleService<T> {

    boolean remove(Long id);

    T update(T t);

   // Integer count();

   // List<T> allOrderedBy(String orderByParam, boolean desc);

   // List<T> nOrderedBy(String orderByParam, boolean desc, int number);

}
