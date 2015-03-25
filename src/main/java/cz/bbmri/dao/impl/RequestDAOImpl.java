package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.RequestDAO;
import cz.bbmri.entity.Request;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("requestDAO")
@Transactional
public class RequestDAOImpl extends GenericDAOImpl<Request> implements RequestDAO {

    public Request get(Long id) {
                      return (Request) getCurrentSession().get(Request.class, id);
                  }
}
