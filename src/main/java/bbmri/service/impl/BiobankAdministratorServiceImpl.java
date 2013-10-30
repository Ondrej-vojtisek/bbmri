package bbmri.service.impl;

import bbmri.dao.BiobankAdministratorDao;
import bbmri.dao.BiobankDao;
import bbmri.dao.UserDao;
import bbmri.entities.Biobank;
import bbmri.entities.BiobankAdministrator;
import bbmri.entities.User;
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
@Service
public class BiobankAdministratorServiceImpl extends BasicServiceImpl implements BiobankAdministratorService {

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private UserDao userDao;

    public List<BiobankAdministrator> all() {
        return biobankAdministratorDao.all();
    }

    public Integer count() {
        return biobankAdministratorDao.count();
    }

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

        // Only persmission can be updated

        if (biobankAdministrator.getPermission() != null) ba.setPermission(biobankAdministrator.getPermission());

        biobankAdministratorDao.update(ba);

        return ba;
    }

    public void remove(Long id) {
        notNull(id);

        BiobankAdministrator ba = biobankAdministratorDao.get(id);
        if (ba == null) {
            return;
        }
        biobankAdministratorDao.remove(ba);
    }

    public BiobankAdministrator get(Long biobankId, Long userId) {
        notNull(biobankId);
        notNull(userId);

        Biobank biobankDB = biobankDao.get(biobankId);
        User userDB = userDao.get(userId);

        return biobankAdministratorDao.get(biobankDB, userDB);
    }


    public boolean contains(Long biobankId, Long userId) {
        notNull(biobankId);
        notNull(userId);

        Biobank biobankDB = biobankDao.get(biobankId);
        User userDB = userDao.get(userId);

        return !(biobankDB == null || userDB == null) && biobankAdministratorDao.contains(biobankDB, userDB);
    }


}
