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

public class ProjectState implements Serializable {

    public ProjectState() {
    }

    public ProjectState(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //    Must match the predefined table records from visual paradigm project

    public static final ProjectState NEW = new ProjectState(1, "new");
    public static final ProjectState CONFIRMED = new ProjectState(2, "confirmed");
    public static final ProjectState DENIED = new ProjectState(3, "denied");
    public static final ProjectState STARTED = new ProjectState(4, "started");
    public static final ProjectState CANCELED = new ProjectState(5, "canceled");
    public static final ProjectState FINISHED = new ProjectState(6, "finished");

    public static final String PROP_ID = "id";
    public static final String PROP_NAME = "name";
    public static final String PROP_PROJECT = "project";

    private int id;
    private String name;
    private Set<Project> project = new HashSet<Project>();

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getName() {
        return name;
    }

    public void setProject(Set<Project> value) {
        this.project = value;
    }

    public Set<Project> getProject() {
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectState)) return false;

        ProjectState that = (ProjectState) o;

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
}
