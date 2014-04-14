package cz.bbmri.dao.impl;

import cz.bbmri.dao.ProjectAdministratorDao;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 27.9.13
 * Time: 9:43
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ProjectAdministratorDaoImpl extends BasicDaoImpl<ProjectAdministrator, Long> implements ProjectAdministratorDao {

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

        List<ProjectAdministrator> list = typedQuery.getResultList();
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
