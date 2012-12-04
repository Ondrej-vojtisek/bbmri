package bbmri.entities;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public enum ProjectState {

    NEW("new"),
    APPROVED("approved"),
    STARTED("started"),
    CANCELED("canceled"),
    FINISHED("finished");
    private String state;

    private ProjectState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
