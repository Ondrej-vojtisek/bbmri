package cz.bbmri.dao.impl;

import cz.bbmri.dao.RequestDao;
import cz.bbmri.entities.Request;
import org.springframework.stereotype.Repository;

/**
 * Implementation for interface handling instances of Request. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
class RequestDaoImpl extends BasicDaoImpl<Request> implements RequestDao {

}
