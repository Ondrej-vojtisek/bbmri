package cz.bbmri.entities.enumeration;

/**
 * Type of attachments which can be uploaded to biobank.
 * CALIBRATION_PROTOCOL            - Calibration report
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum BiobankAttachmentType {

    CALIBRATION_PROTOCOL("calibration");

    private final String state;

    private BiobankAttachmentType(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }

}
