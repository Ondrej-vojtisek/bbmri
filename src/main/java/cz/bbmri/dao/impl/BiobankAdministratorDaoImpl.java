package cz.bbmri.dao.impl;

import cz.bbmri.dao.BiobankAdministratorDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 24.9.13
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BiobankAdministratorDaoImpl extends BasicDaoImpl<BiobankAdministrator> implements BiobankAdministratorDao {

    public boolean contains(Biobank biobank, User user) {
        BiobankAdministrator pa = get(biobank, user);
        return pa != null;

    }

    @SuppressWarnings("unchecked")
    public BiobankAdministrator get(Biobank biobank, User user) {
        Query query = em.createQuery("SELECT p FROM BiobankAdministrator p where " +
                "p.biobank = :biobankParam " +
                "and p.user = :userParam ");
        query.setParameter("biobankParam", biobank);
        query.setParameter("userParam", user);

        List<BiobankAdministrator> list = query.getResultList();
        if (list == null) {
            return null;
        }
        if (!list.isEmpty()) {
            return (BiobankAdministrator) query.getSingleResult();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<BiobankAdministrator> get(Biobank biobank, Permission permission) {
        Query query = em.createQuery("SELECT p FROM BiobankAdministrator p where " +
                "p.biobank = :biobankParam " +
                "and p.permission = :permissionParam ");
        query.setParameter("biobankParam", biobank);
        query.setParameter("permissionParam", permission);

        return query.getResultList();
    }

    /**
     * Return the highest permission of user to any biobank.
     *
     * @param user
     * @return
     */
    @SuppressWarnings("unchecked")
    public Permission getHighestPermission(User user) {

        Query query = em.createQuery("" +
                "SELECT p.permission " +
                "FROM BiobankAdministrator p WHERE " +
                    "p.user = :userParam " +
                        "ORDER BY " +
                            "CASE " +
                                "WHEN (p.permission = :managerPermission) THEN 4 " +
                                "WHEN (p.permission = :editorPermission) THEN 3 " +
                                "WHEN (p.permission = :executorPermission) THEN 2 " +
                                "WHEN (p.permission = :visitorPermission) THEN 1" +
                                "ELSE 0 " +
                            "END " +
                        "DESC");
        query.setParameter("userParam", user);
        query.setParameter("managerPermission", Permission.MANAGER);
        query.setParameter("editorPermission", Permission.EDITOR);
        query.setParameter("executorPermission", Permission.EXECUTOR);
        query.setParameter("visitorPermission", Permission.VISITOR);

        List<Permission> list = query.getResultList();
        if (list == null) {
            return null;
        }
        if (list.isEmpty()) {
            return null;
        }

        return (Permission) query.getSingleResult();
    }
}