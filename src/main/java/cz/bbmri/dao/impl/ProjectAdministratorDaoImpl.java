package cz.bbmri.dao.impl;

import cz.bbmri.dao.ProjectAdministratorDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.Permission;
import org.springframework.stereotype.Repository;

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

    @SuppressWarnings("unchecked")
    public ProjectAdministrator get(Project project, User user) {
        Query query = em.createQuery("SELECT p FROM ProjectAdministrator p where " +
                "p.project = :projectParam " +
                "and p.user = :userParam ");
        query.setParameter("projectParam", project);
        query.setParameter("userParam", user);

        List<ProjectAdministrator> list = query.getResultList();
        if (list == null) {
            return null;
        }
        if (!list.isEmpty()) {
            return (ProjectAdministrator) query.getSingleResult();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
     public List<ProjectAdministrator> get(Project project, Permission permission) {
          Query query = em.createQuery("SELECT p FROM ProjectAdministrator p where " +
                  "p.project = :projectParam " +
                  "and p.permission = :permissionParam ");
          query.setParameter("projectParam", project);
          query.setParameter("permissionParam", permission);

          return query.getResultList();
      }

}
