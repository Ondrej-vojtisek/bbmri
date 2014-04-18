package cz.bbmri.dao.impl;

import cz.bbmri.dao.BoxDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Rack;
import cz.bbmri.entities.infrastructure.RackBox;
import cz.bbmri.entities.infrastructure.StandaloneBox;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class BoxDaoImpl extends BasicDaoImpl<Box> implements BoxDao {


    public List<StandaloneBox> getSorted(Biobank biobank, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name


        if (orderByParam != null) {

            if (orderByParam.equals("numberOfPositions")) {
                orderParam = "ORDER BY COUNT box.positions";
            } else {
                orderParam = "ORDER BY box." + orderByParam;
            }
        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        TypedQuery<StandaloneBox> typedQuery1 = em.createQuery("SELECT box FROM StandaloneBox box WHERE " +
                "box.infrastructure.biobank = :biobank " +
                orderParam, StandaloneBox.class);
        typedQuery1.setParameter("biobank", biobank);
        return typedQuery1.getResultList();
    }

    public List<RackBox> getSorted(Rack rack, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY box." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        TypedQuery<RackBox> typedQuery1 = em.createQuery("SELECT box FROM RackBox box WHERE " +
                "box.rack = :rack " +
                orderParam, RackBox.class);
        typedQuery1.setParameter("rack", rack);
        return typedQuery1.getResultList();
    }

    public Box getByName(Biobank biobank, Rack rack, String name) {
        notNull(biobank);
        notNull(name);

        Query query;

        if (rack == null) {

            // Standalone box

            query = em.createQuery("SELECT box FROM StandaloneBox box WHERE " +
                    "box.infrastructure.biobank = :biobankParam AND " +
                    "box.name = :nameParam");
            query.setParameter("biobankParam", biobank);
        } else {

            // rack box

            query = em.createQuery("SELECT box FROM RackBox box WHERE " +
                    "box.rack = :rackParam AND " +
                    "box.name = :nameParam");
            query.setParameter("rackParam", rack);
        }

        query.setParameter("nameParam", name);

        return getSingleResult();

    }
}
