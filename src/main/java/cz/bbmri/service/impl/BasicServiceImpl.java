package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class providing basic operations used in more than one service implementation
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class BasicServiceImpl {
    /**
     * StoragePath is retrieved from my.properties file
     */
    @Value("${StoragePath}")
    protected String storagePath;

    final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private ProjectAdministratorDao projectAdministratorDao;

    @Autowired
    private BiobankAdministratorDao biobankAdministratorDao;

    @Autowired
    private UserDao userDao;

    /* Necessary condition - use for situation like ValidationErrors = null. If this is true than even methods like isNull will
    * fail. */

    /**
     * Necessary condition of methods. If object o is null, method is interrupted with exception. Situation when situation
     * is certainly caused by implementation bug and not by user.
     *
     * @param o
     * @throws IllegalArgumentException
     */
    static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object must not be null!");
        }
    }

    /**
     * Shared method for handling null params of methods. If o is null - inform user that operation was unsuccessful.
     * If method doesn't have ValidationErrors among arguments, than send message to logger.
     * <p/>
     * Temporal throw IllegalArgumentException for testing and debugging.
     *
     * @param o       - tested object
     * @param varName - name of variable
     * @param errors  - signalization to user
     * @return true - is null, false not null
     */
    boolean isNull(final Object o, String varName, ValidationErrors errors) {
        if (o == null) {
            if (errors != null) {
                // If it is possible to print in on frontend
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.ilegalArguments", varName));
            } else {
                // if not - than note inform at least developer
                logger.error("Variable: " + varName + " is null");

                // TODO: temporal but good for testing
                throw new IllegalArgumentException("Variable: " + varName);
            }
            return true;
        }
        return false;
    }

    /**
     * How to handle exceptions - print then for user if possible or ad then to logger.
     *
     * @param errors
     * @param ex
     */
    void operationFailed(ValidationErrors errors, Exception ex) {
        if (errors != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.fail"));
        }
        if (ex != null) {
            logger.error(ex.getLocalizedMessage());
        }
    }

    /**
     * Indication that operation had no effect
     *
     * @param errors
     */
    void noEffect(ValidationErrors errors) {
        if (errors != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.noEffect"));
        }
    }

    /**
     * Return all users associated with project
     *
     * @param projectId - Id of project
     * @return list of users associated with project
     */
    List<User> getProjectAdministratorsUsers(Long projectId) {
        Project projectDB = projectDao.get(projectId);
        if (isNull(projectDB, "projectDB", null)) return null;

        List<User> users = new ArrayList<User>();

        for (ProjectAdministrator projectAdministrator : projectDB.getProjectAdministrators()) {
            users.add(projectAdministrator.getUser());
        }
        return users;
    }

    /**
     * Return all users associated with project except the given one. It enables to send notification as broadcast
     *
     * @param project
     * @param excludedUserId - initiator of event will be excluded from recipients
     * @return list of users associated with project except one
     */
    List<User> getOtherProjectWorkers(Project project, Long excludedUserId) {
        Project projectDB = projectDao.get(project.getId());
        if (isNull(projectDB, "projectDB", null)) return null;

        Set<ProjectAdministrator> projectAdministrators = projectDB.getProjectAdministrators();

        if (excludedUserId != null) {
            ProjectAdministrator paExclude = projectAdministratorDao.get(projectDao.get(project.getId()),
                    userDao.get(excludedUserId));
            if (projectAdministrators.contains(paExclude)) {
                projectAdministrators.remove(paExclude);
            }
        }

        List<User> users = new ArrayList<User>();
        for (ProjectAdministrator pa : projectAdministrators) {
            users.add(pa.getUser());
        }

        return users;
    }

    /**
     * Return all users associated with biobank except the given one. It enables to send notification as broadcast
     *
     * @param biobank
     * @param excludedUserId - initiator of event will be excluded from recipients
     * @return list of users associated with biobank except one
     */
    List<User> getOtherBiobankAdministrators(Biobank biobank, Long excludedUserId) {
        Biobank biobankDB = biobankDao.get(biobank.getId());
        if (isNull(biobankDB, "biobankDB", null)) return null;

        Set<BiobankAdministrator> biobankAdministrators = biobankDB.getBiobankAdministrators();

        if (excludedUserId != null) {
            BiobankAdministrator baExclude = biobankAdministratorDao.get(biobankDao.get(biobank.getId()), userDao.get(excludedUserId));

            if (biobankAdministrators.contains(baExclude)) {
                biobankAdministrators.remove(baExclude);
            }
        }

        List<User> users = new ArrayList<User>();
        for (BiobankAdministrator ba : biobankAdministrators) {
            users.add(ba.getUser());
        }

        return users;
    }

}
