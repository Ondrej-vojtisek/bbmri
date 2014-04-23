package cz.bbmri.trigeredEvents;

/**
 * Biobank monitoring import - periodically executed method(s)
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface MonitoringDataCheck {

    /**
     * Periodically check MONITORING_DATA_FOLDER of each biobank for new .xml import about biobank occupancy.
     * Each new file is validated and content is parsed into object defined in java BBMRI application.
     * If there was no error during execution than file is moved to folder MONITORING_DATA_ARCHIVE_FOLDER
     * of biobank to be archived.
     * In case of error the execution is stopped and message is put in logger.
     * <p/>
     * Implementation note: method is fired using CRON. It is enough to have annotation @Scheduled(...) before method
     * implementation. But it is necessary to have the method defined also in interface. Otherwise @Scheduled annotation
     * doesn't work.
     * <p/>
     * DON'T DELETE THIS METHOD DESCRIPTION!
     */
    void checkBiobankMonitoringData();

}
