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

public class Sex implements Serializable {

    public Sex() {
    }

    public Sex(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //    Must match the predefined table records from visual paradigm project

    public static final Sex MALE = new Sex(1, "male");
    public static final Sex FEMALE = new Sex(2, "female");
    public static final Sex UNKNOWN = new Sex(3, "unknown");

    public static final String PROP_ID = "id";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_PATIENT = "patient";

	private int id;
    private String name;
    private Set<Patient> patient = new HashSet<Patient>();
	
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
	
	public void setPatient(Set<Patient> value) {
		this.patient = value;
	}
	
	public Set<Patient> getPatient() {
		return patient;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sex)) return false;

        Sex sex = (Sex) o;

        if (id != sex.id) return false;
        if (!name.equals(sex.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
