package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.infrastructure.RackBox;
import cz.bbmri.entities.infrastructure.StandaloneBox;

import java.util.List;

/**
 * Interface to handle instances of Box stored in database. Also all extended classes of box are handled by BoxDao.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface BoxDao extends BasicDao<Box> {

    /**
     * Return all instances of StandaloneBox associated with project ordered by given parameter. Desc param changes if it is ordered DESC or ASC
     *
     * @param biobank      - finds all boxes associated with this biobank
     * @param orderByParam - select column by which will the result be sorted
     * @param desc         - flag determining if order will be DESC (true) or default ASC (false)
     * @return sorted list of all standalone boxes associated with specified biobank
     */
    List<StandaloneBox> getSorted(Biobank biobank, String orderByParam, boolean desc);

    /**
     * Return all instances of RackBox associated with rack ordered by given parameter. Desc param changes if it is ordered DESC or ASC
     *
     * @param rack         - finds all boxes associated with this rack
     * @param orderByParam - select column by which will the result be sorted
     * @param desc         - flag determining if order will be DESC (true) or default ASC (false)
     * @return sorted list of all rack boxes associated with specified rack
     */
    List<RackBox> getSorted(Rack rack, String orderByParam, boolean desc);

    /**
     * Return box with unique given name of specified biobank or rack. Because box can be rackBox or standAlone box it
     * can be found by different "parent" entity. StandAlone box is located directly in biobank (or infrastructure to be
     * more specific) but rackBox is located in rack.
     *
     * @param biobank - if not null than standalone box will be searched
     * @param rack    - if not null than rackbox will be searched
     * @param name    - unique name of box
     * @return Box or null if not found
     */
    Box getByName(Biobank biobank, Rack rack, String name);
}
