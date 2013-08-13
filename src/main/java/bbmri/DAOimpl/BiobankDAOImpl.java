package bbmri.DAOimpl;

import bbmri.DAO.BiobankDAO;
import bbmri.entities.Biobank;
import bbmri.entities.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class BiobankDAOImpl extends DAOImpl<Biobank> implements BiobankDAO {

}
