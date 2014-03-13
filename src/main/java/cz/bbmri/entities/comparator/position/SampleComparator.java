package cz.bbmri.entities.comparator.position;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.infrastructure.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public class SampleComparator implements Comparator<Position> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public int compare(Position position1, Position position2) {

        Sample atr1 = position1.getSample();
        Sample atr2 = position2.getSample();

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
