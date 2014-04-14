package cz.bbmri.service;

import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.enumeration.Permission;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.9.13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankAdministratorService {

    boolean hasPermission(Permission permission, Long objectId, Long userId);

    boolean changeAdministratorPermission(Long objectAdministratorId, Permission permission, ValidationErrors errors,
                                          Long loggedUserId);

    boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId);

    boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors,
                                Long loggedUserId);

    BiobankAdministrator get(Long biobankId, Long userId);

    boolean contains(Long biobankId, Long userId);

    boolean hasSameOrHigherPermission(Long userId, Permission permission);

    BiobankAdministrator get(Long id);
}
