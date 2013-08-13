package bbmri.DAOimpl;

import bbmri.DAO.RoleDAO;
import bbmri.entities.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Role: Ori
 * Date: 22.7.13
 * Time: 12:54
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RoleDAOImpl extends DAOImpl<Role> implements RoleDAO {

}
