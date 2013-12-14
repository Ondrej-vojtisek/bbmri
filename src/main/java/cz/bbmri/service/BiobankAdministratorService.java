package cz.bbmri.service;

import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.enumeration.Permission;

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
