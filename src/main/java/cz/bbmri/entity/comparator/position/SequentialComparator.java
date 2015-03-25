package cz.bbmri.entity.comparator.position;

import cz.bbmri.entity.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * Implementation of Position comparator to sort list of positions by its sequential order
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class SequentialComparator implements Comparator<Position> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public int compare(Position position1, Position position2) {

        Integer atr1 = position1.getSequentialPosition();
        Integer atr2 = position2.getSequentialPosition();

        if (atr1 == null) {
            if (atr2 == null) {
                return 0;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        if (atr2 == null) {
            return Integer.MAX_VALUE;
        }

        return atr1.compareTo(atr2);
    }
}
