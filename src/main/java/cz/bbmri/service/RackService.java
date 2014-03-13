package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Rack;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public interface RackService extends BasicService<Rack>{

    List<Rack> getSortedRacks(Long biobankId, String orderByParam, boolean desc);

    Rack create(Long containerId, Rack rack);
}
