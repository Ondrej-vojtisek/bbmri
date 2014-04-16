package cz.bbmri.service.impl;

import cz.bbmri.dao.BoxDao;
import cz.bbmri.dao.PositionDao;
import cz.bbmri.dao.SampleDao;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Position;
import cz.bbmri.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.3.14
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("positionService")
public class PositionServiceImpl extends BasicServiceImpl implements PositionService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private BoxDao boxDao;

    @Autowired
    private SampleDao sampleDao;

    public Position getByCoordinates(Box box, Integer seqPosition, Integer column, Integer row) {
        return positionDao.getByCoordinates(box, seqPosition, column, row);
    }

    public Position create(Position position, Long boxId, Long sampleId) {
        if (isNull(position, "position", null)) return null;
        if (isNull(boxId, "boxId", null)) return null;
        if (isNull(sampleId, "sampleId", null)) return null;

        Box boxDB = boxDao.get(boxId);

        if (isNull(boxDB, "boxDB", null)) return null;

        Sample sampleDB = sampleDao.get(sampleId);

        if (isNull(sampleDB, "sampleDB", null)) return null;

        position.setBox(boxDB);
        position.setSample(sampleDB);
        positionDao.create(position);

        return position;
    }

    public Position update(Position position) {
        if (isNull(position, "position", null)) return null;
        Position positionDB = positionDao.get(position.getId());

        if (isNull(positionDB, "positionDB", null)) return null;

        if (position.getSample() != null) {
            if (!position.getSample().equals(positionDB.getSample()))
                positionDB.setSample(position.getSample());
        }

        positionDao.update(positionDB);
        return positionDB;
    }
}
