package cz.bbmri.trigeredEvents.impl;

import cz.bbmri.dao.NotificationDao;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.service.RequestService;
import cz.bbmri.service.SampleReservationService;
import cz.bbmri.trigeredEvents.ReservationValidityCheck;
import net.sourceforge.stripes.action.LocalizableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class ReservationValidityCheckImpl extends Basic implements ReservationValidityCheck {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private SampleReservationService sampleReservationService;

    @Autowired
    private RequestService requestService;

    /**
     * Method should be fired once per day - 10 minutes after midnight.
     * Scheduled(cron = "10 0 * * * *")
     * For testing purpose is better to fire the method each minute.
     */

    @Scheduled(cron = "1 * * * * *")
    public void checkReservationValidity() {
        log("CRON - checkReservationValidity auto triggered at: " + new Date());

        Date date = new Date();
        boolean firstValid = false;
        for (SampleReservation sampleReservation : sampleReservationService.getSampleReservationsOrderedByDate()) {

            /* collection of reservations is sorted by date - so if first one (the oldest one) is valid, than all
               reservations will be valid and method execution can be ended
            */
            if (firstValid) break;

            // if date is after reservation validity than validity is expired
            if (date.after(sampleReservation.getValidity())) {

                setReservationAsExpired(sampleReservation);

                LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.AutoTriggeredOperationsImpl.reservationExpired");

                notificationDao.create(sampleReservation.getUser(),
                        NotificationType.SAMPLE_REQUEST_DETAIL, locMsg, sampleReservation.getId());
            } else {
                // reservations are sorted from oldest to newest
                // if first one is valid, that all next will be also valid
                firstValid = true;
            }
        }
    }


    private void setReservationAsExpired(SampleReservation sampleReservation) {
        // if succ
        if (sampleReservationService.setAsExpired(sampleReservation.getId())) {

            // delete all request - alocated samples are free
            for (Request request : sampleReservation.getRequests()) {
                requestService.remove(request.getId(), null);
            }
        } else {
            logger.error("Set reservation as expired failed");
        }
    }
}
