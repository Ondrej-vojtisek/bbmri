package cz.bbmri.entities.enumeration;

import cz.bbmri.action.biobank.BiobankActionBean;
import cz.bbmri.action.project.ProjectActionBean;
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

    PROJECT_DETAIL(ProjectActionBean.class.getName(),  "detail"),
    PROJECT_ATTACHMENT(ProjectActionBean.class.getName(), "attachments"),
    PROJECT_ADMINISTRATOR(ProjectActionBean.class.getName(), "administratorsResolution"),
    PROJECT_DELETE(ProjectActionBean.class.getName(), null),

    /* BiobankFacade notifications */

    BIOBANK_DETAIL(BiobankActionBean.class.getName(), "detail"),
    BIOBANK_ADMINISTRATOR(BiobankActionBean.class.getName(), "administratorsResolution"),
    BIOBANK_DELETE(BiobankActionBean.class.getName(), null),

    /* UserFacade notifications */


    /* This should lead to support page - list of all administrators and developers */
    USER_SUPPORT(null, "detail");



    private String actionBeanName;

    private String confirmEvent;

  //  private String denyEvent;

    private NotificationType(String actionBeanName, String confirmEvent) {
        this.actionBeanName = actionBeanName;
        this.confirmEvent = confirmEvent;
    }

    public String getActionBeanName() {
        return actionBeanName;
    }


    public String getConfirmEvent(){
        return confirmEvent;
    }

}
