package cz.bbmri.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Message to user that certain event happened. Message has link to provide about information about event. Link is defined
 * by notificationType (providing combination of actionBean and event) and objectId (value of param).
 * Text of notification is localized - translation is based on language setting of recipient.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public class Notification implements Serializable {

    public static final String PROP_ID = "id";
   	public static final String PROP_CREATED = "created";
   	public static final String PROP_READ = "read";
   	public static final String PROP_OBJECT_ID = "objectId";
   	public static final String PROP_MESSAGE = "message";
   	public static final String PROP_USER = "user";
	public static final String PROP_NOTIFICATION_TYPE = "notificationType";

    private long id;
    private Date created;
    private boolean read;
    private long objectId;
    private String message;
    private User user;
	private NotificationType notificationType;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
