package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.ProjectUserDAO;
import cz.bbmri.entity.Permission;
import cz.bbmri.entity.Project;
import cz.bbmri.entity.ProjectUser;
import cz.bbmri.entity.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
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

    public void remove(ProjectUser projectUser) {
        getCurrentSession().delete(projectUser);
    }

    public ProjectUser get(Project project, User user) {
        Criterion criterionProject = Restrictions.eq(ProjectUser.PROP_PROJECT, project);
        Criterion criterionUser = Restrictions.eq(ProjectUser.PROP_USER, user);

        // Retrieve a list of existing users matching the criterion above the list retrieval
        List<ProjectUser> list = getCurrentSession().createCriteria(ProjectUser.class)
                .add(criterionProject)
                .add(criterionUser)
                .setMaxResults(1).list();

        // Retrieve iterator from the list
        Iterator iterator = list.iterator();

        // Prepare the variable
        ProjectUser projectUser = null;

        // If user loaded
        if (iterator.hasNext()) {

            // Store the user instance
            projectUser = (ProjectUser) iterator.next();
        }

        return projectUser;
    }

    public boolean hasPermission(Permission permission, Project project, User user) {
        ProjectUser projectUser = get(project, user);

        if (projectUser == null) {
            return false;
        }

        return projectUser.getPermission().include(permission);
    }
}
