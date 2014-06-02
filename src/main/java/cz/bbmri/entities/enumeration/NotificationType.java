package cz.bbmri.entities.enumeration;

import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.biobank.BiobankAdministratorsActionBean;
import cz.bbmri.action.biobank.BiobankAttachmentsActionBean;
import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.action.project.ProjectAdministratorsActionBean;
import cz.bbmri.action.project.ProjectAttachmentsActionBean;
import cz.bbmri.action.request.RequestActionBean;

/**
 * NotificationType defines what can used do after reading notification. For instance if project worker gets message that
 * its own project was changed (by another project worker) he would probably want to se changed state of projekt. The appropriate
 * NotificationType would be project_detail - it will set link from notification to se detail of project.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum NotificationType {

    /* Project notifications */

    PROJECT_DETAIL(ProjectActionBean.class.getName(),  "detail", "projectId"),
    PROJECT_ATTACHMENT(ProjectAttachmentsActionBean.class.getName(), "attachmentsResolution", "projectId"),
    PROJECT_ADMINISTRATOR(ProjectAdministratorsActionBean.class.getName(), "administratorsResolution", "projectId"),
    PROJECT_DELETE(ProjectActionBean.class.getName(), null, null),

    /* Biobank notifications */

    BIOBANK_DETAIL(BiobankActionBean.class.getName(), "detail", "biobankId"),
    BIOBANK_ADMINISTRATOR(BiobankAdministratorsActionBean.class.getName(), "administratorsResolution", "biobankId"),
    BIOBANK_DELETE(BiobankActionBean.class.getName(), null, null),
    BIOBANK_ATTACHMENT(BiobankAttachmentsActionBean.class.getName(), "attachmentsResolution", "biobankId"),

    /* RequestFacade notifications */

    SAMPLE_REQUEST_DETAIL(RequestActionBean.class.getName(), "detail", "sampleQuestionId"),

    /* This should lead to support page - list of all administrators and developers */
    USER_SUPPORT(null, "detail", null);

    // Name of ActionBean where the confirm event is defined
    private final String actionBeanName;

    // Event which will be allowed to follow from notification
    private final String confirmEvent;

    // Name of parameter which must be set to correctly follow the event
    private final String parameter;

    private NotificationType(String actionBeanName, String confirmEvent, String parameter) {
        this.actionBeanName = actionBeanName;
        this.confirmEvent = confirmEvent;
        this.parameter = parameter;
    }

    public String getActionBeanName() {
        return actionBeanName;
    }

    public String getConfirmEvent(){
        return confirmEvent;
    }

    public String getParameter() {
        return parameter;
    }
}
