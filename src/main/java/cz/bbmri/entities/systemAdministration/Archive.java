package cz.bbmri.entities.systemAdministration;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Used to archive messages about events happening in system. Each archive represents one record described with message
 * and time when event occurred.
 * Actor is a user who initiated event.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Table
@Entity
public class Archive implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SYSTEM_ACTOR = "system_event";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    private String message;

    @Type(type = "timestamp")
    private Date created;

    private String actor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Archive)) return false;

        Archive archive = (Archive) o;

        if (!id.equals(archive.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Archive{" +
                "created=" + created +
                ", message='" + message + '\'' +
                '}';
    }
}
