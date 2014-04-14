package cz.bbmri.service.impl;

import cz.bbmri.dao.BiobankAdministratorDao;
import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.NotificationDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.BiobankAdministratorService;
import cz.bbmri.service.exceptions.LastManagerException;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.9.13
 * Time: 11:55
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("biobankAdministratorService")
public class BiobankAdministratorServiceImpl extends BasicServiceImpl implements BiobankAdministratorService {

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotificationDao notificationDao;

    @Transactional(readOnly = true)
    public BiobankAdministrator get(Long id) {
        notNull(id);
        return biobankAdministratorDao.get(id);
    }

    public boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(objectAdministratorId, "objectAdministratorId", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        BiobankAdministrator ba = biobankAdministratorDao.get(objectAdministratorId);
        if (isNull(ba, "ba", errors)) return false;

        boolean result = false;

        try {
            result = remove(ba);
        } catch (LastManagerException ex) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastBiobankManagerException"));
            return false;
        } catch (Exception ex) {
            operationFailed(errors, ex);
            return false;
        }

        if (result) {
            Biobank biobank = ba.getBiobank();

            User user = ba.getUser();

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.adminDeleted",
                    user.getWholeName(), biobank.getAbbreviation());

            notificationDao.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                    NotificationType.BIOBANK_ADMINISTRATOR, locMsg, biobank.getId());
        }

        return result;
    }

    private boolean remove(BiobankAdministrator objectAdministrator) throws LastManagerException {
        notNull(objectAdministrator);

        User userDB = objectAdministrator.getUser();
        Biobank biobankDB = objectAdministrator.getBiobank();

        if (userDB == null || biobankDB == null) {
            logger.debug("Object retrieved from database is null - userBD or biobankDB");
            return false;
        }

              /* Situation when we want to remove last manager. */
        if (isLastManager(objectAdministrator)) {
            throw new LastManagerException("User: " + userDB.getWholeName()
                    + " is the only administrator with MANAGER permission associated to biobank: "
                    + biobankDB.getName() + ". He can't be removed!");
        }

        if (userDB.getBiobankAdministrators().size() == 1 &&
                userDB.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR)) {
            userDB.getSystemRoles().remove(SystemRole.BIOBANK_OPERATOR);
            userDao.update(userDB);
        }

        biobankAdministratorDao.remove(objectAdministrator);
        return true;
    }


    public boolean changeAdministratorPermission(Long objectAdministratorId,
                                                 Permission permission,
                                                 ValidationErrors errors, Long loggedUserId) {

        notNull(errors);

        if (isNull(permission, "permission", errors)) return false;
        if (isNull(objectAdministratorId, "objectAdministratorId", errors)) return false;

        BiobankAdministrator ba = biobankAdministratorDao.get(objectAdministratorId);

        if (isNull(ba, "ba", errors)) return false;

        boolean result = false;

        try {
            result = update(ba, permission);
        } catch (LastManagerException ex) {
            // Specific error msg
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastBiobankManagerException"));
            return false;
        } catch (Exception ex) {
            // Unknown reason
            operationFailed(errors, ex);
            return false;
        }

        if (result) {
            Biobank biobank = ba.getBiobank();

            User user = ba.getUser();

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.permissionChanged",
                    biobank.getAbbreviation(), user.getWholeName(), permission);

            notificationDao.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                    NotificationType.BIOBANK_ADMINISTRATOR, locMsg, biobank.getId());
        }
        return result;
    }

    private boolean update(BiobankAdministrator ba, Permission permission) throws LastManagerException {
        if (isNull(ba, "ba", null)) return false;
        if (isNull(permission, "permission", null)) return false;

         /* Situation when we want to remove last manager. */

        if (!permission.equals(Permission.MANAGER) && isLastManager(ba)) {
            throw new LastManagerException("User: " + ba.getUser().getWholeName()
                    + " is the only administrator with MANAGER permission associated to biobank: "
                    + ba.getBiobank().getName() + ". He can't be removed!");
        }

        ba.setPermission(permission);
        biobankAdministratorDao.update(ba);
        return true;
    }

    public boolean assignAdministrator(Long objectId,
                                       Long newAdministratorId,
                                       Permission permission,
                                       ValidationErrors errors,
                                       Long loggedUserId) {
        notNull(errors);

        if (isNull(objectId, "objectId", errors)) return false;
        if (isNull(newAdministratorId, "newAdministratorId", errors)) return false;
        if (isNull(permission, "permission", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        Biobank biobankDB = biobankDao.get(objectId);
        User newAdmin = userDao.get(newAdministratorId);

        if (isNull(biobankDB, "biobankDB", errors)) return false;
        if (isNull(newAdmin, "newAdmin ", errors)) return false;

        if (biobankAdministratorDao.get(biobankDB, newAdmin) != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BiobankFacadeImpl.adminAlreadyExists"));
            return false;
        }

        boolean result = false;
        // New administrator role
        BiobankAdministrator ba = new BiobankAdministrator();
        ba.setPermission(permission);
        ba.setBiobank(biobankDB);
        ba.setUser(newAdmin);
        try {
            biobankAdministratorDao.create(ba);
        } catch (DataAccessException ex) {
            operationFailed(errors, ex);
            return false;
        }

        // Initialization of system role for administrator
        if (!newAdmin.getSystemRoles().contains(SystemRole.BIOBANK_OPERATOR)) {
            newAdmin.getSystemRoles().add(SystemRole.BIOBANK_OPERATOR);
            try {
                userDao.update(newAdmin);
            } catch (DataAccessException ex) {
                biobankAdministratorDao.remove(ba);
                operationFailed(errors, ex);
                return false;
            }
        }

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.BiobankFacadeImpl.adminAssigned",
                biobankDB.getAbbreviation(), newAdmin.getWholeName(), permission);

        notificationDao.create(getOtherBiobankAdministrators(biobankDB, loggedUserId),
                NotificationType.BIOBANK_ADMINISTRATOR, locMsg, biobankDB.getId());


        return true;

    }


    @Transactional(readOnly = true)
    public boolean isLastManager(BiobankAdministrator objectAdministrator) {
        if (!objectAdministrator.getPermission().equals(Permission.MANAGER)) {
            return false;
        }

        if (biobankAdministratorDao.get(objectAdministrator.getBiobank(), Permission.MANAGER).size() > 1) {
            return false;
        }

        return true;
    }

    @Transactional(readOnly = true)
    public BiobankAdministrator get(Long biobankId, Long userId) {
        if (isNull(biobankId, "biobankId", null)) return null;
        if (isNull(userId, "userId", null)) return null;

        try {
            return biobankAdministratorDao.get(biobankDao.get(biobankId), userDao.get(userId));
        } catch (DataAccessException ex) {
            operationFailed(null, ex);
            return null;
        }

    }

    @Transactional(readOnly = true)
    public boolean contains(Long biobankId, Long userId) {
        if (isNull(biobankId, "biobankId", null)) return false;
        if (isNull(userId, "userId", null)) return false;

        return biobankAdministratorDao.contains(biobankDao.get(biobankId), userDao.get(userId));
    }

    @Transactional(readOnly = true)
    public boolean hasPermission(Permission permission, Long objectId, Long userId) {
        if (isNull(permission, "permission", null)) return false;
        if (isNull(objectId, "objectId", null)) return false;
        if (isNull(userId, "userId", null)) return false;

        BiobankAdministrator ba = biobankAdministratorDao.get(biobankDao.get(objectId), userDao.get(userId));

        if (isNull(ba, "ba", null)) return false;

        return ba.getPermission().include(permission);
    }


    /**
     * Return true if user has at least one permission same or higher then given parameter.
     *
     * @param userId
     * @param permission
     * @return
     */
    @Transactional(readOnly = true)
    public boolean hasSameOrHigherPermission(Long userId, Permission permission) {
        if (isNull(permission, "permission", null)) return false;
        if (isNull(userId, "userId", null)) return false;

        User userDB = userDao.get(userId);
        if (isNull(userDB, "userDB", null)) return false;

        Permission permissionHighest = biobankAdministratorDao.getHighestPermission(userDB);
        if (isNull(permissionHighest, "permissionHighest", null)) return false;

        /* My highest permission to any of my biobank is higher than permission? */
        return permissionHighest.include(permission);
    }

}
