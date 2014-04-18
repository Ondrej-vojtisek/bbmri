package cz.bbmri.entities.enumeration;

/**
 * Description about origination of sample.
 *
 * PREOPERATIONAL - before operation
 * OPERATIONAL - it was taken during operation
 * POST - after operation
 * UNKNOWN - the information is not stored in hospital information system
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum Retrieved {

    PREOPERATIONAL("preoperational"),
    OPERATIONAL("operational"),
    POST("post"),
    UNKNOWN("unknown");

    private final String state;

    private Retrieved(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
