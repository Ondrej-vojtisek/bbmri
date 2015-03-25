package cz.bbmri.dao.impl;

import cz.bbmri.dao.MonitoringDAO;
import cz.bbmri.entity.Monitoring;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for class handling instances of Monitoring.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("monitoringDAO")
@Transactional
public class MonitoringDAOImpl extends GenericDAOImpl<Monitoring> implements MonitoringDAO {

    public Monitoring get(Long id) {
             return (Monitoring) getCurrentSession().get(Monitoring.class, id);
         }

}
