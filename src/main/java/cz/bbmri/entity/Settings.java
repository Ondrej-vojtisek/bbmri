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
import java.util.Locale;

public class Settings implements Serializable {

    public static final String FOLDER = "settings";

    private static final String DEFAULT_LOCALE = "cs";

    public static final String PROP_USER = "user";

	private User user;
    private long userId;
    private String locale;
	
	public void setUserId(long value) {
		this.userId = value;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUser(User value) {
		this.user = value;
	}
	
	public User getUser() {
		return user;
	}

    public Locale getLocaleSettings() {
        if (locale == null) {
            return new Locale(DEFAULT_LOCALE);
        } else {
            return new Locale(locale);
        }
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
