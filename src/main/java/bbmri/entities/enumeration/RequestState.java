package bbmri.entities.enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 9:57
 * To change this template use File | Settings | File Templates.
 */
public enum RequestState {

    NEW("new"),
    APPROVED("approved"),
    DENIED("denied"),
    DELIVERED("delivered");
    private String state;

    private RequestState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
