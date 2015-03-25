package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.TnmDAO;
import cz.bbmri.entity.Tnm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("tnmDAO")
@Transactional
public class TnmDAOImpl extends GenericDAOImpl<Tnm> implements TnmDAO {

    public Tnm get(Long id) {
                      return (Tnm) getCurrentSession().get(Tnm.class, id);
                  }
}
