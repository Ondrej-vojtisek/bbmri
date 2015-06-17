package cz.bbmri.dao.impl;

import cz.bbmri.dao.MaterialTypeDAO;
import cz.bbmri.entity.MaterialType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    @Transactional(readOnly = true)
    public List<MaterialType> all() {
        Criteria criteria = getCurrentSession().createCriteria(MaterialType.class);
        criteria.addOrder(Order.asc("name"));
        return criteria.list();
    }


}
