package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Biobank;

/**
 * Interface to handle instances of Biobank stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface BiobankDao extends BasicDao<Biobank> {

    /**
     * Find biobank in db by its abbreviation.  Abbreviation is unique attribute of biobank.
     *
     * @param abbreviation - search parameter
     * @return biobank with given abbreviation or null
     */
    Biobank getBiobankByAbbreviation(String abbreviation);

}
