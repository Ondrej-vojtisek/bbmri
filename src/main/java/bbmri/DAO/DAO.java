package bbmri.DAO;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 22.7.13
 * Time: 12:52
 * To change this template use File | Settings | File Templates.
 */
public interface DAO<T> {

      List<T> all();

      T get(Long id);

      void create(T t);

      void remove(T t);

      void update(T t);

      Integer count();
}
