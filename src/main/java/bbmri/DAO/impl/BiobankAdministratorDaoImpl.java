package bbmri.dao.impl;

import bbmri.dao.BiobankAdministratorDao;
import bbmri.entities.Biobank;
import bbmri.entities.BiobankAdministrator;
import bbmri.entities.User;
import bbmri.entities.enumeration.Permission;
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

    public List<BiobankAdministrator> get(Biobank biobank, Permission permission) {
         Query query = em.createQuery("SELECT p FROM BiobankAdministrator p where " +
                 "p.biobank = :biobankParam " +
                 "and p.permission = :permissionParam ");
         query.setParameter("biobankParam", biobank);
         query.setParameter("permissionParam", permission);

         return query.getResultList();
     }
}