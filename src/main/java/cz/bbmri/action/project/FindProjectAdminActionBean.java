package cz.bbmri.action.project;

import cz.bbmri.action.FindActionBean;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/project/administrators/addAdministrator/{$event}/{projectId}")
public class FindProjectAdminActionBean extends FindActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FindProjectAdminActionBean() {
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.PROJECT_DETAIL,
                ComponentManager.PROJECT_DETAIL));
    }

    private static Breadcrumb getBreadcrumb(boolean active, Long projectId) {
           return new Breadcrumb(FindProjectAdminActionBean.class.getName(),
                   "display", false, "cz.bbmri.action.project.ProjectActionBean.addAdministrator",
                   active, "projectId", projectId);
       }


    @DefaultHandler
    public Resolution display() {

        getBreadcrumbs().add(ProjectActionBean.getProjectsBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(false, projectId));
        getBreadcrumbs().add(ProjectAdministratorsActionBean.getBreadcrumb(false, projectId));
        getBreadcrumbs().add(FindProjectAdminActionBean.getBreadcrumb(true, projectId));

        return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS_ADD);
    }

    @HandlesEvent("find")
    public Resolution find() {
        return new ForwardResolution(this.getClass(), "display")
                .addParameter("UserFind", getUserFind())
                .addParameter("projectId", id);
    }
}
