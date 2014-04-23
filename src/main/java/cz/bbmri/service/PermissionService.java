package cz.bbmri.service;

import cz.bbmri.entities.enumeration.Permission;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * Shared interface between ProjectAdministratorService and BiobankAdministratorService
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface PermissionService<T> {

    /**
     * Return relationship between User and Project or Biobank (by type T).
     *
     * @param objectId - ID of biobank of project
     * @param userId   - ID of user
     * @return instance of T or null
     */
    T get(Long objectId, Long userId);

    /**
     * Check whether user (defined by id) has permission to given object (project or biobank)
     *
     * @param permission
     * @param objectId   - ID of biobank or project
     * @param userId     - ID of User
     * @return true if the relationship exists or false if not
     */
    boolean hasPermission(Permission permission, Long objectId, Long userId);

    /**
     * Change permission of existing relationship of type T. Relationship is defined by ID.
     *
     * @param objectAdministratorId - ID of relastionship
     * @param permission            - new permission
     * @param errors                - in case of error, error messages will be stored into errors
     * @param loggedUserId          - ID of event initiator. Notification about new file will be sent to project team except
     *                              initiator (BROADCAST)
     * @return true/false
     */
    boolean changeAdministratorPermission(Long objectAdministratorId, Permission permission, ValidationErrors errors,
                                          Long loggedUserId);


    /**
     * Remove relationship of type T identified by its ID.
     *
     * @param objectAdministratorId - ID of relationship
     * @param errors                - in case of error, error messages will be stored into errors
     * @param loggedUserId          - ID of event initiator. Notification about new file will be sent to project team except
     *                              initiator (BROADCAST)
     * @return true/false
     */
    boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId);

    /**
     * Create new relationship of type T. Add new administrator of biobank or project. New administrator is created
     * with given permission.
     *
     * @param objectId           - ID of biobank/project
     * @param newAdministratorId - ID of user - new administrator
     * @param permission         - permission which will be set to new administrator
     * @param errors             - in case of error, error messages will be stored into errors
     * @param loggedUserId       - ID of event initiator. Notification about new file will be sent to project team except
     *                           initiator (BROADCAST)
     * @return true/false
     */
    boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors,
                                Long loggedUserId);

    /**
     * Check if there exists relationship between user and given object - both identified by IDs.
     * True is returned if the relationship exists (with any permission).
     *
     * @param objectId - ID of object (project or biobank)
     * @param userId   - ID of user
     * @return true if relationship exists,  false otherwise
     */
    boolean contains(Long objectId, Long userId);

}
