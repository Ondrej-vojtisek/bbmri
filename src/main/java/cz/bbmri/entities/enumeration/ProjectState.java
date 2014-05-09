package cz.bbmri.entities.enumeration;

/**
 * ProjectState describes present state of project.
 *
 * NEW - Project was just uploaded. Someone (system administrator) must check it for presence of required data and forms.
 * APPROVED - Project was successfully checked by biobank administrator and everything was correct
 * DENIED - Project was checked but there is some issue to solve (missing form, ...)
 * STARTED - Approved project with at least one sample request
 * CANCELED - Project was canceled - it was approved but it can't fill sample requests
 * FINISHED - Project was finished - it can't be changed now. It stays in system as archive.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum ProjectState {

    NEW("new"),
    APPROVED("approved"),
    DENIED("denied"),
    STARTED("started"),
    CANCELED("canceled"),
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
