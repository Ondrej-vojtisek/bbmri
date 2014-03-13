package cz.bbmri.entities.comparator.sample;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.field.SampleNos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */
public class TotalSampleNumberComparator implements Comparator<Sample> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public int compare(Sample sample1, Sample sample2) {

        SampleNos atr1 = sample1.getSampleNos();
        SampleNos atr2 = sample2.getSampleNos();


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

        if (atr1.getSamplesNo() == null) {
            if (atr2.getSamplesNo() == null) {
                return 0;
            } else {
                return Integer.MIN_VALUE;
            }
        }

        if (atr2.getSamplesNo() == null) {
            return Integer.MAX_VALUE;
        }

        return atr1.getSamplesNo().compareTo(atr2.getSamplesNo());
    }
}
