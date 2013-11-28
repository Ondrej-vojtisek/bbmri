package bbmri.service;

import bbmri.entities.User;
import bbmri.entities.enumeration.Permission;
import bbmri.service.exceptions.LastManagerException;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 19.11.13
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public interface PermissionService<O, P> {

    void changeAdministratorPermission(P objectAdministrator, Permission permission) throws LastManagerException;

    void assignAdministrator(O object, Long userId, Permission permission);

    void removeAdministrator(P objectAdministrator) throws LastManagerException;
}
