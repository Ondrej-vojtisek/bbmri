package bbmri.serviceImpl;

import bbmri.dao.RoleDao;
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
       private RoleDao roleDao;
   
       public Role create(Role role) {
           try {
               roleDao.create(role);
               return role;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }

    /*
       public void remove(Role role) {
           try {
               roleDao.remove(role);
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
       */
   
       public void remove(Long id) {
           try {
               Role roleDB = roleDao.get(id);
               if (roleDB != null) {
                   roleDao.remove(roleDB);
               }
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
   
       public Role update(Role role) {
           try {
               Role roleDB = roleDao.get(role.getId());
               if (roleDB == null) {
                   return null;
               }
               if (role.getName() != null) roleDB.setName(role.getName());

               roleDao.update(roleDB);
               return role;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
   
       public List<Role> all() {
           try {
               List<Role> roles = roleDao.all();
               return roles;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
   
       public Role get(Long id) {
           try {
               Role roleDB = roleDao.get(id);
               return roleDB;
           } catch (DataAccessException ex) {
               throw ex;
           }
       }

    public Integer count() {
           try {
               return roleDao.count();
           } catch (DataAccessException ex) {
               throw ex;
           }
       }
    
}
