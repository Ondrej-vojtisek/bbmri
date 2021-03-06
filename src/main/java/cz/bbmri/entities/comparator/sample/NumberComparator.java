package cz.bbmri.entities.comparator.sample;

import cz.bbmri.entities.sample.Sample;
import cz.bbmri.entities.sample.field.SampleIdentification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * Implementation of Sample comparator to sort list of samples by number of bioptical report
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class NumberComparator implements Comparator<Sample> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public int compare(Sample sample1, Sample sample2) {

        SampleIdentification atr1 = sample1.getSampleIdentification();
        SampleIdentification atr2 = sample2.getSampleIdentification();

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

        if (atr1.getNumber() == null) {
            if (atr2.getNumber() == null) {
                return 0;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        if (atr2.getNumber() == null) {
            return Integer.MAX_VALUE;
        }

        return atr1.getNumber().compareTo(atr2.getNumber());
    }
}
