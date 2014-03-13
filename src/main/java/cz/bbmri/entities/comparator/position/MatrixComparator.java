package cz.bbmri.entities.comparator.position;

import cz.bbmri.entities.infrastructure.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class MatrixComparator implements Comparator<Position> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public int compare(Position position1, Position position2) {

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

        // rows are equal

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
