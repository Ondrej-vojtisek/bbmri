package bbmri.dao.impl;


import bbmri.dao.UserDao;
import bbmri.entities.User;
import org.jcp.xml.dsig.internal.dom.ApacheNodeSetData;
import org.springframework.stereotype.Repository;
import org.w3c.dom.xpath.XPathResult;

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

    public List<User> findUser(User user){

        notNull(user);

        Query query = em.createQuery("" +
                "SELECT p " +
                "FROM User p WHERE " +
                    "p.name LIKE :nameParam " +
                    "OR p.surname LIKE :surnameParam " +
                    "OR p.email LIKE :emailParam " +
                    "ORDER BY " +
                        "CASE " +
                            "WHEN (p.email = :emailParam) THEN 100 " +
                            "WHEN (p.surname = :surnameParam) THEN 50 " +
                            "WHEN (p.name = :nameParam) THEN 30 " +
                            "ELSE 0 " +
                        "END " +
                    "DESC");

        query.setParameter("nameParam", (user.getName() != null ? "%" + user.getName() + "%"  : " "));
        query.setParameter("surnameParam", (user.getName() != null ? "%" + user.getSurname() + "%" : " "));
        query.setParameter("emailParam", (user.getName() != null ? "%" + user.getEmail() + "%" : " "));

        return query.getResultList();
    }
}



