package cz.bbmri.entities.systemAdministration;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Settings affecting behaviour of whole system. Setting is structure as set of key + value. Each has different meaning and
 * changes behaviour of different part of system. System administrators (user with ADMINISTRATOR system role) can change
 * values of global settings.
 *
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@Table
@Entity
public class GlobalSetting implements Serializable {

    /**
     * Global setting with the key RESERVATION_VALIDITY defines how long is reservation preserved in system before
     * expiration.
     */
    public static final String RESERVATION_VALIDITY = "RESERVATION_VALIDITY";

    @Id
    @Column(nullable = false)
    private String key;

    private String value;

    @Type(type = "timestamp")
    private Date lastModification;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalSetting that = (GlobalSetting) o;

        if (!key.equals(that.key)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "GlobalSettings{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", lastModification=" + lastModification +
                '}';
    }
}
