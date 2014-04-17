package cz.bbmri.dao.impl;

import cz.bbmri.dao.ContainerDao;
import cz.bbmri.entities.Biobank;
import cz.bbmri.entities.infrastructure.Container;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */

@Repository
public class ContainerDaoImpl extends BasicDaoImpl<Container, Long> implements ContainerDao {

    public List<Container> getSorted(Biobank biobank, String orderByParam, boolean desc) {
        String orderParam = "";
        // ORDER BY p.name
        if (orderByParam != null) {
            orderParam = "ORDER BY container." + orderByParam;

        }
        // ORDER BY p.name DESC
        if (desc) {
            orderParam = orderParam + " DESC";
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
