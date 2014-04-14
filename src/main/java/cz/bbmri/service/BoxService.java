package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.*;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.2.14
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public interface BoxService {

    boolean remove(Long id);

    Box update(Box box);

    Box get(Long id);

    boolean createRackBox(Long rackId, RackBox box, ValidationErrors errors);

    boolean createStandaloneBox(Long infrastructureId, StandaloneBox box, ValidationErrors errors);

    RackBox createRackBox(Long rackId, RackBox rackBox);

    StandaloneBox createStandaloneBox(Long infrastructureId, StandaloneBox standaloneBox);

    List<RackBox> getSortedRackBoxes(Long rackId, String orderByParam, boolean desc);

    List<StandaloneBox> getSortedStandAloneBoxes(Long biobankId, String orderByParam, boolean desc);

    Box getBoxByName(Biobank biobank, Rack rack, String name);
}
