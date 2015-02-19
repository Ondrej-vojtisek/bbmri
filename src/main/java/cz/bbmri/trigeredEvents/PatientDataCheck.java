package cz.bbmri.trigeredEvents;

/**
 * Biobank patient data import - periodically executed method(s)
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface PatientDataCheck {

    /**
     * Periodically check PATIENT_DATA_FOLDER of each biobank for new .xml import with patient data (patient and his samples).
     * Each new file is validated and content is parsed into object defined in java BBMRI application .
     * If there was no error during execution than file is moved to folder PATIENT_DATA_ARCHIVE_FOLDER
     * of biobank to be archived.
     * In case of error the execution is stopped and message is put in logger.
     * <p/>
     * Implementation note: method is fired using CRON. It is enough to have annotation @Scheduled(...) before method
     * implementation. But it is necessary to have the method defined also in interface. Otherwise @Scheduled annotation
     * doesn't work.
     * <p/>
     * DON'T DELETE THIS METHOD DESCRIPTION!
     */
    void scheduledPatientDataCheck();

}
