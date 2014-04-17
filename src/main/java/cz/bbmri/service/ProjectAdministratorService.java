package cz.bbmri.service;

import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface ProjectAdministratorService extends Get<ProjectAdministrator>{

    ProjectAdministrator get(Long projectId, Long userId);

    boolean contains(Long projectId, Long userId);

    boolean hasPermission(Permission permission, Long objectId, Long userId);

    boolean changeAdministratorPermission(Long objectAdministratorId, Permission permission, ValidationErrors errors,
                                          Long loggedUserId);

    boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId);

    boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors,
                                Long loggedUserId);
}
