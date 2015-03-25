package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.RetrievedDAO;
import cz.bbmri.entity.Retrieved;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("retrievedDAO")
@Transactional
public class RetrievedDAOImpl extends GenericDAOImpl<Retrieved> implements RetrievedDAO {

    public Retrieved get(Short id) {
                      return (Retrieved) getCurrentSession().get(Retrieved.class, id);
                  }
}
