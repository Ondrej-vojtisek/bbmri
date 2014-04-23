package cz.bbmri.service;

import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.service.simpleService.Get;

/**
 * API for handling BiobankAdministrators
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface BiobankAdministratorService extends PermissionService<BiobankAdministrator>, Get<BiobankAdministrator> {



}
