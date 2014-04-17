package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.infrastructure.RackBox;
import cz.bbmri.entities.infrastructure.StandaloneBox;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface BoxDao extends BasicDao<Box> {

    List<StandaloneBox> getSorted(Biobank biobank, String orderByParam, boolean desc);

    List<RackBox> getSorted(Rack rack, String orderByParam, boolean desc);

    Box getByName(Biobank biobank, Rack rack, String name);
}
