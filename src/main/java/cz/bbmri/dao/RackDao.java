package cz.bbmri.dao;

import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;

/**
 * Interface to handle instances of Rack stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface RackDao extends BasicBiobankDao<Rack>  {

    /**
     * Find Rack by its name. Rack name is unique within Container.
     *
     * @param container
     * @param name
     * @return Rack with given name located in Container or null
     */
    Rack getByName(Container container, String name);
}
