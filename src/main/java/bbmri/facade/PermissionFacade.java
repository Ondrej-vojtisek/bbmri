package bbmri.facade;

import bbmri.entities.enumeration.Permission;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 10.12.13
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */
public interface PermissionFacade {

    boolean hasPermission(Permission permission, Long objectId, Long userId);

    boolean changeAdministratorPermission(Long objectAdministratorId, Permission permission, ValidationErrors errors);

    boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors);

    boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors);
}
