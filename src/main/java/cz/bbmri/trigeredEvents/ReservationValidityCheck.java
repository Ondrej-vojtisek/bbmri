package cz.bbmri.trigeredEvents;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 17.4.14
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public interface ReservationValidityCheck {

    // CRON triggered
    void checkReservationValidity();
}
