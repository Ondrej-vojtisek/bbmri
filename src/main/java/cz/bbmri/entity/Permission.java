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
}
