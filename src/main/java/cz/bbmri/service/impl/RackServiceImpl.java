package cz.bbmri.service.impl;

import cz.bbmri.dao.BoxDao;
import cz.bbmri.dao.ContainerDao;
import cz.bbmri.dao.RackDao;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.infrastructure.RackBox;
import cz.bbmri.service.RackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 21:18
 * To change this template use File | Settings | File Templates.
 */

@Transactional
@Service("rackService")
public class RackServiceImpl extends BasicServiceImpl implements RackService {


    @Autowired
    private ContainerDao containerDao;

    @Autowired
    private RackDao rackDao;

    @Autowired
    private BoxDao boxDao;

    public Rack create(Long containerId, Rack rack) {
        if (containerId == null) {
            logger.debug("InfrastructureId can't be null");
            return null;
        }
        if (rack == null) {
            logger.debug("rack can't be null");
            return null;
        }

        Container containerDB = containerDao.get(containerId);
        if (containerDB == null) {
            logger.debug("infrastructureDB can't be null");
            return null;
        }

        rack.setContainer(containerDB);
        rackDao.create(rack);
        return rack;
    }

    public boolean remove(Long id) {
        Rack rackDB = rackDao.get(id);
        if (rackDB == null) {
            logger.debug("Rack doens't exist");
            return true;
        }
        if (!rackDB.getRackBoxes().isEmpty()) {
            for (RackBox box : rackDB.getRackBoxes()) {
                boxDao.remove(box);
            }
        }
        rackDB.setContainer(null);
        rackDao.remove(rackDB);

        return true;
    }

    public Rack update(Rack rack) {
        if (rack == null) {
            logger.debug("Rack can't be null");
            return null;
        }
        Rack rackDB = rackDao.get(rack.getId());

        if (rackDB == null) {
            logger.debug("RackDB can't be null");
            return null;
        }

        if (rack.getCapacity() != null) rackDB.setCapacity(rack.getCapacity());
        if (rack.getName() != null) rackDB.setName(rack.getName());

        if (rack.getRackBoxes() != null) rackDB.setRackBoxes(rack.getRackBoxes());

        rackDao.update(rackDB);
        return rackDB;
    }

    @Transactional(readOnly = true)
    public List<Rack> all() {
        return rackDao.all();
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return rackDao.count();
    }

    @Transactional(readOnly = true)
    public Rack get(Long id) {
        notNull(id);
        return rackDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Rack> allOrderedBy(String orderByParam, boolean desc) {
        return rackDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Rack> nOrderedBy(String orderByParam, boolean desc, int number) {
        return rackDao.nOrderedBy(orderByParam, desc, number);
    }

    @Transactional(readOnly = true)
    public Rack eagerGet(Long rackId, boolean box) {
        Rack rack = rackDao.get(rackId);

        if (box) {
            logger.debug("" + rack.getRackBoxes());
        }

        return rack;
    }

}
