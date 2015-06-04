package cz.bbmri.dao.impl;

import cz.bbmri.dao.ConservationMethodDAO;
import cz.bbmri.dao.ContainerDAO;
import cz.bbmri.entity.ConservationMethod;
import cz.bbmri.entity.Container;
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
@Repository("conservationMethodDAO")
@Transactional
public class ConservationMethodDAOImpl extends GenericDAOImpl<ConservationMethod> implements ConservationMethodDAO {

    public ConservationMethod get(Integer id) {
        return (ConservationMethod) getCurrentSession().get(ConservationMethod.class, id);
    }

    public ConservationMethod getByKey(String key) {
        Criterion criterionKey = Restrictions.eq(ConservationMethod.PROP_KEY, key);

        // Retrieve a list of existing users matching the criterion above the list retrieval
        List<ConservationMethod> list = getCurrentSession().createCriteria(ConservationMethod.class)
                .add(criterionKey)
                .setMaxResults(1).list();

        // Retrieve iterator from the list
        Iterator iterator = list.iterator();

        // Prepare the variable
        ConservationMethod conservationMethod = null;

        // If user loaded
        if (iterator.hasNext()) {

            // Store the user instance
            conservationMethod = (ConservationMethod) iterator.next();
        }
        return conservationMethod;
    }
}
