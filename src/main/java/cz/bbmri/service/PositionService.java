package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Position;
import cz.bbmri.service.simpleService.Update;

/**
 * API for handling Positions
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface PositionService extends Update<Position> {

    /**
     * Return position by its coordinates in given box. Row and column is defined or sequential position.
     *
     * @param box         - where position is searched
     * @param seqPosition - sequential position
     * @param column      - matrix position
     * @param row         - matrix position
     * @return position if exists or null
     */
    Position getByCoordinates(Box box, Integer seqPosition, Integer column, Integer row);

    /**
     * Store position in DB
     *
     * @param position - new instance of position
     * @param boxId - in which box is position located
     * @param sampleId - ID of sample stored at this position
     * @return  instance of Position
     */
    Position create(Position position, Long boxId, Long sampleId);
}
