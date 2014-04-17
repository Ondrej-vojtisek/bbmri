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
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public class BasicServiceImpl {

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
    static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object must not be null!");
        }
    }

    /* Check for null value. Used for argument and also for objects retrieved from DB. */
    boolean isNull(final Object o, String varName, ValidationErrors errors) {
        if (o == null) {
            operationFailed(errors, null);

            if(errors != null){
                // If it is possible to print in on frontend
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.ilegalArguments", varName));
            }else{
                // if not - than note for developer
                logger.error("Variable: " + varName + " is null");
                // TODO: temporal but good for testing
                throw new IllegalArgumentException("Variable: " + varName);
            }
            return true;
        }
        return false;
    }

    /* Indication for user that operation failed and for developer note to log*/
    void operationFailed(ValidationErrors errors, Exception ex) {
        if (errors != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.fail"));
        }
        if (ex != null) {
            logger.error(ex.getLocalizedMessage());
        }
    }

    /* Indication that operation didn't do anything*/
    void noEffect(ValidationErrors errors) {
        if (errors != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.noEffect"));
        }
    }

    /* Definition here because it is useful at more facades when we need to send notification */
    List<User> getProjectAdministratorsUsers(Long projectId) {
        Project projectDB = projectDao.get(projectId);
        // Project projectDB = projectService.eagerGet(projectId, true, false, false);
        List<User> users = new ArrayList<User>();
        for (ProjectAdministrator projectAdministrator : projectDB.getProjectAdministrators()) {
            users.add(projectAdministrator.getUser());
        }
        return users;
    }

    /* Definition here because it is useful at more facades when we need to send notification */
    List<User> getOtherProjectWorkers(Project project, Long excludedUserId) {
        Project projectDB = projectDao.get(project.getId());
        //  Project projectDB = projectService.eagerGet(project.getId(), true, false, false);
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

    List<User> getOtherBiobankAdministrators(Biobank biobank, Long excludedUserId) {
        Biobank biobankDB = biobankDao.get(biobank.getId());
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
