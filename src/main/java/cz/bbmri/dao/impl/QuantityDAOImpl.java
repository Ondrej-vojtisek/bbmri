package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.QuantityDAO;
import cz.bbmri.entity.Quantity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("quantityDAO")
@Transactional
public class QuantityDAOImpl extends GenericDAOImpl<Quantity> implements QuantityDAO {
}
