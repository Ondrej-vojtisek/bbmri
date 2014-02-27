package cz.bbmri.entities.enumeration;

import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.action.request.RequestActionBean;
import cz.bbmri.entities.Project;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 14.12.13
 * Time: 20:43
 * To change this template use File | Settings | File Templates.
 */
public enum NotificationType {

    /* ProjectFacade notifications */

    PROJECT_DETAIL(ProjectActionBean.class.getName(),  "detail", "projectId"),
    PROJECT_ATTACHMENT(ProjectActionBean.class.getName(), "attachments", "projectId"),
    PROJECT_ADMINISTRATOR(ProjectActionBean.class.getName(), "administratorsResolution", "projectId"),
    PROJECT_DELETE(ProjectActionBean.class.getName(), null, null),

    /* BiobankFacade notifications */

    BIOBANK_DETAIL(BiobankActionBean.class.getName(), "detail", "biobankId"),
    BIOBANK_ADMINISTRATOR(BiobankActionBean.class.getName(), "administratorsResolution", "biobankId"),
    BIOBANK_DELETE(BiobankActionBean.class.getName(), null, null),

    /* UserFacade notifications */

    /* RequestFacade notifications */

    SAMPLE_REQUEST_DETAIL(RequestActionBean.class.getName(), "detail", "sampleQuestionId"),

    /* This should lead to support page - list of all administrators and developers */
    USER_SUPPORT(null, "detail", null);



    private String actionBeanName;

    private String confirmEvent;

    private String parameter;

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
