package cz.bbmri.dao.impl;

import cz.bbmri.dao.StorageMethodologyDAO;
import cz.bbmri.entity.StorageMethodology;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository("storageMethodologyDAO")
@Transactional
public class StorageMethodologyDAOImpl extends GenericDAOImpl<StorageMethodology> implements StorageMethodologyDAO {

    public StorageMethodology get(Long id) {
        return (StorageMethodology) getCurrentSession().get(StorageMethodology.class, id);
    }
}

