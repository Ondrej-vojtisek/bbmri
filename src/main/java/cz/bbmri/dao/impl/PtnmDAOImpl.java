package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.PtnmDAO;
import cz.bbmri.entity.Ptnm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("ptnmDAO")
@Transactional
public class PtnmDAOImpl extends GenericDAOImpl<Ptnm> implements PtnmDAO {
}
