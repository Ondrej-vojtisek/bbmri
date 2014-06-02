package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.BoxDao;
import cz.bbmri.dao.ContainerDao;
import cz.bbmri.dao.RackDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.infrastructure.RackBox;
import cz.bbmri.service.RackService;
import cz.bbmri.service.exceptions.DuplicitEntityException;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
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

    @Autowired
    private BiobankDao biobankDao;

    //  errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.rackcreatefailed"));
    public boolean create(Long containerId, Rack rack, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(containerId, "containerId", errors)) return false;
        if (isNull(rack, "rack", errors)) return false;

        try {
            create(containerId, rack);
        } catch (DuplicitEntityException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.impl.BasicServiceImpl.duplicateEntity"));
            return false;
        }

        // Archive message
        Container containerDB = containerDao.get(containerId);
        archive("New Rack with name: " + rack.getName() + "was created in biobank: " +
                containerDB.getInfrastructure().getBiobank().getAbbreviation(),
                loggedUserId);

        return true;

    }

    // method for automatized create not triggered from frontend
    public Rack create(Long containerId, Rack rack) throws DuplicitEntityException {

        if (isNull(containerId, "containerId", null)) return null;
        if (isNull(rack, "rack", null)) return null;

        Container containerDB = containerDao.get(containerId);
        if (isNull(containerDB, "containerDB", null)) return null;

        // Rack with the same name already exists in container
        if (rackDao.getByName(containerDB, rack.getName()) != null) {
            throw new DuplicitEntityException("Rack with name: " + rack.getName() + " already exists in container: " +
                    containerDB);
        }

        rack.setContainer(containerDB);
        rackDao.create(rack);
        return rack;
    }

    public boolean remove(Long id) {
        if (isNull(id, "id", null)) return false;
        Rack rackDB = rackDao.get(id);
        if (isNull(rackDB, "rackDB", null)) return false;

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
        if (isNull(rack, "rack", null)) return null;

        Rack rackDB = rackDao.get(rack.getId());
        if (isNull(rackDB, "rackDB", null)) return null;

        if (rack.getCapacity() != null) rackDB.setCapacity(rack.getCapacity());
        if (rack.getName() != null) rackDB.setName(rack.getName());

        rackDao.update(rackDB);
        return rackDB;
    }

    @Transactional(readOnly = true)
    public Rack get(Long id) {
        if (isNull(id, "id", null)) return null;
        return rackDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Rack> getSortedRacks(Long biobankId, String orderByParam, boolean desc) {
        if (isNull(biobankId, "biobankId", null)) return null;

        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        return rackDao.getSorted(biobankDB, orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public Rack getRackByName(Container container, String name) {
        if (isNull(container, "container", null)) return null;
        if (isNull(name, "name", null)) return null;

        return rackDao.getByName(container, name);
    }

}
