package cz.bbmri.dao.impl;

import cz.bbmri.dao.ContainerDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Implementation for interface handling instances of Container. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class ContainerDaoImpl extends BasicDaoImpl<Container> implements ContainerDao {

    public List<Container> getSorted(Biobank biobank, String orderByParam, boolean desc) {
        notNull(biobank);

        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY container." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
        }

        if(biobank.getInfrastructure() == null){
            logger.debug("infrastructure null");
            return null;
        }

        typedQuery = em.createQuery("SELECT container FROM Container container WHERE " +
                "container.infrastructure.biobank = :biobank " +
                orderParam, Container.class);
        typedQuery.setParameter("biobank", biobank);
        return typedQuery.getResultList();
    }

    public Container getByName(Biobank biobank, String name) {
        notNull(biobank);
        notNull(name);
        typedQuery = em.createQuery("SELECT p FROM Container p WHERE " +
                "p.infrastructure.biobank = :biobankParam AND " +
                "p.name = :nameParam", Container.class);
        typedQuery.setParameter("biobankParam", biobank);
        typedQuery.setParameter("nameParam", name);

        return getSingleResult();
    }
}
