package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.*;
import cz.bbmri.entities.constant.Constant;
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
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 28.8.13
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 */
public class BasicServiceImpl {

    @Value("${StoragePath}")
    protected String storagePath;

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());


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
    public static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object must not be null!");
        }
    }

    /* Check for null value. Used for argument and also for objects retrieved from DB. */
    public boolean isNull(final Object o, String varName, ValidationErrors errors) {
        if (o == null) {
            operationFailed(errors, null);
            logger.error("Variable: " + varName + " is null");
            //errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.ilegalArguments", varName));
            return true;
        }
        return false;
    }

    /* Indication for user that operation failed and for developer note to log*/
    public void operationFailed(ValidationErrors errors, Exception ex) {
        if (errors != null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.fail"));
        }
        if (ex != null) {
            logger.error(ex.getLocalizedMessage());
        }
    }

    /* Definition here because it is useful at more facades when we need to send notification */
    public List<User> getProjectAdministratorsUsers(Long projectId) {
        Project projectDB = projectDao.get(projectId);
        // Project projectDB = projectService.eagerGet(projectId, true, false, false);
        List<User> users = new ArrayList<User>();
        for (ProjectAdministrator projectAdministrator : projectDB.getProjectAdministrators()) {
            users.add(projectAdministrator.getUser());
        }
        return users;
    }

    /* Definition here because it is useful at more facades when we need to send notification */
    public List<User> getOtherProjectWorkers(Project project, Long excludedUserId) {
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

    public List<User> getOtherBiobankAdministrators(Biobank biobank, Long excludedUserId) {
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

    protected boolean createFolderStructure(Project project, ValidationErrors errors) {

        String projectPath = storagePath + Project.PROJECT_FOLDER;

        if (!ServiceUtils.folderExists(storagePath)) {
            if (ServiceUtils.createFolder(storagePath, errors) != Constant.SUCCESS) {
                return false;
            }
        }

        // If this is the first created project - create folder for all projects

        if (!ServiceUtils.folderExists(projectPath)) {
            if (ServiceUtils.createFolder(projectPath, errors) != Constant.SUCCESS) {
                return false;
            }
        }

        // Folder for the project

        if (!ServiceUtils.folderExists(project.getProjectFolderPath())) {
            if (ServiceUtils.createFolder(project.getProjectFolderPath(), errors) != Constant.SUCCESS) {
                return false;
            }
        }

        return true;

    }

}
