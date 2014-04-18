package cz.bbmri.entities.enumeration;

/**
 * Defines levels of permission which can be given to user access project or biobank.
 *
 * MANAGER - Can do everything including changing permissions of other administrators. If user uploads project than he is
 * a manager by default. If user is selected as a first administrator of biobank than he is a manager.
 * EDITOR - Can change changeable attributes of object (e.g. project specification, ...). Can do everything what EXECUTOR
 * can
 * EXECUTOR - Can execute confirmation or denial. For instance confirm uploaded project etc.
 * VISITOR - Can only access object, but can't change anything. This permission is only for presentation - for instance
 * when we want to show someone possibilities of system.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum Permission {

    MANAGER("manager"),
    EDITOR("editor"),
    EXECUTOR("executor"),
    VISITOR("visitor");

    private final String state;

    private Permission(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state;
    }

    /**
     * Checks if given permission this.permission includes given permission. Visitor is included in any permission,
     * executor is included in editor and manager etc...
     *
     * @param permission
     * @return true if this.permission includes given permission
     */
    public boolean include(Permission permission) {
        if(permission == null){
            return false;
        }

        switch (permission) {
            case VISITOR:
                return true;

            case EXECUTOR:
                return !this.equals(Permission.VISITOR);

            case EDITOR:
                return !this.equals(Permission.VISITOR)
                        && !this.equals(Permission.EXECUTOR);

            case MANAGER:
                return this.equals(Permission.MANAGER);
        }
        /* This could not happen*/
        return false;
    }

    public static int compare(Permission e1, Permission e2) {
        if (e1 == e2) {
            return 0;
        }
        // order is based on include
        if (e1.include(e2)){
            return 1;
        }
        return -1;
    }
}
