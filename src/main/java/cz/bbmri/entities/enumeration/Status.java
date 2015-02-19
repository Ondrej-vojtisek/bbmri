package cz.bbmri.entities.enumeration;

/**
 * Status describing results of IO imports.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public enum Status {

    ADDED_NEW("added"),
    CHANGED_CURRENT("changed"),
    UNCHANGED_CURRENT("unchanged"),
    NOT_ADDED("not_added"),
    REMOVED("removed"),
    ERROR("error");

    private final String state;

    private Status(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }

}
