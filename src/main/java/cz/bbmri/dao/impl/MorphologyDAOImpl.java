package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractCompositeDAO;
import cz.bbmri.dao.MorphologyDAO;
import cz.bbmri.entity.Morphology;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("morphologyDAO")
@Transactional
public class MorphologyDAOImpl extends GenericDAOImpl<Morphology> implements MorphologyDAO {
}
