package cz.bbmri.dao;

import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface RackDao extends BasicBiobankDao<Rack>  {

    Rack getByName(Container container, String name);
}
