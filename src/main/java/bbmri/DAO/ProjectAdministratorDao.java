package bbmri.dao;

import bbmri.entities.Project;
import bbmri.entities.ProjectAdministrator;
import bbmri.entities.User;
import bbmri.entities.enumeration.Permission;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 27.9.13
 * Time: 9:42
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectAdministratorDao extends BasicDao<ProjectAdministrator>{

    boolean contains(Project project, User user);

    ProjectAdministrator get(Project project, User user);

    List<ProjectAdministrator> get(Project project, Permission permission);
}
