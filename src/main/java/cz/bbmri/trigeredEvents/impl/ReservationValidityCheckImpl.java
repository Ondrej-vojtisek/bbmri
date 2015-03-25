package cz.bbmri.trigeredEvents.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Transactional
@Service("reservationValidityCheck")
public class ReservationValidityCheckImpl /* extends Basic implements ReservationValidityCheck */ {

//    @Autowired
//    private NotificationDao notificationDao;

    /**
     * Method should be fired once per day - 10 minutes after midnight.
     * Scheduled(cron = "10 0 * * * *")
     * For testing purpose is better to fire the method each minute.
     */

//    //TODO
//  //  @Scheduled(cron = "1 * * * * *")
//    public void checkReservationValidity() {
//        log("CRON - checkReservationValidity auto triggered at: " + new Date());
//
//        Date date = new Date();
//        boolean firstValid = false;
//        for (Reservation reservation : sampleReservationService.getSampleReservationsOrderedByDate()) {
//
//            /* collection of reservations is sorted by date - so if first one (the oldest one) is valid, than all
//               reservations will be valid and method execution can be ended
//            */
//            if (firstValid) break;
//
//            // if date is after reservation validity than validity is expired
//            if (date.after(reservation.getValidation())) {
//
//                setReservationAsExpired(reservation);
//
//                LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.AutoTriggeredOperationsImpl.reservationExpired");
//
//                // TODO
////                notificationDao.create(sampleReservation.getUser(),
////                        NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleReservation.getId());
//            } else {
//                // reservations are sorted from oldest to newest
//                // if first one is valid, that all next will be also valid
//                firstValid = true;
//            }
//        }
//    }
//
//
//    private void setReservationAsExpired(Reservation reservation) {
//        // if succ
//        if (sampleReservationService.setAsExpired(sampleReservation.getId())) {
//
//            // delete all request - alocated samples are free
//            for (Request request : reservation.getRequest()) {
//                requestDao.remove(request);
//            }
//        } else {
//            logger.error("Set reservation as expired failed");
//        }
//    }
}
