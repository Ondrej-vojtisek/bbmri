package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.infrastructure.Infrastructure;

/**
 * Interface to handle instances of Infrastructure stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface InfrastructureDao extends BasicDao<Infrastructure> {

    // No other methods needed here

}
