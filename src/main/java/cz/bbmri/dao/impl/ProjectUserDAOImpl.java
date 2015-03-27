package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.ProjectUserDAO;
import cz.bbmri.entity.Project;
import cz.bbmri.entity.ProjectUser;
import cz.bbmri.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("projectUserDAO")
@Transactional
public class ProjectUserDAOImpl extends GenericDAOImpl<ProjectUser> implements ProjectUserDAO {

    public static List<User> getOtherProjectUsers(Project project, User user) {
        notNull(project);
        notNull(user);

        Set<ProjectUser> projectUsers = project.getProjectUser();

        List<User> users = new ArrayList<User>();

        for (ProjectUser projectUser : projectUsers) {
            if (!projectUser.getUser().equals(user)) {
                users.add(projectUser.getUser());
            }
        }

        return users;
    }

    public void remove(ProjectUser projectUser){
        getCurrentSession().delete(projectUser);
    }
}
