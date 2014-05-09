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
 * API for handling Containers
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface ContainerService extends Get<Container>, Update<Container>, Remove {

    /**
     * Sorted list of all containers located in specified biobank
     *
     * @param biobankId    - ID of biobank
     * @param orderByParam
     * @param desc
     * @return sorted list of containers
     */
    List<Container> getSortedContainers(Long biobankId, String orderByParam, boolean desc);

    /**
     * Store new instance of container in DB. Method from manual usage from webpages
     *
     * @param infrastructureId - ID of infrastructure in which the container will be placed
     * @param container        - new instance of container
     * @param errors           - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean create(Long infrastructureId, Container container, ValidationErrors errors);

    /**
     * Store new instance of container in DB. Method without error notifications - it is suitable for automatized
     * creation triggered from xml parsers.
     *
     * @param infrastructureId - ID of infrastructure where container will be placed
     * @param container        - new instance of Container
     * @return new instance or null
     * @throws DuplicitEntityException
     */
    Container create(Long infrastructureId, Container container) throws DuplicitEntityException;

    /**
     * Return container by its name.
     * Name must be unique in context of infrastructure (biobank).
     *
     * @param biobank - instance of biobank where container is searched
     * @param name    - name of container
     * @return container with given name or null
     */
    Container getContainerByName(Biobank biobank, String name);

    /**
     * Return all monitored containers of given biobank
     *
     * @param biobank - instace of biobank where containers are searched
     * @return list of containers with monitoring
     */
    List<Container> getMonitoredContainers(Biobank biobank);

}
