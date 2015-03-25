package cz.bbmri.dao;

import cz.bbmri.entity.Position;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface PositionDAO extends AbstractDAO<Position, Long> {

    /**
      * Return Position by its coordinates. Position can be defined sequentially or by combination of column and row.
      * Coordinates are unique inside each box.
      *
      * @param box         - Which box is searched for position
      * @param seqPosition - If the position is defined sequentionally
      * @param column      - column position if the position is defined by matrix
      * @param row         - row position if the position is defined by matrix
      * @return Position with given coordinates (found in given box) or null
      */
//     Position getByCoordinates(Box box, Integer seqPosition, Integer column, Integer row);

}
