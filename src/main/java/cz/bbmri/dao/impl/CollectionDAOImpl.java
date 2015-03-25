package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.CollectionDAO;
import cz.bbmri.entity.Collection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("collectionDAO")
@Transactional
public class CollectionDAOImpl extends GenericDAOImpl<Collection> implements CollectionDAO {

    public Collection get(Long id) {
                      return (Collection) getCurrentSession().get(Collection.class, id);
                  }
}
