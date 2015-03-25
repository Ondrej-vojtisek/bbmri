package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.MaterialTypeDAO;
import cz.bbmri.entity.MaterialType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("materialTypeDAO")
@Transactional
public class MaterialTypeDAOImpl extends GenericDAOImpl<MaterialType> implements MaterialTypeDAO {

    public MaterialType get(Integer id) {
                      return (MaterialType) getCurrentSession().get(MaterialType.class, id);
                  }

}
