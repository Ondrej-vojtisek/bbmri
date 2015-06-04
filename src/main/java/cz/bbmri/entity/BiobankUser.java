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

public class BiobankUser extends CompositeKeyGroup implements Serializable {

	public static final String FOLDER = "biobankUser";

    public static final String PROP_BIOBANK = "biobank";
   	public static final String PROP_USER = "user";
   	public static final String PROP_PERMISSION = "permission";

	private Biobank biobank;
    private int biobankId;
    private User user;
    private long userId;
    private Permission permission;

    public void setBiobankId(int value) {
   		this.biobankId = value;
   	}

   	public int getBiobankId() {
   		return biobankId;
   	}
	
	public void setUserId(long value) {
		this.userId = value;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setBiobank(Biobank value) {
		this.biobank = value;
	}
	
	public Biobank getBiobank() {
		return biobank;
	}
	
	public void setUser(User value) {
		this.user = value;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setPermission(Permission value) {
		this.permission = value;
	}
	
	public Permission getPermission() {
		return permission;
	}

    public boolean equals(Object aObj) {
   		if (aObj == this)
   			return true;
   		if (!(aObj instanceof BiobankUser))
   			return false;
   		BiobankUser biobankuser = (BiobankUser)aObj;
   		if (getBiobank() == null) {
   			if (biobankuser.getBiobank() != null)
   				return false;
   		}
   		else if (!getBiobank().equals(biobankuser.getBiobank()))
   			return false;
   		if (getUser() == null) {
   			if (biobankuser.getUser() != null)
   				return false;
   		}
   		else if (!getUser().equals(biobankuser.getUser()))
   			return false;
   		return true;
   	}

   	public int hashCode() {
   		int hashcode = 0;
   		if (getBiobank() != null) {
   			hashcode = hashcode + getBiobank().getId();
   		}
   		if (getUser() != null) {
   			hashcode = hashcode + (int) getUser().getId();
   		}
   		return hashcode;
   	}

    @Override
    public String toString() {
        return "BiobankUser{" +
                "biobank=" + biobank.getAcronym() +
                ", user=" + user.getWholeName() +
                ", permission=" + permission +
                '}';
    }
}
