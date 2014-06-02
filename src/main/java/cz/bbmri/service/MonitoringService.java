package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.monitoring.Monitoring;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * API for handling monitoring
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface MonitoringService extends Get<Monitoring> {

    /**
     * Store new instance of monitoring in DB.
     *
     * @param containerId  - ID of container which will be monitored
     * @param monitoring   - new instance of monitoring
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - id of user who initiated the event
     * @return true/false
     */
    boolean create(Long containerId, Monitoring monitoring, ValidationErrors errors, Long loggedUserId);

}
