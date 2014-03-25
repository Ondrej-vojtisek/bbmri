package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.ContainerDao;
import cz.bbmri.dao.InfrastructureDao;
import cz.bbmri.dao.RackDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Infrastructure;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 16:45
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("containerService")
public class ContainerServiceImpl extends BasicServiceImpl implements ContainerService {

    @Autowired
    private InfrastructureDao infrastructureDao;

    @Autowired
    private ContainerDao containerDao;

    @Autowired
    private RackDao rackDao;

    @Autowired
    private BiobankDao biobankDao;

    public Container create(Long infrastructureId, Container container) {
        if (infrastructureId == null) {
            logger.debug("InfrastructureId can't be null");
            return null;
        }
        if (container == null) {
            logger.debug("container can't be null");
            return null;
        }

        Infrastructure infrastructureDB = infrastructureDao.get(infrastructureId);
        if (infrastructureDB == null) {
            logger.debug("infrastructureDB can't be null");
            return null;
        }

        container.setInfrastructure(infrastructureDB);
        containerDao.create(container);
        return container;
    }

    public boolean remove(Long id) {
        Container containerDB = containerDao.get(id);
        if (containerDB == null) {
            logger.debug("Container doens't exist");
            return true;
        }
        if (!containerDB.getRacks().isEmpty()) {
            for (Rack rack : containerDB.getRacks()) {
                rackDao.remove(rack);
            }
        }
        containerDB.setInfrastructure(null);
        containerDao.remove(containerDB);

        return true;
    }

    public Container update(Container container) {
        if (container == null) {
            logger.debug("Container can't be null");
            return null;
        }
        Container containerDB = containerDao.get(container.getId());

        if (containerDB == null) {
            logger.debug("ContainerDB can't be null");
            return null;
        }

        if (container.getLocation() != null) containerDB.setLocation(container.getLocation());
        if (container.getName() != null) containerDB.setName(container.getName());
        if (container.getCapacity() != null) containerDB.setCapacity(container.getCapacity());
        if (container.getTempMax() != null) containerDB.setTempMax(container.getTempMax());
        if (container.getTempMin() != null) containerDB.setTempMin(container.getTempMin());

        if (container.getRacks() != null) containerDB.setRacks(container.getRacks());

        containerDao.update(containerDB);
        return containerDB;
    }

    @Transactional(readOnly = true)
    public List<Container> all() {
        return containerDao.all();
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return containerDao.count();
    }

    @Transactional(readOnly = true)
    public Container get(Long id) {
        notNull(id);
        return containerDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Container> allOrderedBy(String orderByParam, boolean desc) {
        return containerDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Container> nOrderedBy(String orderByParam, boolean desc, int number) {
        return containerDao.nOrderedBy(orderByParam, desc, number);
    }

    public List<Container> getSortedContainers(Long biobankId, String orderByParam, boolean desc) {
        if (biobankId == null) {
            logger.debug("biobankId is null");
            return null;
        }

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can´t be null");
            return null;
        }

        return containerDao.getSorted(biobankDB, orderByParam, desc);
    }

    public Container getContainerByName(Biobank biobank, String name) {
        return containerDao.getByName(biobank,  name);
    }
}
