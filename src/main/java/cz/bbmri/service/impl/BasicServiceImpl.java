package cz.bbmri.service.impl;

import cz.bbmri.entities.*;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.service.BiobankAdministratorService;
import cz.bbmri.service.BiobankService;
import cz.bbmri.service.ProjectAdministratorService;
import cz.bbmri.service.ProjectService;
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
    private ProjectService projectService;

    @Autowired
    private BiobankService biobankService;

    @Autowired
    private ProjectAdministratorService projectAdministratorService;

    @Autowired
    private BiobankAdministratorService biobankAdministratorService;

    public static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object must not be null!");
        }
    }

    public static boolean isNull(final Object o, String varName, ValidationErrors errors) throws IllegalArgumentException {
        if (o == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.ilegalArguments", varName));
            return true;
        }
        return false;
    }

    public void developerMsg(Exception ex) {
        logger.error(ex.getLocalizedMessage());
    }

    public void objectNotFound(ValidationErrors errors, LocalizableError error, String objectName) throws IllegalArgumentException {
        if (errors != null) {
            // if errors set
            if (error != null) {
                // if specific message set
                errors.addGlobalError(error);
            } else {

                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.objectNotPresentInDB", objectName));
            }
        }
        logger.error("Searched object " + objectName + " was not present in DB");
    }

    public static void fatalError(ValidationErrors errors) {
        errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.dbg.fatal"));
    }

    /* Definition here because it is useful at more facades when we need to send notification */
    public List<User> getProjectAdministratorsUsers(Long projectId) {
        Project projectDB = projectService.get(projectId);
        // Project projectDB = projectService.eagerGet(projectId, true, false, false);
        List<User> users = new ArrayList<User>();
        for (ProjectAdministrator projectAdministrator : projectDB.getProjectAdministrators()) {
            users.add(projectAdministrator.getUser());
        }
        return users;
    }

    /* Definition here because it is useful at more facades when we need to send notification */
    public List<User> getOtherProjectWorkers(Project project, Long excludedUserId) {
        Project projectDB = projectService.get(project.getId());
        //  Project projectDB = projectService.eagerGet(project.getId(), true, false, false);
        Set<ProjectAdministrator> projectAdministrators = projectDB.getProjectAdministrators();

        if (excludedUserId != null) {
            ProjectAdministrator paExclude = projectAdministratorService.get(project.getId(), excludedUserId);
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
        Biobank biobankDB = biobankService.get(biobank.getId());
        Set<BiobankAdministrator> biobankAdministrators = biobankDB.getBiobankAdministrators();

        if (excludedUserId != null) {
            BiobankAdministrator baExclude = biobankAdministratorService.get(biobank.getId(), excludedUserId);
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
