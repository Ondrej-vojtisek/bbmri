package cz.bbmri.entity.comparator.sample;

/**
 * Implementation of Sample comparator to sort list of samples by material type
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class MaterialTypeComparator /* implements Comparator<Sample> */{

//    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//
//     public int compare(Sample sample1, Sample sample2) {
//
//         MaterialType atr1 = sample1.getMaterialType();
//         MaterialType atr2 = sample2.getMaterialType();
//
//
//         if (atr1 == null) {
//             if (atr2 == null) {
//                 return 0;
//             } else {
//                 return Integer.MIN_VALUE;
//             }
//         }
//
//         if (atr2 == null) {
//             return Integer.MAX_VALUE;
//         }
//
//         if (atr1.getType() == null) {
//             if (atr2.getType() == null) {
//                 return 0;
//             } else {
//                 return Integer.MIN_VALUE;
//             }
//         }
//
//         if (atr2.getType() == null) {
//             return Integer.MAX_VALUE;
//         }
//
//         return atr1.getType().compareTo(atr2.getType());
//     }
}
