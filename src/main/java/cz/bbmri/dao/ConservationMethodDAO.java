package cz.bbmri.dao;

import cz.bbmri.entity.ConservationMethod;
import cz.bbmri.entity.Container;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface ConservationMethodDAO extends AbstractDAO<ConservationMethod, Integer> {

    ConservationMethod getByKey(String key);

}
