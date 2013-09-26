package bbmri.serviceImpl;

import bbmri.dao.BiobankAdministratorDao;
import bbmri.entities.BiobankAdministrator;
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

        // TODO

        return null;
    }

    public void remove(Long id) {
        notNull(id);

        // TODO
    }


}
