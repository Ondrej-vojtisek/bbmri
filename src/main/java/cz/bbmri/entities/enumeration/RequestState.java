package cz.bbmri.entities.enumeration;

/**
 * RequestState describes present state of sample request or sample reservation.
 *
 * NEW - SampleRequest or SampleReservation was created
 * APPROVED - biobank administrator approved request/reservation - it means that bioban can fullfill the request
 * DENIED - biobank can't fullfill request
 * CLOSED - set of samples was attached to request/reservation and this set was finished
 * AGREED - check of project worker whether the set of samples fullfill his requirements  - set agreed
 *        - if not agreed then change the state info CLOSED again
 * DELIVERED - sample set was delivered to reseacher
 * EXPIRED - when reservation is older than expiration date
 *
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum RequestState {

    NEW("new"),
    APPROVED("approved"),
    DENIED("denied"),
    CLOSED("closed"),
    AGREED("agreed"),
    DELIVERED("delivered"),
    EXPIRED("expired");

    private final String state;

    private RequestState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }
}
