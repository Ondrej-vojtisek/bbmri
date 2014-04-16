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
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.2.14
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public interface BoxService extends Remove, Update<Box>, Get<Box> {

    boolean createRackBox(Long rackId, RackBox box, ValidationErrors errors);

    boolean createStandaloneBox(Long infrastructureId, StandaloneBox box, ValidationErrors errors);

    RackBox createRackBox(Long rackId, RackBox rackBox) throws DuplicitEntityException;

    StandaloneBox createStandaloneBox(Long infrastructureId, StandaloneBox standaloneBox) throws DuplicitEntityException;

    List<RackBox> getSortedRackBoxes(Long rackId, String orderByParam, boolean desc);

    List<StandaloneBox> getSortedStandAloneBoxes(Long biobankId, String orderByParam, boolean desc);

    Box getBoxByName(Biobank biobank, Rack rack, String name);
}
