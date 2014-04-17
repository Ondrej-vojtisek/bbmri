package cz.bbmri.dao.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.entities.Biobank;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */


@Repository
public class BiobankDaoImpl extends BasicDaoImpl<Biobank, Long> implements BiobankDao {

    public Biobank getBiobankByAbbreviation(String abbreviation) {
        notNull(abbreviation);
        typedQuery = em.createQuery("SELECT p FROM Biobank p where p.abbreviation = :abbreviationParam", Biobank.class);
        typedQuery.setParameter("abbreviationParam", abbreviation);

        return getSingleResult();
    }

  }
