package cz.bbmri.entity.comparator.position;

import cz.bbmri.entity.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * Implementation of Position comparator to sort list of positions by its matrix coordinates
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class MatrixComparator implements Comparator<Position> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public int compare(Position position1, Position position2) {

        // Rows are compared primarily
        Integer atr1 = position1.getRow();
        Integer atr2 = position2.getRow();

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

        // it is enough to test only row
        if (atr1.compareTo(atr2) != 0) {
            return atr1.compareTo(atr2);
        }

        // rows are equal -> compare columns

        atr1 = position1.getColumn();
        atr2 = position2.getColumn();

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
