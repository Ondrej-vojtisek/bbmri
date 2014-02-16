package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Container;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public interface ContainerService extends BasicService<Container>{

    Container create(Long infrastructureId, Container container);

    Container eagerGet(Long containerId, boolean rack);

}
