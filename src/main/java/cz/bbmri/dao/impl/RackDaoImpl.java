package cz.bbmri.dao.impl;

import cz.bbmri.dao.RackDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import cz.bbmri.entities.infrastructure.Rack;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation for interface handling instances of Rack. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class RackDaoImpl extends BasicDaoImpl<Rack> implements RackDao {

    public List<Rack> getSorted(Biobank biobank, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY rack." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        typedQuery  = em.createQuery("SELECT rack FROM Rack rack WHERE " +
                "rack.container.infrastructure.biobank = :biobank " +
                orderParam, Rack.class);
        typedQuery .setParameter("biobank", biobank);
        return typedQuery .getResultList();
    }

    public Rack getByName(Container container, String name) {
        notNull(container);
        notNull(name);
        typedQuery  = em.createQuery("SELECT p FROM Rack p WHERE " +
                "p.container = :containerParam AND " +
                "p.name = :nameParam", Rack.class);
        typedQuery .setParameter("containerParam", container);
        typedQuery .setParameter("nameParam", name);

        return getSingleResult();
    }
}
