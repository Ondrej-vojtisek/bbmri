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

public class Retrieved implements Serializable {

    public Retrieved() {
    }

    public Retrieved(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //    Must match the predefined table records from visual paradigm project

    public static final Retrieved PREOPERATIONAL = new Retrieved(1, "preoperational");
    public static final Retrieved OPERATIONAL = new Retrieved(2, "operational");
    public static final Retrieved POST = new Retrieved(3, "post");
    public static final Retrieved UNKNOWN = new Retrieved(4, "unknown");

    public static final String PROP_ID = "id";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_SAMPLE = "sample";

	private int id;
    private String name;
	
	private Set<Sample> sample = new HashSet<Sample>();
	
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
	
	public void setSample(Set<Sample> value) {
		this.sample = value;
	}
	
	public Set<Sample> getSample() {
		return sample;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Retrieved)) return false;

        Retrieved retrieved = (Retrieved) o;

        if (id != retrieved.id) return false;
        if (!name.equals(retrieved.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
