package bbmri.service;

import bbmri.entities.BiobankAdministrator;
import bbmri.entities.enumeration.Permission;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.9.13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public interface BiobankAdministratorService extends BasicService<BiobankAdministrator> {

    BiobankAdministrator get(Long biobankId, Long userId);

    boolean contains(Long biobankId, Long userId);

    boolean hasSameOrHigherPermission(Long userId, Permission permission);

}
