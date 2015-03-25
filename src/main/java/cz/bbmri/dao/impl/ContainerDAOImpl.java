package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.ContainerDAO;
import cz.bbmri.entity.Container;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("containerDAO")
@Transactional
public class ContainerDAOImpl extends GenericDAOImpl<Container> implements ContainerDAO {

    public Container get(Long id) {
                      return (Container) getCurrentSession().get(Container.class, id);
                  }
}
