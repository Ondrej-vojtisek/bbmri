package cz.bbmri.dao.impl;


import cz.bbmri.dao.UserDao;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.SystemRole;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDaoImpl extends BasicDaoImpl<User> implements UserDao {

    @SuppressWarnings("unchecked")
    public List<User> findUser(User user){

        notNull(user);

/*
NOT case sensitive user search with priority match. If complete match on mail then higher priority than complete match on name
*/
        Query query = em.createQuery("" +
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
                    "DESC");

        query.setParameter("nameParam", (user.getName() != null ? "%" + user.getName().toLowerCase() + "%"  : " "));
        query.setParameter("surnameParam", (user.getSurname() != null ? "%" + user.getSurname().toLowerCase() + "%" : " "));
        query.setParameter("emailParam", (user.getEmail() != null ? "%" + user.getEmail().toLowerCase() + "%" : " "));

        return query.getResultList();
    }

    public User get(String eppn){
        notNull(eppn);
        Query query = em.createQuery("SELECT p FROM User p WHERE p.eppn = :eppnParam");
        query.setParameter("eppnParam", eppn);

        try{
            return (User) query.getSingleResult();
        }catch(NoResultException ex){
            return null;
        }
    }

    public List<User> getAllWithSystemRole(SystemRole systemRole){
        notNull(systemRole);
        Query query = em.createQuery("SELECT user FROM User user " +
                "JOIN user.systemRoles role " +
                "WHERE role = :systemRoleParam");
        query.setParameter("systemRoleParam", systemRole);

        return query.getResultList();
    }
}



