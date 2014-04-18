package cz.bbmri.dao.impl;

import cz.bbmri.dao.PositionDao;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Position;
import org.springframework.stereotype.Repository;

/**
 * Implementation for interface handling instances of Position. Implementation is using JPQL.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Repository
public class PositionDaoImpl extends BasicDaoImpl<Position> implements PositionDao {

    public Position getByCoordinates(Box box, Integer seqPosition, Integer column, Integer row) {
        notNull(box);

        if (seqPosition == null) {
            // position is defined by row and column
            typedQuery = em.createQuery("SELECT p FROM Position p WHERE " +
                    "p.box = :boxParam AND " +
                    "p.row = :rowParam AND " +
                    "p.column = :columnParam ", Position.class);

            typedQuery.setParameter("rowParam", row);
            typedQuery.setParameter("columnParam", column);
        } else if (column == null && row == null) {
            // position is defined by its sequential order
            typedQuery = em.createQuery("SELECT p FROM Position p WHERE " +
                    "p.box = :boxParam AND " +
                    "p.sequentialPosition = :seqParam", Position.class);
            typedQuery.setParameter("seqParam", seqPosition);
        } else {
            // none is defined - exception
            throw new IllegalArgumentException("Matrix position and sequential position is null");
        }
        typedQuery.setParameter("boxParam", box);

        return getSingleResult();
    }
}
