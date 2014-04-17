package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface ContainerDao extends BasicBiobankDao<Container>  {

    Container getByName(Biobank biobank, String name);
}
