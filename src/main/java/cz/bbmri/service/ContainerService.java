package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Container;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public interface ContainerService extends BasicService<Container>{

    Container create(Long infrastructureId, Container container);

    List<Container> getSortedContainers(Long biobankId, String orderByParam, boolean desc);

}
