package cz.bbmri.service;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.service.exceptions.DuplicitEntityException;
import cz.bbmri.service.simpleService.Get;
import cz.bbmri.service.simpleService.Remove;
import cz.bbmri.service.simpleService.Update;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public interface ContainerService extends Get<Container>, Update<Container>, Remove {

    List<Container> getSortedContainers(Long biobankId, String orderByParam, boolean desc);

    boolean create(Long infrastructureId, Container container, ValidationErrors errors);

    Container create(Long infrastructureId, Container container) throws DuplicitEntityException;

    Container getContainerByName(Biobank biobank, String name);

}
