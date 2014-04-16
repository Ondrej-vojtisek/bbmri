package cz.bbmri.entities.enumeration;

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
    CLOSED("closed"),
    AGREED("agreed"),
    DELIVERED("delivered"),
    EXPIRED("expired");

    /**
     * Workflow - project worker creates sample request for the project.
     * It can be approved or denied. If approved than biobank operator prepares
     * sample group - e.g. set of samples which will be released
     * If this set is prepared then change to prepared
     * If samples were given to applicant then change state to delivered
     * */

    /**
     * Maybe canceled state would be also useful ... pokud zademe dve banky o stejne vzorky
     * obe pripravi sadu vzorku a nam staci jedna.. abychom to mohli zrusit.
     * Pripadne ze navrzena sada neni dostatecna pro nas vyzkum
     * */

    private final String state;

    private RequestState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
