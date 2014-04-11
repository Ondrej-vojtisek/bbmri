package cz.bbmri.facade;

import cz.bbmri.entities.enumeration.Permission;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 10.12.13
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public interface PermissionFacade {

    boolean hasPermission(Permission permission, Long objectId, Long userId);

    boolean changeAdministratorPermission(Long objectAdministratorId, Permission permission, ValidationErrors errors,
                                          Long loggedUserId);

    boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId);

    boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors,
                                Long loggedUserId);
}
