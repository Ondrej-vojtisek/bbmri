package cz.bbmri.facade;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 10.4.14
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public interface AutoTriggeredOperations {

    // CRON triggered
    void checkBiobankPatientData();

    // CRON triggered
    void checkBiobankMonitoringData();

    // CRON triggered
    void checkReservationValidity();
}
