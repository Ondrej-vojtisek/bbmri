package cz.bbmri.entity;

import org.hibernate.annotations.Table;

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

public class Archive implements Serializable {

    public static final String FOLDER = "archive";

    public static final String SYSTEM_ACTOR = "system_event";

    public static final String PROP_ID = "id";
   	public static final String PROP_CREATED = "created";
   	public static final String PROP_MESSAGE = "message";
   	public static final String PROP_ACTOR = "actor";

    private long id;
   	private Date created = new Date();
   	private String message;
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
    public String toString() {
        return "Archive{" +
                "created=" + created +
                ", message='" + message + '\'' +
                '}';
    }
}
