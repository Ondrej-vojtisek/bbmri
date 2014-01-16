package cz.bbmri.entities.enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 8.1.14
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 */
public enum Sex {

    MALE("male"),
    FEMALE("female");

    private String state;

    private Sex(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
