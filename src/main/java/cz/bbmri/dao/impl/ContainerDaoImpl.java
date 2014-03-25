package cz.bbmri.dao.impl;

import cz.bbmri.dao.ContainerDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 15:08
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class ContainerDaoImpl extends BasicDaoImpl<Container, Long> implements ContainerDao {

    public List<Container> getSorted(Biobank biobank, String orderByParam, boolean desc) {
        Query query = null;
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY container." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        query = em.createQuery("SELECT container FROM Container container WHERE " +
                "container.infrastructure.biobank = :biobank " +
                orderParam);
        query.setParameter("biobank", biobank);
        return query.getResultList();
    }

    public Container getByName(Biobank biobank, String name) {
        notNull(biobank);
        notNull(name);
        Query query = em.createQuery("SELECT p FROM Container p WHERE " +
                "p.infrastructure.biobank = :biobankParam AND " +
                "p.name = :nameParam");
        query.setParameter("biobankParam", biobank);
        query.setParameter("nameParam", name);

        try {
            return (Container) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
