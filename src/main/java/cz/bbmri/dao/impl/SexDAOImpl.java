package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.SexDAO;
import cz.bbmri.entity.Sex;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("sexDAO")
@Transactional
public class SexDAOImpl extends GenericDAOImpl<Sex> implements SexDAO {

    public Sex get(Integer id) {
                      return (Sex) getCurrentSession().get(Sex.class, id);
                  }

}
