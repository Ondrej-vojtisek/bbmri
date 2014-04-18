package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Module;

/**
 * Interface to handle instances of Module (both LTS and STS) stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface ModuleDao extends BasicDao<Module> {

     // No other methods needed here

}
