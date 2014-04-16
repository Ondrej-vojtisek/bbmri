package cz.bbmri.entities.enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 18.9.13
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public enum Permission {

    /*
    * MANAGER - can do everything including changing permissions of other administrators.
    *           If user creates biobank he is MANAGER by default.
    * EDITOR - can change attributes of biobank e.g. name, address
    * CONFIRM - can confirm requests and projects
    * VISITOR - can't do anything, except the access of biobank administrators pages
    * */

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


    /*
    * Checks if this includes permission of _permission_. Manager include all, visitor include only visitor etc.
    * */
    public boolean include(Permission permission) {

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
        if (e1.include(e2)){
            return 1;
        }

        return -1;
    }
}
