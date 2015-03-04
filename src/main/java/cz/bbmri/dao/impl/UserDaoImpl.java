package cz.bbmri.dao.impl;

import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  Implementation for interface handling instances of User. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */


@Transactional
@Repository("userDAO")
public class UserDaoImpl extends BasicDaoImpl<User> implements UserDao {

    public List<User> findUser(String name, String surname, String email, int requiredResults){

        typedQuery = em.createQuery("" +
                "SELECT p " +
                "FROM User p WHERE " +
                    "LOWER(p.shibboleth.name) LIKE :nameParam " +
                    "OR LOWER(p.shibboleth.surname) LIKE :surnameParam " +
                    "OR LOWER(p.shibboleth.email) LIKE :emailParam " +
                    "ORDER BY " +
                        "CASE " +
                            "WHEN (LOWER(p.shibboleth.email) = :emailParam) THEN 100 " +
                            "WHEN (LOWER(p.shibboleth.surname) = :surnameParam) THEN 50 " +
                            "WHEN (LOWER(p.shibboleth.name) = :nameParam) THEN 30 " +
                            "ELSE 0 " +
                        "END " +
                    "DESC", User.class);

        typedQuery.setParameter("nameParam", (name != null ? "%" + name.toLowerCase() + "%"  : " "));
        typedQuery.setParameter("surnameParam", (surname != null ? "%" + surname.toLowerCase() + "%" : " "));
        typedQuery.setParameter("emailParam", (email != null ? "%" + email.toLowerCase() + "%" : " "));

        List<User> users = typedQuery.getResultList();
          if (users == null) {
              return null;
          }
          if (requiredResults > users.size()) {
              return users;
          }

        return users.subList(0, requiredResults);
    }

//    public User get(String eppn, String targetedId, String persistentId){
//
////        primary shibboleth identifier
//        if(eppn != null){
//            typedQuery = em.createQuery("SELECT p FROM User p WHERE p.shibboleth.eppn = :eppnParam", User.class);
//            typedQuery.setParameter("eppnParam", eppn);
//            return getSingleResult();
//        }
//
////        secondary shibboleth identifier
//        else if(targetedId != null){
//            typedQuery = em.createQuery("SELECT p FROM User p WHERE p.shibboleth.targetedId = :targetedIdParam", User.class);
//            typedQuery.setParameter("targetedIdParam", targetedId);
//            return getSingleResult();
//        }
//
////        ternary shibboleth identifier
//        else if(persistentId != null){
//            typedQuery = em.createQuery("SELECT p FROM User p WHERE p..shibboleth.eppn = :persistentIdParam", User.class);
//            typedQuery.setParameter("persistentIdParam", persistentId);
//            return getSingleResult();
//        }
//
//        return null;
//    }

    public List<User> getAllWithSystemRole(SystemRole systemRole){
        notNull(systemRole);
        typedQuery = em.createQuery("SELECT user FROM User user " +
                "JOIN user.systemRoles role " +
                "WHERE role = :systemRoleParam", User.class);
        typedQuery.setParameter("systemRoleParam", systemRole);

        return typedQuery.getResultList();
    }
}



