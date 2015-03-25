package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.SampleDAO;
import cz.bbmri.entity.Sample;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("sampleDAO")
@Transactional
public class SampleDAOImpl extends GenericDAOImpl<Sample> implements SampleDAO {

    public Sample get(Long id) {
                      return (Sample) getCurrentSession().get(Sample.class, id);
                  }

}
