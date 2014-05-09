package cz.bbmri.service.impl;

import cz.bbmri.dao.ContainerDao;
import cz.bbmri.dao.MonitoringDao;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.monitoring.Monitoring;
import cz.bbmri.service.MonitoringService;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Transactional
@Service("monitoringService")
public class MonitoringServiceImpl extends BasicServiceImpl implements MonitoringService {

    @Autowired
    private MonitoringDao monitoringDao;

    @Autowired
    private ContainerDao containerDao;

    public Monitoring get(Long id) {
        if (isNull(id, "id", null)) return null;

        Monitoring monitoringDB = monitoringDao.get(id);
        if (isNull(monitoringDB, "monitoringDB", null)) return null;

        return monitoringDB;
    }

    public boolean create(Long containerId, Monitoring monitoring, ValidationErrors errors) {
        notNull(errors);

        if (isNull(monitoring, "monitoring", errors)) return false;

        if (isNull(containerId, "containerId", errors)) return false;

        Container containerDB = containerDao.get(containerId);
        if (isNull(containerDB, "containerDB", errors)) return false;

        // TODO check duplicity - there can't be two same monitorings with the same type and name
        monitoring.setContainer(containerDB);

        monitoringDao.create(monitoring);

        return true;
    }

}
