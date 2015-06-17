/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * <p/>
 * This is an automatic generated file. It will be regenerated every time
 * you generate persistence class.
 * <p/>
 * Modifying its content may cause the program not work, or your work may lost.
 * <p/>
 * Licensee: Masaryk University
 * License Type: Academic
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

    public static final Locale LOCALE_CZ = new Locale("cs", "CZ");
    public static final String CZ_DATE_PATTERN_JS = "dd-mm-yyyy";
    public static final String EN_DATE_PATTERN_JS = "mm-dd-yyyy";

    public static final String CZ_DATE_PATTERN = "dd-MM-yyyy";
    public static final String EN_DATE_PATTERN = "MM-dd-yyyy";

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
            return LOCALE_CZ;
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
