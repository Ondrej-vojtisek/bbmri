package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.*;
import cz.bbmri.service.BoxService;
import cz.bbmri.service.exceptions.DuplicitEntityException;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
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

    // method for automatized imports - so there are no errors
    public RackBox createRackBox(Long rackId, RackBox rackBox) throws DuplicitEntityException {
        if (isNull(rackId, "rackId", null)) return null;
        if (isNull(rackBox, "box", null)) return null;

        Rack rackDB = rackDao.get(rackId);
        if (isNull(rackDB, "rackDB", null)) return null;
        rackBox.setRack(rackDB);

        // Box with the same name already exists in biobank
        if (boxDao.getByName(null, rackDB, rackBox.getName()) != null) {
            throw new DuplicitEntityException("Box with name: " + rackBox.getName() + " already exists in rack: " +
                    rackDB.getName());
        }

        try {
            boxDao.create(rackBox);
        } catch (DataAccessException ex) {
            operationFailed(null, ex);
            return null;
        }
        return rackBox;
    }

    public boolean createRackBox(Long rackId, RackBox box, ValidationErrors errors) {
        notNull(errors);

        RackBox newBox;

        try {
            newBox = createRackBox(rackId, box);
        } catch (DuplicitEntityException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.impl.BasicServiceImpl.duplicateEntity"));
            return false;
        }

        if (newBox == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.boxcreatefailed"));
            return false;
        }

        return true;
    }

    // method for automatized imports - so there are no errors
    public StandaloneBox createStandaloneBox(Long infrastructureId, StandaloneBox standaloneBox) throws DuplicitEntityException {
        if (isNull(infrastructureId, "infrastructureId", null)) return null;
        if (isNull(standaloneBox, "standaloneBox", null)) return null;

        Infrastructure infrastructureDB = infrastructureDao.get(infrastructureId);
        if (isNull(infrastructureDB, "infrastructureDB", null)) return null;
        standaloneBox.setInfrastructure(infrastructureDB);

        // Box with the same name already exists in biobank
        if (boxDao.getByName(infrastructureDB.getBiobank(), null,  standaloneBox.getName()) != null) {
            throw new DuplicitEntityException("Box with name: " + standaloneBox.getName() + " already exists in biobank: " +
                    infrastructureDB.getBiobank().getAbbreviation());
        }

        try {
            boxDao.create(standaloneBox);
        } catch (DataAccessException ex) {
            operationFailed(null, ex);
            return null;
        }
        return standaloneBox;
    }

    public boolean createStandaloneBox(Long infrastructureId, StandaloneBox box, ValidationErrors errors) {
        notNull(errors);

        StandaloneBox newBox;

        try {
            newBox = createStandaloneBox(infrastructureId, box);
        } catch (DuplicitEntityException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.impl.BasicServiceImpl.duplicateEntity"));
            return false;
        }

        if (newBox == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.boxcreatefailed"));
            return false;
        }
        return true;
    }

    public boolean remove(Long id) {
        if (isNull(id, "id", null)) return false;

        Box boxDB = boxDao.get(id);
        if (isNull(boxDB, "boxDB", null)) return false;

        if (!boxDB.getPositions().isEmpty()) {
            // remove all positions
            for (Position position : boxDB.getPositions()) {
                positionDao.remove(position);
            }
        }
        boxDao.remove(boxDB);
        return true;
    }

    public Box update(Box box) {
        if (isNull(box, "box", null)) return null;

        Box boxDB = boxDao.get(box.getId());

        if (isNull(boxDB, "boxDB", null)) return null;

        if (box.getCapacity() != null) boxDB.setCapacity(box.getCapacity());
        if (box.getName() != null) boxDB.setName(box.getName());
        if (box.getTempMax() != null) boxDB.setTempMax(box.getTempMax());
        if (box.getTempMin() != null) boxDB.setTempMin(box.getTempMin());

        boxDao.update(boxDB);
        return boxDB;
    }

    @Transactional(readOnly = true)
    public Box get(Long id) {
        if (isNull(id, "id", null)) return null;
        return boxDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<RackBox> getSortedRackBoxes(Long rackId, String orderByParam, boolean desc) {
        if (isNull(rackId, "rackId", null)) return null;

        Rack rackDB = rackDao.get(rackId);
        if (isNull(rackDB, "rackDB", null)) return null;

        return boxDao.getSorted(rackDB, orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public List<StandaloneBox> getSortedStandAloneBoxes(Long biobankId, String orderByParam, boolean desc) {
        if (isNull(biobankId, "biobankId", null)) return null;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        return boxDao.getSorted(biobankDB, orderByParam, desc);
    }

    @Override
    public Box getBoxByName(Biobank biobank, Rack rack, String name) {
        return boxDao.getByName(biobank, rack, name);
    }
}
