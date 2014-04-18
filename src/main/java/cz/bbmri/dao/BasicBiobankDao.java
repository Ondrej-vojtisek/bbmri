package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Biobank;

import java.util.List;

/**
 * Interface for all entities with relationship depending on biobank. There is typical use case to find all entities
 * asociated with one biobank and exactly that method is set by this interface.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

abstract interface BasicBiobankDao<T> extends BasicDao<T> {

    /**
     * Return all instances of Type T associated with biobank ordered by given parameter. Desc param changes if it is ordered DESC or ASC
     *
     * @param biobank      - finds all objects associated with this biobank
     * @param orderByParam - select column by which will the result be sorted
     * @param desc         - flag determining if order will be DESC (true) or default ASC (false)
     * @return sorted list of all T associated with specified biobank
     */
    List<T> getSorted(Biobank biobank, String orderByParam, boolean desc);
}
