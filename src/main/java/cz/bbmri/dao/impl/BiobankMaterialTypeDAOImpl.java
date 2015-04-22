package cz.bbmri.dao.impl;

import cz.bbmri.dao.BiobankMaterialTypeDAO;
import cz.bbmri.entity.Biobank;
import cz.bbmri.entity.BiobankMaterialType;
import cz.bbmri.entity.MaterialType;
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
@Repository("biobankMaterialTypeDAO")
@Transactional
public class BiobankMaterialTypeDAOImpl extends GenericDAOImpl<BiobankMaterialType> implements BiobankMaterialTypeDAO {

    public MaterialType get(Biobank biobank, String key) {
        Criterion criterionBiobank = Restrictions.eq(BiobankMaterialType.PROP_BIOBANK, biobank);
        Criterion criterionKey = Restrictions.eq(BiobankMaterialType.PROP_KEY, key);

        // Retrieve a list of existing materialTypes matching the criterion above the list retrieval
        List<BiobankMaterialType> list = getCurrentSession().createCriteria(BiobankMaterialType.class)
                .add(criterionBiobank)
                .add(criterionKey)
                .setMaxResults(1).list();

        // Retrieve iterator from the list
        Iterator iterator = list.iterator();

        // Prepare the variable
        BiobankMaterialType biobankMaterialType = null;

        // If materialType loaded
        if (iterator.hasNext()) {

            // Store the materialType instance
            biobankMaterialType = (BiobankMaterialType) iterator.next();
        }

        if (biobankMaterialType == null) {

            System.err.println("BiobankMaterialType");

            return null;
        }

        System.err.println("Searched materialType: " + biobankMaterialType.getMaterialType());

        return biobankMaterialType.getMaterialType();

    }

    public void remove(BiobankMaterialType biobankMaterialType) {
        getCurrentSession().delete(biobankMaterialType);
    }
}
