package cz.bbmri.entity.comparator;


/**
 * Implementation of BiobankAdministrator comparator to sort list of administrators by importance of its permission
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class PermissionUserComparator /* implements Comparator<Object> */ {

//    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//
//       public int compare(Object admin1, Object admin2) {
//
//           Permission atr1 = null;
//           Permission atr2 = null;
//
//           boolean instanceOf = false;
//
//           if (admin1 instanceof BiobankAdministrator
//                   && admin2 instanceof BiobankAdministrator) {
//               atr1 = ((BiobankAdministrator) admin1).getPermission();
//               atr2 = ((BiobankAdministrator) admin2).getPermission();
//               instanceOf = true;
//           }
//
//           if (admin1 instanceof ProjectAdministrator
//                   && admin2 instanceof ProjectAdministrator) {
//               instanceOf = true;
//               atr1 = ((ProjectAdministrator) admin1).getPermission();
//               atr2 = ((ProjectAdministrator) admin2).getPermission();
//           }
//
//           if(!instanceOf){
//               logger.debug("Compared objects are null!");
//               throw new ClassCastException("Objects are not comparable with PermissionComparator");
//           }
//
//           if (atr1 == null) {
//               if (atr2 == null) {
//                   return 0;
//               } else {
//                   return Integer.MIN_VALUE;
//               }
//           }
//
//           if (atr2 == null) {
//               return Integer.MAX_VALUE;
//           }
//
//           return atr1.compareTo(atr2);
//
//       }
}
