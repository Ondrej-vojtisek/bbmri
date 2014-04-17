package cz.bbmri.dao.impl;

import cz.bbmri.dao.PositionDao;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Position;
import org.springframework.stereotype.Repository;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */

@Repository
public class PositionDaoImpl extends BasicDaoImpl<Position, Long> implements PositionDao {

    public Position getByCoordinates(Box box, Integer seqPosition, Integer column, Integer row) {
        notNull(box);
        if (seqPosition == null) {
            typedQuery = em.createQuery("SELECT p FROM Position p WHERE " +
                    "p.box = :boxParam AND " +
                    "p.row = :rowParam AND " +
                    "p.column = :columnParam ", Position.class);

            typedQuery.setParameter("rowParam", row);
            typedQuery.setParameter("columnParam", column);
        } else if (column == null && row == null) {
            typedQuery = em.createQuery("SELECT p FROM Position p WHERE " +
                    "p.box = :boxParam AND " +
                    "p.sequentialPosition = :seqParam", Position.class);
            typedQuery.setParameter("seqParam", seqPosition);
        } else {
            logger.debug("Matrix position and sequential position is null");
            return null;
        }
        typedQuery.setParameter("boxParam", box);

        return getSingleResult();
    }
}
