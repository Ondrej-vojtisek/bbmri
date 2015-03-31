package cz.bbmri.dao;

import cz.bbmri.entity.Permission;
import cz.bbmri.entity.Project;
import cz.bbmri.entity.ProjectUser;
import cz.bbmri.entity.User;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface ProjectUserDAO extends AbstractCompositeDAO<ProjectUser> {

    ProjectUser get(Project project, User user);

    boolean hasPermission(Permission permission, Project project, User user);

}
