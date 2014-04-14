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

    @Autowired
    private BiobankDao biobankDao;


    public Infrastructure initialize(Biobank biobank) {
        if (biobank == null) {
            logger.debug("Object is null");
            return null;
        }
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
        Infrastructure infrastructureDB = infrastructureDao.get(id);
        if (infrastructureDB == null) {
            logger.debug("Infrastructure doens't exist");
            return true;
        }
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

    public Infrastructure update(Infrastructure infrastructure) {
        if (infrastructure == null) {
            logger.debug("Infrastructure can't be null");
            return null;
        }
        Infrastructure infrastructureDB = infrastructureDao.get(infrastructure.getId());

        if (infrastructureDB == null) {
            logger.debug("InfrastructureDB can't be null");
            return null;
        }

        if (infrastructure.getContainers() != null) infrastructureDB.setContainers(infrastructure.getContainers());
        if (infrastructure.getStandaloneBoxes() != null)
            infrastructureDB.setStandaloneBoxes(infrastructure.getStandaloneBoxes());

        infrastructureDao.update(infrastructureDB);
        return infrastructureDB;
    }

    @Transactional(readOnly = true)
    public List<Infrastructure> all() {
        return infrastructureDao.all();
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return infrastructureDao.count();
    }

    @Transactional(readOnly = true)
    public Infrastructure get(Long id) {
        notNull(id);
        return infrastructureDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Infrastructure> allOrderedBy(String orderByParam, boolean desc) {
        return infrastructureDao.allOrderedBy(orderByParam, desc);
    }

    public boolean create(Long biobankId) {
        Infrastructure infrastructure = initialize(biobankDao.get(biobankId));
        // This method is not caused intentionally by user so there is no need to create any un-success messages

        if (infrastructure == null) {
            logger.debug("Infrastructure was not created");
            return false;
        }

        return true;
    }

}
