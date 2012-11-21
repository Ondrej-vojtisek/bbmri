package bbmri.DAO;

import bbmri.entities.Request;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */
public interface RequestDAO {

    public void create(Request request, EntityManager em);

    public void remove(Request request, EntityManager em);

    public void update(Request request, EntityManager em);

    public List<Request> getAll(EntityManager em);

    public Request get(Long id, EntityManager em);

}
