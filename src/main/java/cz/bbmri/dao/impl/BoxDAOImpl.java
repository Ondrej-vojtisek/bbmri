package cz.bbmri.dao.impl;

import cz.bbmri.dao.BoxDAO;
import cz.bbmri.entity.Box;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("boxDAO")
@Transactional
public class BoxDAOImpl extends GenericDAOImpl<Box> implements BoxDAO {

    public Box get(Long id) {
                      return (Box) getCurrentSession().get(Box.class, id);
                  }
}
