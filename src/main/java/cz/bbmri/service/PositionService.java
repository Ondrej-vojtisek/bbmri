package cz.bbmri.service;

import cz.bbmri.entities.infrastructure.Box;
import cz.bbmri.entities.infrastructure.Position;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.3.14
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
public interface PositionService {

    Position getByCoordinates(Box box, Integer seqPosition, Integer column, Integer row);

    Position create(Position position, Long boxId, Long sampleId);

    Position update(Position position);
}
