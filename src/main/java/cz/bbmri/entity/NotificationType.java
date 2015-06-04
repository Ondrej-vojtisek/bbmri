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

import cz.bbmri.action.BiobankActionBean;
import cz.bbmri.action.ProjectActionBean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Fake table to map NotificationType enum during hibernate mapping export
 */
public class NotificationType implements Serializable {

    public static final String FOLDER = "notificationType";

    public NotificationType() {
    }

    public NotificationType(int id, String actionBeanName, String eventName, String parameter) {
        this.id = id;
        this.actionBeanName = actionBeanName;
        this.eventName = eventName;
        this.parameter = parameter;
    }

    /* Project notifications */

    public static final NotificationType PROJECT_DETAIL = new NotificationType(1, ProjectActionBean.class.getName(), "detail", "id");
    public static final NotificationType PROJECT_ATTACHMENT = new NotificationType(2, ProjectActionBean.class.getName(), "attachments", "id");
    public static final NotificationType PROJECT_ADMINISTRATOR = new NotificationType(3, ProjectActionBean.class.getName(), "administrators", "id");
    public static final NotificationType PROJECT_DELETE = new NotificationType(4, ProjectActionBean.class.getName(), null, null);

    /* Biobank notifications */

    public static final NotificationType BIOBANK_DETAIL = new NotificationType(5, BiobankActionBean.class.getName(), "detail", "id");
    public static final NotificationType BIOBANK_ADMINISTRATOR = new NotificationType(6, BiobankActionBean.class.getName(), "attachments", "id");
    public static final NotificationType BIOBANK_DELETE = new NotificationType(7, BiobankActionBean.class.getName(), "administrators", "id");
    public static final NotificationType BIOBANK_ATTACHMENT = new NotificationType(8, BiobankActionBean.class.getName(), null, null);


    public static final String PROP_ID = "id";
    public static final String PROP_ACTION_BEAN_NAME = "actionBeanName";
    public static final String PROP_EVENT_NAME = "eventName";
    public static final String PROP_PARAMETER = "parameter";
    public static final String PROP_NOTIFICATION = "notification";

    private int id;
    private Set<Notification> notification = new HashSet<Notification>();
    private String actionBeanName;

    private String eventName;

    private String parameter;

    public void setId(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public void setNotification(Set<Notification> value) {
        this.notification = value;
    }

    public Set<Notification> getNotification() {
        return notification;
    }

    public String getActionBeanName() {
        return actionBeanName;
    }

    public void setActionBeanName(String actionBeanName) {
        this.actionBeanName = actionBeanName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
