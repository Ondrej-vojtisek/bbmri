package cz.bbmri.entity.comparator;


/**
 * Implementation of BiobankAdministrator comparator
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class PermissionComparator /* implements Comparator<Object> */ {

//    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//
//    public int compare(Object admin1, Object admin2) {
//
//        User atr1 = null;
//        User atr2 = null;
//
//        boolean instanceOf = false;
//
//        if (admin1 instanceof BiobankAdministrator
//                && admin2 instanceof BiobankAdministrator) {
//            atr1 = ((BiobankAdministrator) admin1).getUser();
//            atr2 = ((BiobankAdministrator) admin2).getUser();
//            instanceOf = true;
//        }
//
//        if (admin1 instanceof ProjectAdministrator
//                && admin2 instanceof ProjectAdministrator) {
//            instanceOf = true;
//            atr1 = ((ProjectAdministrator) admin1).getUser();
//            atr2 = ((ProjectAdministrator) admin2).getUser();
//        }
//
//        if (!instanceOf) {
//            logger.debug("Compared objects are null!");
//            throw new ClassCastException("Objects are not comparable with PermissionComparator");
//        }
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
////        if (atr1.getSurname() == null) {
////            if (atr2.getSurname() == null) {
////                return 0;
////            } else {
////                return Integer.MIN_VALUE;
////            }
////        }
////
////        if (atr2.getSurname() == null) {
////            return Integer.MAX_VALUE;
////        }
////
////        return atr1.getSurname().compareTo(atr2.getSurname());
//
//        // TODO
//        return -1;
//    }
}
