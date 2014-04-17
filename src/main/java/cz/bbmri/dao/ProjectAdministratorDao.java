package cz.bbmri.dao;

import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 27.9.13
 * Time: 9:42
 * To change this template use File | Settings | File Templates.
 */
public interface ProjectAdministratorDao extends BasicDao<ProjectAdministrator, Long>{

    boolean contains(Project project, User user);

    ProjectAdministrator get(Project project, User user);

    List<ProjectAdministrator> get(Project project, Permission permission);

}
