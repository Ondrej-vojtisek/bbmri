package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;

/**
 * Interface to handle instances of Container stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface ContainerDao extends BasicBiobankDao<Container> {

    /**
     * Return Container specified by its name and biobank when it is located
     *
     * @param biobank - where the container is located
     * @param name    - name of container
     * @return Container with given name or null
     */
    Container getByName(Biobank biobank, String name);
}
