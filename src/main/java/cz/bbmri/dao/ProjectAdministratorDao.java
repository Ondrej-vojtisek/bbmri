package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface ProjectAdministratorDao extends BasicDao<ProjectAdministrator> {

    boolean contains(Project project, User user);

    ProjectAdministrator get(Project project, User user);

    List<ProjectAdministrator> get(Project project, Permission permission);

}
