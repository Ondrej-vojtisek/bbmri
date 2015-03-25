package cz.bbmri.dao;

import cz.bbmri.entity.Shibboleth;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface ShibbolethDAO extends AbstractDAO<Shibboleth, Long> {

    // TODO doxygen
    Shibboleth get(String eppn, String targeted, String persitent);
}
