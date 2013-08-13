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
public class RoleDAOImpl implements RoleDAO {
    
    @PersistenceContext
        private EntityManager em;
    
    public void create(Role role) {
           DAOUtils.notNull(role);
           em.persist(role);
       }
   
       public void remove(Role role) {
           DAOUtils.notNull(role);
           em.remove(role);
       }
   
       public void update(Role role) {
           DAOUtils.notNull(role);
           em.merge(role);
       }
   
       public List<Role> all() {
           Query query = em.createQuery("SELECT p FROM Role p");
           return query.getResultList();
       }
   
       public Role get(Long id) {
           DAOUtils.notNull(id);
           return em.find(Role.class, id);
       }

       public Integer count() {
           Query query = em.createQuery("SELECT COUNT (p) FROM Role p");
           return Integer.parseInt(query.getSingleResult().toString());
       }

}
