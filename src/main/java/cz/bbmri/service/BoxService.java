package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.infrastructure.RackBox;
import cz.bbmri.entities.infrastructure.StandaloneBox;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 15.2.14
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public interface BoxService extends BasicService<Box> {

    RackBox createRackBox(Long rackId, RackBox rackBox);

    StandaloneBox createStandaloneBox(Long infrastructureId, StandaloneBox standaloneBoxBox);

    List<RackBox> getSortedRackBoxes(Long rackId, String orderByParam, boolean desc);

    List<StandaloneBox> getSortedStandAloneBoxes(Long biobankId, String orderByParam, boolean desc);
}
