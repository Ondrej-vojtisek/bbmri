package cz.bbmri.entities.enumeration;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum AttachmentType {
    PATIENT_AGREEMENT("patient"),
    ETHICAL_AGREEMENT("ethical"),
    MATERIAL_TRANSFER_AGREEMENT("mta"),
    RESULT("result"),
    OTHER("other");

    private final String state;

    private AttachmentType(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
