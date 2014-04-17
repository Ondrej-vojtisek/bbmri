package cz.bbmri.service.impl;

import cz.bbmri.dao.NotificationDao;
import cz.bbmri.dao.ProjectAdministratorDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.service.ProjectAdministratorService;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Transactional
@Service("projectAdministratorService")
public class ProjectAdministratorServiceImpl extends BasicServiceImpl implements ProjectAdministratorService {

    @Autowired
    private ProjectAdministratorDao projectAdministratorDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private NotificationDao notificationDao;

    @Transactional(readOnly = true)
    public ProjectAdministrator get(Long id) {
        if (isNull(id, "id", null)) return null;
        return projectAdministratorDao.get(id);
    }

//    public ProjectAdministrator update(ProjectAdministrator projectAdministrator) {
//        if (isNull(projectAdministrator, "projectAdministrator", null)) return null;
//
//        ProjectAdministrator pa = projectAdministratorDao.get(projectAdministrator.getId());
//        if (isNull(pa, "pa", null)) return null;
//
//        // Only permission can be updated
//        if (projectAdministrator.getPermission() != null) pa.setPermission(projectAdministrator.getPermission());
//
//        projectAdministratorDao.update(pa);
//
//        return pa;
//    }

    @Transactional(readOnly = true)
    public ProjectAdministrator get(Long projectId, Long userId) {
        if (isNull(projectId, "projectId", null)) return null;
        if (isNull(userId, "userId", null)) return null;

        Project projectDB = projectDao.get(projectId);
        if (isNull(projectDB, "projectDB", null)) return null;

        User userDB = userDao.get(userId);
        if (isNull(userDB, "userDB", null)) return null;

        return projectAdministratorDao.get(projectDB, userDB);
    }

    @Transactional(readOnly = true)
    public boolean contains(Long projectId, Long userId) {
        if (isNull(projectId, "projectId", null)) return false;
        if (isNull(userId, "userId", null)) return false;


        Project projectDB = projectDao.get(projectId);
        if (isNull(projectDB, "projectDB", null)) return false;

        User userDB = userDao.get(userId);
        if (isNull(userDB, "userDB", null)) return false;

        return projectAdministratorDao.contains(projectDB, userDB);
    }

    public boolean removeAdministrator(Long objectAdministratorId, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(objectAdministratorId, "objectAdministratorId", null)) return false;
        if (isNull(loggedUserId, "loggedUserId", null)) return false;

        ProjectAdministrator pa = projectAdministratorDao.get(objectAdministratorId);
        if (isNull(pa, "pa", errors)) return false;

        boolean result = false;

        if (isLastManager(pa)) {
            // Enough to inform user
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastProjectManagerException"));
            return false;
        }
        User userDB = pa.getUser();

        // Remove system role
        if (userDB.getProjectAdministrators().size() == 1 &&
                userDB.getSystemRoles().contains(SystemRole.PROJECT_TEAM_MEMBER)) {
            userDB.getSystemRoles().remove(SystemRole.PROJECT_TEAM_MEMBER);
            userDao.update(userDB);
        }

        projectAdministratorDao.remove(pa);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.adminDeleted",
                userDB.getWholeName(), pa.getProject().getName());

        notificationDao.create(getOtherProjectWorkers(pa.getProject(), loggedUserId),
                NotificationType.BIOBANK_ADMINISTRATOR, locMsg, pa.getProject().getId());

        return result;
    }

    @Transactional(readOnly = true)
    boolean isLastManager(ProjectAdministrator objectAdministrator) {
        if (isNull(objectAdministrator, "objectAdministrator", null)) return false;

        // Not manager
        if (!objectAdministrator.getPermission().equals(Permission.MANAGER)) {
            return false;
        }

        // more managers than one
        if (projectAdministratorDao.get(objectAdministrator.getProject(), Permission.MANAGER).size() > 1) {
            return false;
        }

        // just one manager
        return true;
    }

    public boolean changeAdministratorPermission(Long objectAdministratorId,
                                                 Permission permission,
                                                 ValidationErrors errors,
                                                 Long loggedUserId) {
        notNull(errors);

        if (isNull(objectAdministratorId, "objectAdministratorId", null)) return false;
        if (isNull(loggedUserId, "loggedUserId", null)) return false;

        ProjectAdministrator pa = projectAdministratorDao.get(objectAdministratorId);
        if (isNull(pa, "pa", errors)) return false;

        if (!permission.equals(Permission.MANAGER) && isLastManager(pa)) {
            // Enough to inform user
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.exceptions.LastProjectManagerException"));
            return false;
        }

        pa.setPermission(permission);
        projectAdministratorDao.update(pa);


        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.permissionChanged",
                pa.getProject().getName(), pa.getUser().getWholeName(), permission);

        notificationDao.create(getOtherProjectWorkers(pa.getProject(), loggedUserId),
                NotificationType.PROJECT_ADMINISTRATOR, locMsg, pa.getProject().getId());
        return true;
    }

    public boolean assignAdministrator(Long objectId, Long newAdministratorId, Permission permission, ValidationErrors errors, Long loggedUserId) {
        notNull(errors);

        if (isNull(objectId, "objectId", null)) return false;
        if (isNull(loggedUserId, "loggedUserId", null)) return false;
        if (isNull(newAdministratorId, "newAdministratorId", null)) return false;
        if (isNull(permission, "permission", null)) return false;

        Project projectDB = projectDao.get(objectId);
        User newAdmin = userDao.get(newAdministratorId);

        if (isNull(projectDB, "projectDB", null)) return false;
        if (isNull(newAdmin, "newAdmin", null)) return false;

        // He is already admin
        if (projectAdministratorDao.get(projectDB, newAdmin) != null) {
            noEffect(errors);
            return false;
        }

        ProjectAdministrator pa = new ProjectAdministrator();
        pa.setPermission(permission);
        pa.setProject(projectDB);
        pa.setUser(newAdmin);

        projectAdministratorDao.create(pa);

        // Set system role
        if (!newAdmin.getSystemRoles().contains(SystemRole.PROJECT_TEAM_MEMBER)) {
            newAdmin.getSystemRoles().add(SystemRole.PROJECT_TEAM_MEMBER);
        }

        userDao.update(newAdmin);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.assignedAdministrator",
                projectDB.getName(), newAdmin.getWholeName(), permission);

        notificationDao.create(getOtherProjectWorkers(projectDB, loggedUserId),
                NotificationType.PROJECT_ADMINISTRATOR, locMsg, projectDB.getId());
        return true;
    }
    public boolean hasPermission(Permission permission, Long objectId, Long userId) {
        if (isNull(objectId, "objectId", null)) return false;
        if (isNull(userId, "userId", null)) return false;
        if (isNull(permission, "permission", null)) return false;

        ProjectAdministrator pa = projectAdministratorDao.get(projectDao.get(objectId), userDao.get(userId));

        if (isNull(pa, "pa", null)) return false;

        return pa.getPermission().include(permission);
    }
}
