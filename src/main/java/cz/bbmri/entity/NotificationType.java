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
import cz.bbmri.action.QuestionActionBean;
import cz.bbmri.action.ReservationActionBean;


/**
 * Fake table to map NotificationType enum during hibernate mapping export
 */
public enum NotificationType {

    /* Project notifications */

    PROJECT_DETAIL(ProjectActionBean.class.getName(), "detail", "id"),
    PROJECT_ATTACHMENT(ProjectActionBean.class.getName(), "attachments", "id"),
    PROJECT_ADMINISTRATOR(ProjectActionBean.class.getName(), "projectuser", "id"),
    PROJECT_DELETE(ProjectActionBean.class.getName(), null, null),
    PROJECT_QUESTIONS(ProjectActionBean.class.getName(), "questions", "id"),

    /* Biobank notifications */

    BIOBANK_DETAIL(BiobankActionBean.class.getName(), "detail", "id"),
    BIOBANK_ADMINISTRATOR(BiobankActionBean.class.getName(), "biobankuser", "id"),
    BIOBANK_ATTACHMENT(BiobankActionBean.class.getName(), "attachments", "id"),
    BIOBANK_QUESTIONS(BiobankActionBean.class.getName(), "questions", "id"),

    /* Question notifications */
    QUESTION_DETAIL(QuestionActionBean.class.getName(), "detail", "id"),

      /* Reservation notifications */
    RESERVATION_DETAIL(ReservationActionBean.class.getName(), "detail", "id");

    NotificationType(String actionBeanName, String eventName, String parameter) {
        this.actionBeanName = actionBeanName;
        this.eventName = eventName;
        this.parameter = parameter;
    }

    private final String actionBeanName;
    private final String eventName;
    private final String parameter;
    
    public String getActionBeanName() {
        return actionBeanName;
    }
    
    public String getEventName() {
        return eventName;
    }

    public String getParameter() {
        return parameter;
    }
    

}
