package cz.bbmri.dao.impl;

import cz.bbmri.dao.RequestDao;
import cz.bbmri.entities.Request;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */

@Repository
class RequestDaoImpl extends BasicDaoImpl<Request, Long> implements RequestDao {

}
