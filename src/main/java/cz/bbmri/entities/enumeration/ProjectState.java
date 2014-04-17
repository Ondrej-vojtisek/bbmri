package cz.bbmri.entities.enumeration;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum ProjectState {

    NEW("new"),
    APPROVED("approved"),
    STARTED("started"),
    CANCELED("canceled"),
    DENIED("denied"),
    FINISHED("finished");
    private final String state;

    private ProjectState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
