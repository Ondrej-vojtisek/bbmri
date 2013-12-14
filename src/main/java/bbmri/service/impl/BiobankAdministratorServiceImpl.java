package bbmri.service.impl;

import bbmri.dao.BiobankAdministratorDao;
import bbmri.dao.BiobankDao;
import bbmri.dao.UserDao;
import bbmri.entities.Biobank;
import bbmri.entities.BiobankAdministrator;
import bbmri.entities.User;
import bbmri.entities.enumeration.Permission;
import bbmri.service.BiobankAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.9.13
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("biobankAdministratorService")
public class BiobankAdministratorServiceImpl extends BasicServiceImpl implements BiobankAdministratorService {

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    public List<BiobankAdministrator> all() {
        return biobankAdministratorDao.all();
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return biobankAdministratorDao.count();
    }

    @Transactional(readOnly = true)
    public BiobankAdministrator get(Long id) {
        notNull(id);
        return biobankAdministratorDao.get(id);
    }

    public BiobankAdministrator update(BiobankAdministrator biobankAdministrator) {
        notNull(biobankAdministrator);

        BiobankAdministrator ba = biobankAdministratorDao.get(biobankAdministrator.getId());
        if (ba == null) {
            return null;
        }

        // Only permission can be updated

        if (biobankAdministrator.getPermission() != null) ba.setPermission(biobankAdministrator.getPermission());

        biobankAdministratorDao.update(ba);

        return ba;
    }

    public boolean remove(Long id) {
        notNull(id);

        BiobankAdministrator ba = biobankAdministratorDao.get(id);
        if (ba == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }
        biobankAdministratorDao.remove(ba);
        return true;
    }

    @Transactional(readOnly = true)
    public BiobankAdministrator get(Long biobankId, Long userId) {
        notNull(biobankId);
        notNull(userId);

        Biobank biobankDB = biobankDao.get(biobankId);
        User userDB = userDao.get(userId);

        return biobankAdministratorDao.get(biobankDB, userDB);
    }

    @Transactional(readOnly = true)
    public boolean contains(Long biobankId, Long userId) {
        notNull(biobankId);
        notNull(userId);

        Biobank biobankDB = biobankDao.get(biobankId);
        User userDB = userDao.get(userId);

        if (biobankDB == null || userDB == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }

        return biobankAdministratorDao.contains(biobankDB, userDB);
    }


    /**
     * Return true if user has at least one permission same or higher then given parameter.
     * @param userId
     * @param permission
     * @return
     */
    @Transactional(readOnly = true)
    public boolean hasSameOrHigherPermission(Long userId, Permission permission) {
        notNull(userId);
        notNull(permission);

        User userDB = userDao.get(userId);

        if (userDB == null) {
            logger.debug("Object retrieved from database is null");
            return false;
        }

        Permission permissionHighest = biobankAdministratorDao.getHighestPermission(userDB);

        if(permissionHighest == null){
            return false;
        }

        /* My highest permission to any of my biobank is higher than permission? */
        return permissionHighest.include(permission);
    }


}
