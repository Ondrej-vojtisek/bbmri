package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Rack;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */
public interface RackService extends BasicService<Rack>{

    Rack eagerGet(Long rackId, boolean box);

    Rack create(Long containerId, Rack rack);
}
