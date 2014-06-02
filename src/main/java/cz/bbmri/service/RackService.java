package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.service.exceptions.DuplicitEntityException;
import cz.bbmri.service.simpleService.Get;
import cz.bbmri.service.simpleService.Remove;
import cz.bbmri.service.simpleService.Update;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * API for handling Racks
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface RackService extends Get<Rack>, Update<Rack>, Remove {

    /**
     * Store instance of rack in DB
     *
     * @param containerId  - ID or existing container where rack is located
     * @param rack         - new instance of rack
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - id of user who initiated event
     * @return true/false
     */
    boolean create(Long containerId, Rack rack, ValidationErrors errors, Long loggedUserId);

    /**
     * Store instance of rack in DB. Method without error notifications - it is suitable for automatized
     * creation triggered from xml parsers.
     *
     * @param containerId - - ID or existing container where rack is located
     * @param rack        - new instance of rack
     * @return instance of rack
     * @throws DuplicitEntityException
     */
    Rack create(Long containerId, Rack rack) throws DuplicitEntityException;

    /**
     * Search container for a rack with given name
     *
     * @param container - where to find rack
     * @param name      - name of rack
     * @return rack with given name or null
     */
    Rack getRackByName(Container container, String name);

}
