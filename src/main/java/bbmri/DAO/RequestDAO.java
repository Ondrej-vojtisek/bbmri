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

    void create(Request request, EntityManager em);

    void remove(Request request, EntityManager em);

    void update(Request request, EntityManager em);

    List<Request> getAll(EntityManager em);

    Request get(Long id, EntityManager em);

}
