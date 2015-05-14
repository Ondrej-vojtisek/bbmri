package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.RoleDAO;
import cz.bbmri.entity.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("roleDAO")
@Transactional
public class RoleDAOImpl extends GenericDAOImpl<Role> implements RoleDAO {

    public Role get(Integer id) {
                      return (Role) getCurrentSession().get(Role.class, id);
                  }

}
