package cz.bbmri.entities.enumeration;

/**
 * Type of attachments which can be uploaded to project. Not all are required from oficial system requirements.
 * PATIENT_AGREEMENT            - Patient agreement asociated with project
 * ETHICAL_AGREEMENT            - Result of ethical commitee about uploaded project.
 * MATERIAL_TRANSFER_AGREEMENT  - Required form needed to upload project into BBMRI
 * RESULT                       - Any document which is shared among research team
 * OTHER                        - Any other kind of file
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum ProjectAttachmentType {

    ETHICAL_AGREEMENT("ethical"),
    MATERIAL_TRANSFER_AGREEMENT("mta"),
    RESULT("result"),
    OTHER("other");

    private final String state;

    private ProjectAttachmentType(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
