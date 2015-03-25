package cz.bbmri.dao.impl;

import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.entity.Biobank;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for class handling instances of Biobank. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */


@Repository("biobankDAO")
@Transactional
public class BiobankDAOImpl extends GenericDAOImpl<Biobank> implements BiobankDAO {

    public Biobank get(Integer id) {
                return (Biobank) getCurrentSession().get(Biobank.class, id);
            }
//
//    public Biobank getBiobankByAbbreviation(String abbreviation) {
//        notNull(abbreviation);
//        typedQuery = em.createQuery("SELECT p FROM Biobank p where p.abbreviation = :abbreviationParam", Biobank.class);
//        typedQuery.setParameter("abbreviationParam", abbreviation);
//
//        return getSingleResult();
//    }

  }
