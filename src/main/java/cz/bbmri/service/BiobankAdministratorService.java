package cz.bbmri.service;

import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface BiobankAdministratorService extends Get<BiobankAdministrator> {

    boolean hasPermission(Permission permission, Long objectId, Long userId);

    boolean changeAdministratorPermission(Long objectAdministratorId, Permission permission, ValidationErrors errors,
                                          Long loggedUserId);

    boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId);

    boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors,
                                Long loggedUserId);

    BiobankAdministrator get(Long biobankId, Long userId);

    boolean contains(Long biobankId, Long userId);

    boolean hasSameOrHigherPermission(Long userId, Permission permission);

}
