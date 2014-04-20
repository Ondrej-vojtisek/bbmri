package cz.bbmri.entities;

import cz.bbmri.entities.enumeration.NotificationType;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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

@Table
@Entity
public class Notification implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(nullable = false)
    private Long id;

    /**
     * Date and time when notification was created
     */
    @Type(type = "timestamp")
    private Date created;

    /**
     * Recipient have seen the notification or not?
     */
    private boolean read;

    /**
     * Type of message - defines actionBean, event and name of param
     */
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    /**
     * Value of param user in URL for more details
     */
    private Long objectId;

    /**
     * Localized message (message is localized before storage in database)
     */
    private String message;

    @ManyToOne
    private User user;

    public Notification() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;

        Notification that = (Notification) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", created=" + created +
                ", read=" + read +
                ", notificationType=" + notificationType +
                '}';
    }
}
