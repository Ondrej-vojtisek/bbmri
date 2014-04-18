package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Request;

/**
 * Interface to handle instances of Request stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface RequestDao extends BasicDao<Request> {

     // No other methods needed here

}
