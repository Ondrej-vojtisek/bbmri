package cz.bbmri.dao.impl;

import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.MaterialType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Biobank getByInstitutionalId(String id) {
        Criterion criterionInstitutionalId = Restrictions.eq(Biobank.PROP_INSTITUTIONAL_ID, id);

        // Retrieve a list of existing biobanks matching the criterion above the list retrieval
        List<Biobank> list = getCurrentSession().createCriteria(Biobank.class)
                .add(criterionInstitutionalId)
                .setMaxResults(1).list();

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<Biobank> all() {
//        Criteria criteria = getCurrentSession().createCriteria(Biobank.class);
//        criteria.addOrder(Order.asc("acronym"));
//        return criteria.list();
//    }

}
