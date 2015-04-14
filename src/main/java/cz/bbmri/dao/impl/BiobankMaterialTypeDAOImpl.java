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
    
    public BiobankMaterialType get(Biobank biobank, MaterialType materialType) {
            Criterion criterionBiobank = Restrictions.eq(BiobankMaterialType.PROP_BIOBANK, biobank);
            Criterion criterionMaterialType = Restrictions.eq(BiobankMaterialType.PROP_MATERIAL_TYPE, materialType);
    
            // Retrieve a list of existing materialTypes matching the criterion above the list retrieval
            List<BiobankMaterialType> list = getCurrentSession().createCriteria(BiobankMaterialType.class)
                    .add(criterionBiobank)
                    .add(criterionMaterialType)
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
    
            return biobankMaterialType;
    
        }
    
        public void remove(BiobankMaterialType biobankMaterialType){
            getCurrentSession().delete(biobankMaterialType);
        }
}
