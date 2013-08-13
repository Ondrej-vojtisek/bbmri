package bbmri.DAOimpl;

import bbmri.DAO.RequestDAO;
import bbmri.entities.Request;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class RequestDAOImpl extends DAOImpl<Request> implements RequestDAO {

}
