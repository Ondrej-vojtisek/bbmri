package cz.bbmri.dao.impl;

import cz.bbmri.dao.BiobankDao;
import cz.bbmri.entities.Biobank;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 11.10.12
 * Time: 12:26
 * To change this template use File | Settings | File Templates.
 */

@Repository
public class BiobankDaoImpl extends BasicDaoImpl<Biobank, Long> implements BiobankDao {

    public Biobank getBiobankByAbbreviation(String abbreviation) {
        notNull(abbreviation);
        Query query = em.createQuery("SELECT p FROM Biobank p where p.abbreviation = :abbreviationParam");
        query.setParameter("abbreviationParam", abbreviation);

        try {
            return (Biobank) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

  }
