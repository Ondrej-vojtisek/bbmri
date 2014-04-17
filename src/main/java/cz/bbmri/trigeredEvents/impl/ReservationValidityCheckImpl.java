package cz.bbmri.trigeredEvents.impl;

import cz.bbmri.dao.NotificationDao;
import cz.bbmri.entities.Request;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.RequestState;
import cz.bbmri.service.RequestService;
import cz.bbmri.service.SampleReservationService;
import cz.bbmri.trigeredEvents.ReservationValidityCheck;
import net.sourceforge.stripes.action.LocalizableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * TODO
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

    // triggers at 0:10 each day
    @Scheduled(cron = "10 0 * * * *")
    public void checkReservationValidity() {
        logger.debug("CRON - checkReservationValidity auto triggered at: " + new Date());

        Date date = new Date();
        boolean firstValid = false;
        for (SampleReservation sampleReservation : sampleReservationService.getSampleReservationsOrderedByDate()) {
            // if this date (today) is after sampleReservation.getValidity
            // then validity is over

            if (firstValid) break;

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
        sampleReservation.setRequestState(RequestState.EXPIRED);
        // if succ
        if (sampleReservationService.setAsExpired(sampleReservation.getId())) {

            // delete all request - alocated samples are free
            for (Request request : sampleReservation.getRequests()) {
                requestService.remove(request.getId(), null);
            }
        }
        else{
            logger.error("Set reservation as expired failed");
        }
    }
}
