package cz.bbmri.dao.impl;

import cz.bbmri.dao.PositionDao;
import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Position;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 14.2.14
 * Time: 23:51
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PositionDaoImpl extends BasicDaoImpl<Position, Long> implements PositionDao {

    public Position getByCoordinates(Box box, Integer seqPosition, Integer column, Integer row) {
        notNull(box);
        Query query = null;
        if (seqPosition == null) {
            query = em.createQuery("SELECT p FROM Position p WHERE " +
                    "p.box = :boxParam AND " +
                    "p.row = :rowParam AND " +
                    "p.column = :columnParam ");

            query.setParameter("rowParam", row);
            query.setParameter("columnParam", column);
        } else if (column == null && row == null) {
            query = em.createQuery("SELECT p FROM Position p WHERE " +
                    "p.box = :boxParam AND " +
                    "p.sequentialPosition = :seqParam");
            query.setParameter("seqParam", seqPosition);
        } else {
            logger.debug("Matrix position and sequential position is null");
            return null;
        }
        query.setParameter("boxParam", box);

        try {
            return (Position) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
