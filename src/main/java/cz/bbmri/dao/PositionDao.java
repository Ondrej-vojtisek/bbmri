package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Position;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface PositionDao extends BasicDao<Position> {

    Position getByCoordinates(Box box, Integer seqPosition, Integer column, Integer row);

}
