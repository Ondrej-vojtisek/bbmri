package cz.bbmri.trigeredEvents;

/**
 * Check of reservation validity - periodically executed method(s)
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface ReservationValidityCheck {

    /**
     * Periodically checks if all reservations present in DB are still valid. If reservation is exceeds its validity, than
     * the state is changed to expired and all allocated samples are freed.
     * <p/>
     * Implementation note: method is fired using CRON. It is enough to have annotation @Scheduled(...) before method
     * implementation. But it is necessary to have the method defined also in interface. Otherwise @Scheduled annotation
     * doesn't work.
     * <p/>
     * DON'T DELETE THIS METHOD DESCRIPTION!
     */
    void checkReservationValidity();
}
