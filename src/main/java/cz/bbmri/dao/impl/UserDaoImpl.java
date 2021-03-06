package cz.bbmri.dao.impl;

import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Implementation for interface handling instances of User. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class UserDaoImpl extends BasicDaoImpl<User> implements UserDao {

    public List<User> findUser(User user){

        notNull(user);
        typedQuery = em.createQuery("" +
                "SELECT p " +
                "FROM User p WHERE " +
                    "LOWER(p.name) LIKE :nameParam " +
                    "OR LOWER(p.surname) LIKE :surnameParam " +
                    "OR LOWER(p.email) LIKE :emailParam " +
                    "ORDER BY " +
                        "CASE " +
                            "WHEN (LOWER(p.email) = :emailParam) THEN 100 " +
                            "WHEN (LOWER(p.surname) = :surnameParam) THEN 50 " +
                            "WHEN (LOWER(p.name) = :nameParam) THEN 30 " +
                            "ELSE 0 " +
                        "END " +
                    "DESC", User.class);

        typedQuery.setParameter("nameParam", (user.getName() != null ? "%" + user.getName().toLowerCase() + "%"  : " "));
        typedQuery.setParameter("surnameParam", (user.getSurname() != null ? "%" + user.getSurname().toLowerCase() + "%" : " "));
        typedQuery.setParameter("emailParam", (user.getEmail() != null ? "%" + user.getEmail().toLowerCase() + "%" : " "));

        return typedQuery.getResultList();
    }

    public User get(String eppn, String targetedId, String persistentId){

//        primary shibboleth identifier
        if(eppn != null){
            typedQuery = em.createQuery("SELECT p FROM User p WHERE p.eppn = :eppnParam", User.class);
            typedQuery.setParameter("eppnParam", eppn);
            return getSingleResult();
        }

//        secondary shibboleth identifier
        else if(targetedId != null){
            typedQuery = em.createQuery("SELECT p FROM User p WHERE p.targetedId = :targetedIdParam", User.class);
            typedQuery.setParameter("targetedIdParam", targetedId);
            return getSingleResult();
        }

//        ternary shibboleth identifier
        else if(persistentId != null){
            typedQuery = em.createQuery("SELECT p FROM User p WHERE p.eppn = :persistentIdParam", User.class);
            typedQuery.setParameter("persistentIdParam", persistentId);
            return getSingleResult();
        }

        return null;
    }

    public List<User> getAllWithSystemRole(SystemRole systemRole){
        notNull(systemRole);
        typedQuery = em.createQuery("SELECT user FROM User user " +
                "JOIN user.systemRoles role " +
                "WHERE role = :systemRoleParam", User.class);
        typedQuery.setParameter("systemRoleParam", systemRole);

        return typedQuery.getResultList();
    }
}



