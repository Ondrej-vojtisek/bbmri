package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.service.exceptions.DuplicitEntityException;
import cz.bbmri.service.simpleService.Get;
import cz.bbmri.service.simpleService.Remove;
import cz.bbmri.service.simpleService.Update;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface RackService extends Get<Rack>, Update<Rack>, Remove {

    boolean create(Long containerId, Rack rack, ValidationErrors errors);

    List<Rack> getSortedRacks(Long biobankId, String orderByParam, boolean desc);

    Rack create(Long containerId, Rack rack) throws DuplicitEntityException;

    Rack getRackByName(Container container, String name);

}
