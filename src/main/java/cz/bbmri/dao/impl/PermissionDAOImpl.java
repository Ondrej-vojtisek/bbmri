package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ArchiveDAO;
import cz.bbmri.dao.PermissionDAO;
import cz.bbmri.entity.Archive;
import cz.bbmri.entity.Permission;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("permissionDAO")
@Transactional
public class PermissionDAOImpl extends GenericDAOImpl<Permission> implements PermissionDAO {

    public Permission get(Integer id) {
                      return (Permission) getCurrentSession().get(Permission.class, id);
                  }

}
