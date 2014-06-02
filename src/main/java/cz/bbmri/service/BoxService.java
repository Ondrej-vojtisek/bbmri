package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.infrastructure.RackBox;
import cz.bbmri.entities.infrastructure.StandaloneBox;
import cz.bbmri.service.exceptions.DuplicitEntityException;
import cz.bbmri.service.simpleService.Get;
import cz.bbmri.service.simpleService.Remove;
import cz.bbmri.service.simpleService.Update;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * API for handling Boxes (both specialized types)
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface BoxService extends Remove, Update<Box>, Get<Box> {

    /**
     * Store instance of rackBox in DB. Method for manual usage from webpages.
     *
     * @param rackId - ID of rack where the rackBox is located
     * @param box    - new instance of rackBox
     * @param errors - in case of error, error messages will be stored into errors
     * @param loggedUserId - id of user who initiated event
     * @return true/false
     */
    boolean createRackBox(Long rackId, RackBox box, ValidationErrors errors, Long loggedUserId);

    /**
     * Store instance of StandaloneBox in DB. Method for manual usage from webpages.
     *
     * @param infrastructureId - ID of infrastructure where the standalone box is located
     * @param box              - new instance of standalone box
     * @param errors           - in case of error, error messages will be stored into errors
     * @param loggedUserId     - id of user who initiated event
     * @return true/false
     */
    boolean createStandaloneBox(Long infrastructureId, StandaloneBox box, ValidationErrors errors, Long loggedUserId);

    /**
     * Store instance of rackBox in DB. Method without error notifications - it is suitable for automatized rackBox
     * creation triggered from xml parsers.
     *
     * @param rackId  - ID of rack where rackBox is located
     * @param rackBox - new instance of rackBox
     * @return new instance or null
     * @throws DuplicitEntityException
     */
    RackBox createRackBox(Long rackId, RackBox rackBox) throws DuplicitEntityException;

    /**
     * Store instance of StandaloneBox in DB. Method without error notifications - it is suitable for automatized
     * standalone box creation triggered from xml parsers
     *
     * @param infrastructureId - ID of infrastructure where the standalone box is located
     * @param standaloneBox    - new instance of standalone box
     * @return new instance or null
     * @throws DuplicitEntityException
     */
    StandaloneBox createStandaloneBox(Long infrastructureId, StandaloneBox standaloneBox) throws DuplicitEntityException;

    /**
     * Return sorted list of rackboxes.
     *
     * @param rackId       - ID of rack. All boxes of the rack will be returned in list
     * @param orderByParam
     * @param desc
     * @return sorted list of rackBoxes
     */
    List<RackBox> getSortedRackBoxes(Long rackId, String orderByParam, boolean desc);

    /**
     * Sorted list of standalone boxes.
     *
     * @param biobankId    - Return all standalone boxes of biobank identifier by biobankId
     * @param orderByParam
     * @param desc
     * @return sorted list of standalone boxes
     */
    List<StandaloneBox> getSortedStandAloneBoxes(Long biobankId, String orderByParam, boolean desc);

    /**
     * Return box by its name. If biobank is given as param (biobank is not null) than standalone box is searched.
     * If rack is given than rackBox is searched. If both biobank and rack is null than null is returned.
     * Name must be unique in context of its hierarchical predecessor.
     *
     * @param biobank - instance of biobank if standalone box is searched
     * @param rack    - instance of rack if rackBox is searched
     * @param name    - name of box
     * @return Box with given name or null
     */
    Box getBoxByName(Biobank biobank, Rack rack, String name);
}
