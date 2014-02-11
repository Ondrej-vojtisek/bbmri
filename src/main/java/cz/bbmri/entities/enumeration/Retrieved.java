package cz.bbmri.entities.enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 11.2.14
 * Time: 13:09
 * To change this template use File | Settings | File Templates.
 */
public enum Retrieved {

    PREOPERATIONAL("preoperational"),
    OPERATIONAL("operational"),
    POST("post"),
    UNKNOWN("unknown");

    private String state;

    private Retrieved(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
