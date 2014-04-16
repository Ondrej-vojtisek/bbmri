package cz.bbmri.service.simpleService;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 14.4.14
 * Time: 15:04
 * To change this template use File | Settings | File Templates.
 */
public interface Get<T> {

    T get(Long id);
}
