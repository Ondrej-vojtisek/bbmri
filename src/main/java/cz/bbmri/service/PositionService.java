package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Position;
import cz.bbmri.service.simpleService.Update;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface PositionService extends Update<Position> {

    Position getByCoordinates(Box box, Integer seqPosition, Integer column, Integer row);

    Position create(Position position, Long boxId, Long sampleId);
}
