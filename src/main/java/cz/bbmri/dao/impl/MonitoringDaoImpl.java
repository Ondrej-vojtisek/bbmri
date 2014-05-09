package cz.bbmri.dao.impl;

import cz.bbmri.dao.MonitoringDao;
import cz.bbmri.entities.infrastructure.monitoring.Monitoring;
import org.springframework.stereotype.Repository;

/**
 * Implementation for interface handling instances of Monitoring.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository
public class MonitoringDaoImpl extends BasicDaoImpl<Monitoring> implements MonitoringDao {
}
