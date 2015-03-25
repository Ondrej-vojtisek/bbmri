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

public class Collection implements Serializable {

    public static final String PROP_ID = "id";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_DESCRIPTION = "description";
   	public static final String PROP_BIOBANK = "biobank";
   	public static final String PROP_SAMPLE = "sample";

	private long id;
	private String name;
	private String description;
	private Biobank biobank;
	private Set<Sample> sample = new HashSet<Sample>();
	
	public void setId(long value) {
		this.id = value;
	}
	
	public long getId() {
		return id;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setBiobank(Biobank value) {
		this.biobank = value;
	}
	
	public Biobank getBiobank() {
		return biobank;
	}
	
	public void setSample(Set<Sample> value) {
		this.sample = value;
	}
	
	public Set<Sample> getSample() {
		return sample;
	}
	

}
