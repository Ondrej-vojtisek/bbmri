package cz.bbmri.service;

import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.11.13
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
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
