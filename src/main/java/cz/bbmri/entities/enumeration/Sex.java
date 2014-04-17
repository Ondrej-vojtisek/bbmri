package cz.bbmri.entities.enumeration;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum Sex {

    MALE("male"),
    FEMALE("female");

    private final String state;

    private Sex(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
