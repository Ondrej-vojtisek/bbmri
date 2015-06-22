package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.BiobankUserDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.BiobankUser;
import cz.bbmri.entity.Permission;
import cz.bbmri.entity.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("biobankUserDAO")
@Transactional
public class BiobankUserDAOImpl extends GenericDAOImpl<BiobankUser> implements BiobankUserDAO {

    public BiobankUser get(Biobank biobank, User user) {
        Criterion criterionBiobank = Restrictions.eq(BiobankUser.PROP_BIOBANK, biobank);
        Criterion criterionUser = Restrictions.eq(BiobankUser.PROP_USER, user);

        // Retrieve a list of existing users matching the criterion above the list retrieval
        List<BiobankUser> list = getCurrentSession().createCriteria(BiobankUser.class)
                .add(criterionBiobank)
                .add(criterionUser)
                .setMaxResults(1).list();

        // Retrieve iterator from the list
        Iterator iterator = list.iterator();

        // Prepare the variable
        BiobankUser biobankUser = null;

        // If user loaded
        if (iterator.hasNext()) {

            // Store the user instance
            biobankUser = (BiobankUser) iterator.next();
        }

        return biobankUser;

    }

    public void remove(BiobankUser biobankUser){
        getCurrentSession().delete(biobankUser);
    }

    public boolean hasPermission(Permission permission, Biobank biobank, User user) {
        BiobankUser biobankUser = get(biobank, user);

        if (biobankUser == null) {
            return false;
        }

        return biobankUser.getPermission().include(permission);
    }

}
