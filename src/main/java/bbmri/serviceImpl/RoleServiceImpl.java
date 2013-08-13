package bbmri.serviceImpl;

import bbmri.DAO.RoleDAO;
import bbmri.entities.Role;
import bbmri.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Role: Ori
 * Date: 22.7.13
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
       private RoleDAO roleDAO;
   
       public Role create(Role role) {
           try {
               roleDAO.create(role);
               return role;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
   
       public void remove(Role role) {
           try {
               roleDAO.remove(role);
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
   
       public void remove(Long id) {
           try {
               Role roleDB = roleDAO.get(id);
               if (roleDB != null) {
                   roleDAO.remove(roleDB);
               }
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
   
       public Role update(Role role) {
           try {
               Role roleDB = roleDAO.get(role.getId());
               if (roleDB == null) {
                   return null;
               }
               if (role.getName() != null) roleDB.setName(role.getName());

               roleDAO.update(roleDB);
               return role;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
   
       public List<Role> getAll() {
           try {
               List<Role> roles = roleDAO.all();
               return roles;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
   
       public Role getById(Long id) {
           try {
               Role roleDB = roleDAO.get(id);
               return roleDB;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
    
}
