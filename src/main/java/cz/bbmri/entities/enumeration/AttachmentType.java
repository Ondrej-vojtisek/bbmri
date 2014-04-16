package cz.bbmri.entities.enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.3.13
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
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
