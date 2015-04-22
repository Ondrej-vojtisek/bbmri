package cz.bbmri.dao;

import cz.bbmri.entity.Biobank;

/**
 * Interface to handle instances of Biobank stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface BiobankDAO extends AbstractDAO<Biobank, Integer> {

    Biobank getByInstitutionalId(String id);

}
