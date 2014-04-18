package cz.bbmri.dao.impl;

import cz.bbmri.dao.BiobankAdministratorDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.Permission;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation for interface handling instances of BiobankAdministrator. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class BiobankAdministratorDaoImpl extends BasicDaoImpl<BiobankAdministrator>
        implements BiobankAdministratorDao {

    public boolean contains(Biobank biobank, User user) {
        BiobankAdministrator pa = get(biobank, user);
        return pa != null;
    }

    public BiobankAdministrator get(Biobank biobank, User user) {
        typedQuery = em.createQuery("SELECT p FROM BiobankAdministrator p where " +
                "p.biobank = :biobankParam " +
                "and p.user = :userParam ", BiobankAdministrator.class);
        typedQuery.setParameter("biobankParam", biobank);
        typedQuery.setParameter("userParam", user);

        return getSingleResult();
    }

    public List<BiobankAdministrator> get(Biobank biobank, Permission permission) {
        typedQuery = em.createQuery("SELECT p FROM BiobankAdministrator p where " +
                "p.biobank = :biobankParam " +
                "and p.permission = :permissionParam ", BiobankAdministrator.class);
        typedQuery.setParameter("biobankParam", biobank);
        typedQuery.setParameter("permissionParam", permission);

        return typedQuery.getResultList();
    }

    public Permission getHighestPermission(User user) {

        TypedQuery<Permission> typedQuery1 = em.createQuery("" +
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
                "DESC", Permission.class);
        typedQuery1.setParameter("userParam", user);
        typedQuery1.setParameter("managerPermission", Permission.MANAGER);
        typedQuery1.setParameter("editorPermission", Permission.EDITOR);
        typedQuery1.setParameter("executorPermission", Permission.EXECUTOR);
        typedQuery1.setParameter("visitorPermission", Permission.VISITOR);

        try {
            return typedQuery1.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}