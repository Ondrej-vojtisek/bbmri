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
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    public boolean create(Long infrastructureId, Container container, ValidationErrors errors) {
        notNull(errors);

        if (create(infrastructureId, container) == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.containercreatefailed"));
            return false;
        }
        return true;

    }

    public Container create(Long infrastructureId, Container container) {
        if (isNull(infrastructureId, "infrastructureId", null)) return null;
        if (isNull(container, "container", null)) return null;

        Infrastructure infrastructureDB = infrastructureDao.get(infrastructureId);
        if (isNull(infrastructureDB, "infrastructureDB", null)) return null;

        container.setInfrastructure(infrastructureDB);
        containerDao.create(container);
        try {
            containerDao.create(container);
        } catch (DataAccessException ex) {
            operationFailed(null, ex);
            return null;
        }
        return container;
    }

    public boolean remove(Long id) {
        if (isNull(id, "id", null)) return false;
        Container containerDB = containerDao.get(id);
        if (isNull(containerDB, "containerDB", null)) return false;

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
        if (isNull(container, "container", null)) return null;
        Container containerDB = containerDao.get(container.getId());
        if (isNull(containerDB, "containerDB", null)) return null;

        if (container.getLocation() != null) containerDB.setLocation(container.getLocation());
        if (container.getName() != null) containerDB.setName(container.getName());
        if (container.getCapacity() != null) containerDB.setCapacity(container.getCapacity());
        if (container.getTempMax() != null) containerDB.setTempMax(container.getTempMax());
        if (container.getTempMin() != null) containerDB.setTempMin(container.getTempMin());

        containerDao.update(containerDB);
        return containerDB;
    }

    @Transactional(readOnly = true)
    public Container get(Long id) {
        if (isNull(id, "id", null)) return null;
        return containerDao.get(id);
    }

    @Transactional(readOnly = true)
    public List<Container> getSortedContainers(Long biobankId, String orderByParam, boolean desc) {
        if (isNull(biobankId, "biobankId", null)) return null;
        Biobank biobankDB = biobankDao.get(biobankId);
        if (isNull(biobankDB, "biobankDB", null)) return null;

        return containerDao.getSorted(biobankDB, orderByParam, desc);
    }

    @Transactional(readOnly = true)
    public Container getContainerByName(Biobank biobank, String name) {
        return containerDao.getByName(biobank, name);
    }


}
