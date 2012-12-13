package bbmri.DAO;

import bbmri.entities.Request;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */
public interface RequestDAO {

    void create(Request request);

    void remove(Request request);

    void update(Request request);

    List<Request> getAll();

    Request get(Long id);

    Integer getCount();

}
