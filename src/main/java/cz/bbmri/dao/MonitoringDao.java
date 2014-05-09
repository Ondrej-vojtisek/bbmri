package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.infrastructure.monitoring.Monitoring;

/**
 * Interface to handle instances of Monitoring stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface MonitoringDao extends BasicDao<Monitoring> {
}
