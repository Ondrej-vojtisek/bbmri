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
public interface DAO<T, ID extends Serializable> {

    public List<T> read();
    public T read(ID id);
    public void save(T t);
    public void delete(T t);
    public void commit();
}
