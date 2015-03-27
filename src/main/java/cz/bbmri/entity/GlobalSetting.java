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
import java.sql.Timestamp;
import java.util.Date;

public class GlobalSetting extends CompositeKeyGroup implements Serializable {

    public static final String PROP_KEY = "key";
   	public static final String PROP_VALUE = "value";
   	public static final String PROP_LAST_MODIFICATION = "lastModification";

	private String key;
	private String value;
	private Timestamp lastModification;
	
	public void setKey(String value) {
		this.key = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

    public Timestamp getLastModification() {
        return lastModification;
    }

    public void setLastModification(Timestamp lastModification) {
        this.lastModification = lastModification;
    }
}