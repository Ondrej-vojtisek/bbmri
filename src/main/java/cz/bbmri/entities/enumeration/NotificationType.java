package cz.bbmri.entities.enumeration;

import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.biobank.BiobankAdministratorsActionBean;
import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.action.project.ProjectAdministratorsActionBean;
import cz.bbmri.action.project.ProjectAttachmentsActionBean;
import cz.bbmri.action.request.RequestActionBean;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public enum NotificationType {

    /* ProjectFacade notifications */

    PROJECT_DETAIL(ProjectActionBean.class.getName(),  "detail", "projectId"),
    PROJECT_ATTACHMENT(ProjectAttachmentsActionBean.class.getName(), "attachmentsResolution", "projectId"),
    PROJECT_ADMINISTRATOR(ProjectAdministratorsActionBean.class.getName(), "administratorsResolution", "projectId"),
    PROJECT_DELETE(ProjectActionBean.class.getName(), null, null),

    /* BiobankFacade notifications */

    BIOBANK_DETAIL(BiobankActionBean.class.getName(), "detail", "biobankId"),
    BIOBANK_ADMINISTRATOR(BiobankAdministratorsActionBean.class.getName(), "administratorsResolution", "biobankId"),
    BIOBANK_DELETE(BiobankActionBean.class.getName(), null, null),

    /* UserFacade notifications */

    /* RequestFacade notifications */

    SAMPLE_REQUEST_DETAIL(RequestActionBean.class.getName(), "detail", "sampleQuestionId"),

    /* This should lead to support page - list of all administrators and developers */
    USER_SUPPORT(null, "detail", null);



    private final String actionBeanName;

    private final String confirmEvent;

    private final String parameter;

  //  private String denyEvent;

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
