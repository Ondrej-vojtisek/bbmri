/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 *
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 *
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Masaryk University
 * License Type: Academic
 */
package cz.bbmri.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Permission implements Serializable {

    public static final String FOLDER = "permission";

    public Permission() {
    }

    public Permission(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //    Must match the predefined table records from visual paradigm project

    public static final Permission MANAGER = new Permission(1, "manager");
    public static final Permission EDITOR = new Permission(2, "editor");
    public static final Permission EXECUTOR = new Permission(3, "executor");
    public static final Permission VISITOR = new Permission(4, "visitor");

    public static final String PROP_ID = "id";
    public static final String PROP_NAME = "name";
    public static final String PROP_BIOBANK_USER = "biobankUser";
    public static final String PROP_PROJECT_USER = "projectUser";

    private int id;
    private String name;
    private Set<BiobankUser> biobankUser = new HashSet<BiobankUser>();
    private Set<ProjectUser> projectUser = new HashSet<ProjectUser>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BiobankUser> getBiobankUser() {
        return biobankUser;
    }

    public void setBiobankUser(Set<BiobankUser> biobankUser) {
        this.biobankUser = biobankUser;
    }

    public Set<ProjectUser> getProjectUser() {
        return projectUser;
    }

    public void setProjectUser(Set<ProjectUser> projectUser) {
        this.projectUser = projectUser;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Permission)) return false;

        Permission that = (Permission) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    /**
     * Checks if given this.permission includes given permission. Visitor is included in any permission,
     * executor is included in editor and manager etc...
     *
     * @param permission
     * @return true if this.permission includes given permission
     */
    public boolean include(Permission permission) {

        if (permission == null) {
            return false;
        }

        if (permission.equals(VISITOR)) {
            return true;
        }

        if (permission.equals(EXECUTOR)) {
            return !this.equals(VISITOR);
        }

        if (permission.equals(EDITOR)) {
            return !this.equals(VISITOR)
                    && !this.equals(EXECUTOR);
        }

        if (permission.equals(MANAGER)) {
            return this.equals(MANAGER);
        }

        /* This could not happen*/
        return false;
    }

    public static int compare(Permission e1, Permission e2) {
        if (e1 == e2) {
            return 0;
        }
        // order is based on include
        if (e1.include(e2)) {
            return 1;
        }
        return -1;
    }
}
