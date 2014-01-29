package cz.bbmri.facade.impl;

import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.service.ProjectAdministratorService;
import cz.bbmri.service.ProjectService;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.10.13
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public class BasicFacade {

    protected static final int SUCCESS = 0;
    protected static final int NOT_SUCCESS = -1;

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectAdministratorService projectAdministratorService;

    public static void notNull(final Object o) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object can't be a null object");
        }
    }

    public static void objectNotFound(final Object o, final Long id) throws IllegalArgumentException {
        if (o == null) {
            throw new IllegalArgumentException("Object can't be a null object. " +
                    "It means that given identifier isn't present in database. ID: " + id);
        }
    }

    public static void fatalError(ValidationErrors errors){
        errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.BasicFacade.dbg.fatal"));
    }

    /* Here because it is useful at more facades when we need to send notification */
    public List<User> getProjectAdministratorsUsers(Long projectId) {
           Project projectDB = projectService.eagerGet(projectId, true, false, false, false);
           List<User> users = new ArrayList<User>();
           for(ProjectAdministrator projectAdministrator : projectDB.getProjectAdministrators()){
               users.add(projectAdministrator.getUser());
           }
           return users;
       }

    /* Here because it is useful at more facades when we need to send notification */
   public List<User> getOtherProjectWorkers(Project project, Long excludedUserId) {
        Project projectDB = projectService.eagerGet(project.getId(), true, false, false, false);
        Set<ProjectAdministrator> projectAdministrators = projectDB.getProjectAdministrators();
        ProjectAdministrator paExclude = projectAdministratorService.get(project.getId(), excludedUserId);
        projectAdministrators.remove(paExclude);

        List<User> users = new ArrayList<User>();
        for (ProjectAdministrator pa : projectAdministrators) {
            users.add(pa.getUser());
        }

        return users;
    }


}
