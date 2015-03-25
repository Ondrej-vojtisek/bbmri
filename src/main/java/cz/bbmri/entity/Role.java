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

public class Role implements Serializable {

    public Role() {
    }

    public Role(int id, String name) {
        this.name = name;
        this.id = id;
    }

    //    Must match the predefined table records from visual paradigm project

    public static final Role AUTHORIZED = new Role(1, "authorized");
    public static final Role ADMIN = new Role(2, "admin");
    public static final Role BIOBANK_OPERATOR = new Role(3, "biobank_operator");
    public static final Role PROJECT_TEAM_MEMBER = new Role(4, "project_team_member");
    public static final Role PROJECT_TEAM_MEMBER_CONFIRMED = new Role(5, "project_team_member_confirmed");
    public static final Role DEVELOPER = new Role(6, "developer");

    public static final String PROP_ID = "id";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_USER = "user";

	private int id;
    private String name;
    private Set<User> user = new HashSet<User>();

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
	
	public void setUser(Set<User> value) {
		this.user = value;
	}
	
	public Set<User> getUser() {
		return user;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        if (!name.equals(role.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
