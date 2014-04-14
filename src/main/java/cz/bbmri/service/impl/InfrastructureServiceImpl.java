package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.BoxDao;
import cz.bbmri.dao.ContainerDao;
import cz.bbmri.dao.InfrastructureDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Infrastructure;
import cz.bbmri.entities.infrastructure.StandaloneBox;
import cz.bbmri.service.InfrastructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("infrastructureService")
public class InfrastructureServiceImpl extends BasicServiceImpl implements InfrastructureService {

    @Autowired
    private InfrastructureDao infrastructureDao;

    @Autowired
    private ContainerDao containerDao;

    @Autowired
    private BoxDao boxDao;


    public Infrastructure initialize(Biobank biobank) {
        if (isNull(biobank, "biobank", null)) return null;

        if (biobank.getInfrastructure() != null) {
            logger.debug("Biobank already has infrastructure");
            return biobank.getInfrastructure();
        }

        Infrastructure infrastructure = new Infrastructure();
        infrastructure.setBiobank(biobank);
        infrastructureDao.create(infrastructure);
        return infrastructure;
    }

    public boolean remove(Long id) {
        if (isNull(id, "id", null)) return false;
        Infrastructure infrastructureDB = infrastructureDao.get(id);
        if (isNull(infrastructureDB, "infrastructureDB", null)) return false;

        if (!infrastructureDB.getStandaloneBoxes().isEmpty()) {
            for (StandaloneBox box : infrastructureDB.getStandaloneBoxes()) {
                boxDao.remove(box);
            }
        }

        if (!infrastructureDB.getContainers().isEmpty()) {
            for (Container container : infrastructureDB.getContainers()) {
                containerDao.remove(container);
            }
        }

        infrastructureDB.getBiobank().setInfrastructure(null);
        infrastructureDB.setBiobank(null);

        infrastructureDao.remove(infrastructureDB);

        return true;
    }

    @Transactional(readOnly = true)
    public Infrastructure get(Long id) {
        if (isNull(id, "id", null)) return null;
        return infrastructureDao.get(id);
    }


}
