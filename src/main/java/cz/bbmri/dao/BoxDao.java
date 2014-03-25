package cz.bbmri.dao;

import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.2.14
 * Time: 10:19
 * To change this template use File | Settings | File Templates.
 */
public interface BoxDao extends BasicDao<Box, Long> {

    List<StandaloneBox> getSorted(Biobank biobank, String orderByParam, boolean desc);

    List<RackBox> getSorted(Rack rack, String orderByParam, boolean desc);

    Box getByName(Biobank biobank, Rack rack, String name);
}
