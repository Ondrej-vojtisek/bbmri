package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Shibboleth;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface ShibbolethDao extends BasicDao<Shibboleth> {

    // TODO doxygen
    Shibboleth get(String eppn, String targetedId, String persitentId);
}
