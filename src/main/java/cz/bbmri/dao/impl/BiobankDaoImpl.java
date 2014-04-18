package cz.bbmri.dao.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.entities.Biobank;
import org.springframework.stereotype.Repository;

/**
 * Implementation for interface handling instances of Biobank. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */


@Repository
public class BiobankDaoImpl extends BasicDaoImpl<Biobank> implements BiobankDao {

    public Biobank getBiobankByAbbreviation(String abbreviation) {
        notNull(abbreviation);
        typedQuery = em.createQuery("SELECT p FROM Biobank p where p.abbreviation = :abbreviationParam", Biobank.class);
        typedQuery.setParameter("abbreviationParam", abbreviation);

        return getSingleResult();
    }

  }
