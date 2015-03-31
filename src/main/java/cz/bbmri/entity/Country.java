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

public class Country implements Serializable {

    public static final String PROP_ID = "id";
   	public static final String PROP_KEY = "key";
   	public static final String PROP_NAME = "name";
   	public static final String PROP_BIOBANK = "biobank";
   	public static final String PROP_CONTACT = "contact";

    public Country() {
    }

    public Country(int id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public static final Country CZ = new Country( 1, "CZ", "Czech republic");

	private int id;
	private String key;
	private String name;
	private Set<Biobank> biobank = new HashSet<Biobank>();
	private Set<Contact> contact = new HashSet<Contact>();
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return id;
	}
	
	public void setKey(String value) {
		this.key = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBiobank(Set<Biobank> value) {
		this.biobank = value;
	}
	
	public Set<Biobank> getBiobank() {
		return biobank;
	}
	
	
	public void setContact(Set<Contact> value) {
		this.contact = value;
	}
	
	public Set<Contact> getContact() {
		return contact;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        if (id != country.id) return false;
        if (!key.equals(country.key)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + key.hashCode();
        return result;
    }
}
