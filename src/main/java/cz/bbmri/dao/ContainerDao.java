package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.infrastructure.Container;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public interface ContainerDao extends BasicBiobankDao<Container>  {

    Container getByName(Biobank biobank, String name);
}
