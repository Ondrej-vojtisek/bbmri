package bbmri.service.impl;

import bbmri.dao.RoleDao;
import bbmri.entities.Role;
import bbmri.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RoleServiceImpl extends BasicServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role create(Role role) {
        notNull(role);
        roleDao.create(role);
        return role;
    }

    public void remove(Long id) {
        notNull(id);
        Role roleDB = roleDao.get(id);
        if (roleDB != null) {
            roleDao.remove(roleDB);
        }
    }

    public Role update(Role role) {
        notNull(role);

        Role roleDB = roleDao.get(role.getId());
        if(roleDB == null){
            return null;
            // TODO: exception
        }
        if (role.getName() != null) roleDB.setName(role.getName());

        roleDao.update(roleDB);
        return role;
    }

    public List<Role> all() {
        return roleDao.all();
    }

    public Role get(Long id) {
        notNull(id);
        return roleDao.get(id);
    }

    public Integer count() {
        return roleDao.count();
    }

}
