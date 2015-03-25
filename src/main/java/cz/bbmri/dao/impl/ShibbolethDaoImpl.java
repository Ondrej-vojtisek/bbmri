package cz.bbmri.dao.impl;

import cz.bbmri.dao.ShibbolethDAO;
import cz.bbmri.entity.Shibboleth;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository("shibbolethDAO")
@Transactional
public class ShibbolethDAOImpl extends GenericDAOImpl<Shibboleth> implements ShibbolethDAO {

    public Shibboleth get(Long id) {
        return (Shibboleth) getCurrentSession().get(Shibboleth.class, id);
    }

    public Shibboleth get(String eppn, String targeted, String persistent) {

        Criterion criterionEppn = Restrictions.eq(Shibboleth.PROP_EPPN, eppn);
        Criterion criterionTargeted = Restrictions.eq(Shibboleth.PROP_TARGETED, targeted);
        Criterion criterionPersistent = Restrictions.eq(Shibboleth.PROP_PERSISTENT, persistent);

        // Retrieve a list of existing users matching the criterion above the list retrieval
        List<Shibboleth> list = getCurrentSession().createCriteria(Shibboleth.class)
                .add(criterionEppn)
                .add(criterionTargeted)
                .add(criterionPersistent)
                .setMaxResults(1).list();

        // Retrieve iterator from the list
        Iterator iterator = list.iterator();

        // Prepare the variable
        Shibboleth shibboleth = null;

        // If user loaded
        if (iterator.hasNext()) {

            // Store the user instance
            shibboleth = (Shibboleth) iterator.next();
        }
        return shibboleth;
    }
}
