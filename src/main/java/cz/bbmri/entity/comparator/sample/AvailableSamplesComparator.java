package cz.bbmri.entity.comparator.sample;

/**
 * Implementation of Sample comparator to sort list of samples by number of available samples
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class AvailableSamplesComparator /* implements Comparator<Sample> */ {

//    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//
//    public int compare(Sample sample1, Sample sample2) {
//
//        SampleNos atr1 = sample1.getSampleNos();
//        SampleNos atr2 = sample2.getSampleNos();
//
//
//        if (atr1 == null) {
//            if (atr2 == null) {
//                return 0;
//            } else {
//                return Integer.MIN_VALUE;
//            }
//        }
//
//        if (atr2 == null) {
//            return Integer.MAX_VALUE;
//        }
//
//        if (atr1.getAvailableSamplesNo() == null) {
//            if (atr2.getAvailableSamplesNo() == null) {
//                return 0;
//            } else {
//                return Integer.MIN_VALUE;
//            }
//        }
//
//        if (atr2.getAvailableSamplesNo() == null) {
//            return Integer.MAX_VALUE;
//        }
//
//        return atr1.getAvailableSamplesNo().compareTo(atr2.getAvailableSamplesNo());
//    }
}
