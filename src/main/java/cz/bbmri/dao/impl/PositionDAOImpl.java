package cz.bbmri.dao.impl;

import cz.bbmri.dao.AbstractDAO;
import cz.bbmri.dao.PositionDAO;
import cz.bbmri.entity.Position;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Repository("positionDAO")
@Transactional
public class PositionDAOImpl extends GenericDAOImpl<Position> implements PositionDAO {

    public Position get(Long id) {
                      return (Position) getCurrentSession().get(Position.class, id);
                  }
}
