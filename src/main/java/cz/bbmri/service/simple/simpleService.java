package cz.bbmri.service.simple;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 14.4.14
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public interface SimpleService<T> {

    List<T> all();

    T get(Long id);
}
