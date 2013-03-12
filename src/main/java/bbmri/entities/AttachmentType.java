package bbmri.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 4.3.13
 * Time: 11:14
 * To change this template use File | Settings | File Templates.
 */
public enum AttachmentType {
    PATIENT_AGREEMENT("_patient"),
    ETHICAL_AGREEMENT("_ethical"),
    MATERIAL_TRANSFER_AGREEMENT("_mta"),
    RESULT("_result"),
    OTHER("_other");
    private String state;

    private AttachmentType(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
