package cz.bbmri.dao.impl;

import cz.bbmri.dao.StorageMethologyDAO;
import cz.bbmri.entity.StorageMethology;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository("storageMethologyDAO")
@Transactional
public class StorageMethologyDAOImpl extends GenericDAOImpl<StorageMethology> implements StorageMethologyDAO {

    public StorageMethology get(Long id) {
        return (StorageMethology) getCurrentSession().get(StorageMethology.class, id);
    }
}

