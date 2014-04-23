package cz.bbmri.service;

import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.service.simpleService.Get;

/**
 * API for handling ProjectAdministrators
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface ProjectAdministratorService extends PermissionService, Get<ProjectAdministrator>{

    // nothing needed here

}
