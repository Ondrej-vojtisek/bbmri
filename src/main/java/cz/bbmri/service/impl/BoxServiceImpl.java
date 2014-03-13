package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.*;
import cz.bbmri.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.2.14
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("boxService")
public class BoxServiceImpl extends BasicServiceImpl implements BoxService {

    @Autowired
    private RackDao rackDao;

    @Autowired
    private BoxDao boxDao;

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private InfrastructureDao infrastructureDao;

    @Autowired
    private BiobankDao biobankDao;


    public RackBox createRackBox(Long rackId, RackBox rackBox) {
        if (rackId == null) {
            logger.debug("RackId can't be null");
            return null;
        }
        if (rackBox == null) {
            logger.debug("rackBox can't be null");
            return null;
        }

        Rack rackDB = rackDao.get(rackId);
        if (rackDB == null) {
            logger.debug("rackDB can't be null");
            return null;
        }

        rackBox.setRack(rackDB);
        boxDao.create(rackBox);

//        if(rackBox.getCapacity() != null){
//            for(int i = 0; i < rackBox.getCapacity(); i++){
//                Position position = new Position();
//                position.setBox(rackBox);
//                positionDao.create(position);
//            }
//        }
        return rackBox;
    }

    public StandaloneBox createStandaloneBox(Long infrastructureId, StandaloneBox standaloneBox) {
        if (infrastructureId == null) {
            logger.debug("infrastructureId can't be null");
            return null;
        }
        if (standaloneBox == null) {
            logger.debug("standaloneBox can't be null");
            return null;
        }

        Infrastructure infrastructureDB = infrastructureDao.get(infrastructureId);
        if (infrastructureDB == null) {
            logger.debug("infrastructureDB can't be null");
            return null;
        }

        standaloneBox.setInfrastructure(infrastructureDB);
        boxDao.create(standaloneBox);
        return standaloneBox;
    }

    public boolean remove(Long id) {
        Box boxDB = boxDao.get(id);
        if (boxDB == null) {
            logger.debug("Box doesn't exist");
            return true;
        }
        if (!boxDB.getPositions().isEmpty()) {
            for (Position position : boxDB.getPositions()) {
                positionDao.remove(position);
            }
        }
        boxDao.remove(boxDB);
        return true;
    }

    public Box update(Box box) {
        if (box == null) {
            logger.debug("Box can't be null");
            return null;
        }
        Box boxDB = boxDao.get(box.getId());

        if (boxDB == null) {
            logger.debug("BoxDB can't be null");
            return null;
        }

        if (box.getCapacity() != null) boxDB.setCapacity(box.getCapacity());
        if (box.getName() != null) boxDB.setName(box.getName());
        if (box.getTempMax() != null) boxDB.setTempMax(box.getTempMax());
        if (box.getTempMin() != null) boxDB.setTempMin(box.getTempMin());

        if (box.getPositions() != null) boxDB.setPositions(box.getPositions());

        boxDao.update(boxDB);
        return boxDB;
    }

    @Transactional(readOnly = true)
    public List<Box> all() {
        return boxDao.all();
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return boxDao.count();
    }

    @Transactional(readOnly = true)
    public Box get(Long id) {
        notNull(id);
        return boxDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Box> allOrderedBy(String orderByParam, boolean desc) {
        return boxDao.allOrderedBy(orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<Box> nOrderedBy(String orderByParam, boolean desc, int number) {
        return boxDao.nOrderedBy(orderByParam, desc, number);
    }

    public List<RackBox> getSortedRackBoxes(Long rackId, String orderByParam, boolean desc) {
        if (rackId == null) {
            logger.debug("rackId is null");
            return null;
        }

        Rack rackDB = rackDao.get(rackId);
        if (rackDB == null) {
            logger.debug("rackDB can´t be null");
            return null;
        }

        return boxDao.getSorted(rackDB, orderByParam, desc);
    }

    public List<StandaloneBox> getSortedStandAloneBoxes(Long biobankId, String orderByParam, boolean desc) {
        if (biobankId == null) {
            logger.debug("biobankId is null");
            return null;
        }

        Biobank biobankDB = biobankDao.get(biobankId);
        if (biobankDB == null) {
            logger.debug("BiobankDB can´t be null");
            return null;
        }

        return boxDao.getSorted(biobankDB, orderByParam, desc);
    }

}
