package cz.bbmri.dao.impl;

import cz.bbmri.dao.RackDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.2.14
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RackDaoImpl extends BasicDaoImpl<Rack, Long> implements RackDao {

    public List<Rack> getSorted(Biobank biobank, String orderByParam, boolean desc) {
        Query query = null;
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY rack." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        query = em.createQuery("SELECT rack FROM Rack rack WHERE " +
                "rack.container.infrastructure.biobank = :biobank " +
                orderParam);
        query.setParameter("biobank", biobank);
        return query.getResultList();
    }

    public Rack getByName(Container container, String name) {
        notNull(container);
        notNull(name);
        Query query = em.createQuery("SELECT p FROM Rack p WHERE " +
                "p.container = :containerParam AND " +
                "p.name = :nameParam");
        query.setParameter("containerParam", container);
        query.setParameter("nameParam", name);

        try {
            return (Rack) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
