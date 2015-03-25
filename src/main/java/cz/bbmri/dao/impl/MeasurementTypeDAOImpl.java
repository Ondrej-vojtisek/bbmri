package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ArchiveDAO;
import cz.bbmri.dao.MeasurementTypeDAO;
import cz.bbmri.entity.Archive;
import cz.bbmri.entity.MeasurementType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("measurementTypeDAO")
@Transactional
public class MeasurementTypeDAOImpl extends GenericDAOImpl<MeasurementType> implements MeasurementTypeDAO {

    public MeasurementType get(Short id) {
                      return (MeasurementType) getCurrentSession().get(MeasurementType.class, id);
                  }
}
