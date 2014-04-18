package cz.bbmri.dao.impl;

import cz.bbmri.dao.ProjectAdministratorDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation for interface handling instances of ProjectAdministrator. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class ProjectAdministratorDaoImpl extends BasicDaoImpl<ProjectAdministrator> implements ProjectAdministratorDao {

    public boolean contains(Project project, User user) {
        ProjectAdministrator pa = get(project, user);
        return pa != null;
    }

    public ProjectAdministrator get(Project project, User user) {
        typedQuery = em.createQuery("SELECT p FROM ProjectAdministrator p where " +
                "p.project = :projectParam " +
                "and p.user = :userParam ", ProjectAdministrator.class);
        typedQuery.setParameter("projectParam", project);
        typedQuery.setParameter("userParam", user);

        return getSingleResult();
    }

    public List<ProjectAdministrator> get(Project project, Permission permission) {
        typedQuery = em.createQuery("SELECT p FROM ProjectAdministrator p where " +
                "p.project = :projectParam " +
                "and p.permission = :permissionParam ", ProjectAdministrator.class);
        typedQuery.setParameter("projectParam", project);
        typedQuery.setParameter("permissionParam", permission);

        return typedQuery.getResultList();
    }

}
