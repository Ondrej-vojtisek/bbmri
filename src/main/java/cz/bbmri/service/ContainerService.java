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
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface ContainerService extends Get<Container>, Update<Container>, Remove {

    List<Container> getSortedContainers(Long biobankId, String orderByParam, boolean desc);

    boolean create(Long infrastructureId, Container container, ValidationErrors errors);

    Container create(Long infrastructureId, Container container) throws DuplicitEntityException;

    Container getContainerByName(Biobank biobank, String name);

}
