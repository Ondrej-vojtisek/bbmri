package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;

import java.util.List;

/**
 * Interface to handle instances of ProjectAdministrator stored in database.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface ProjectAdministratorDao extends BasicDao<ProjectAdministrator> {

    /**
     * Check if there is a administrator relationship between the project and the user.
     *
     * @param project - object of type Project to be checked
     * @param user    - object of type User to be checked
     * @return true or false existence of relastionship between project and user
     */
    boolean contains(Project project, User user);

    /**
     * Return relationship between project and user
     *
     * @param project
     * @param user
     * @return ProjectAdministrator or null
     */
    ProjectAdministrator get(Project project, User user);

    /**
     * Return all administrators of the project with defined permission. E.G. return all managers.
     *
     * @param project    - only instances of projectAdministrator asociated with this project
     * @param permission - select only this permission
     * @return list of projectAdministrators
     */
    List<ProjectAdministrator> get(Project project, Permission permission);

}
