package bbmri.service;

import bbmri.entities.Role;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Role: Ori
 * Date: 22.7.13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public interface RoleService {
    
    Role create(Role role);
   
    void remove(Role role);
   
    void remove(Long id);
   
    Role update(Role role);
   
    List<Role> getAll();
   
    Role getById(Long id);
    
}
