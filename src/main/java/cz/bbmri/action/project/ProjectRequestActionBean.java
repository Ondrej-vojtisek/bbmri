package cz.bbmri.action.project;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.SampleRequestService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/project/requests/{$event}/{projectId}")
public class ProjectRequestActionBean extends PermissionActionBean<SampleRequest> {

    @SpringBean
    private SampleRequestService sampleRequestService;

    public ProjectRequestActionBean() {
        setPagination(new MyPagedListHolder<SampleRequest>(new ArrayList<SampleRequest>()));
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.SAMPLEQUESTION_DETAIL,
                ComponentManager.PROJECT_DETAIL));
        getPagination().setIdentifierParam("projectId");
    }

    public static Breadcrumb getBreadcrumb(boolean active, Long projectId) {
            return new Breadcrumb(ProjectRequestActionBean.class.getName(),
                    "sampleRequestsResolution", false, "cz.bbmri.entities.SampleRequest.sampleRequests",
                    active, "projectId", projectId);
        }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("sampleRequestsResolution")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectVisitor}"})
    public Resolution sampleRequestsResolution() {

        getBreadcrumbs().add(ProjectActionBean.getProjectsBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(false, projectId));
        getBreadcrumbs().add(ProjectRequestActionBean.getBreadcrumb(true, projectId));

        if (projectId != null) {
            getPagination().setIdentifier(projectId);
        }

        initiatePagination();
        if (getOrderParam() == null) {
            // default
            getPagination().setOrderParam("created");
        }
        getPagination().setEvent("sampleRequestsResolution");
        getPagination().setSource(sampleRequestService.getSortedSampleRequest(
                projectId,
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(PROJECT_DETAIL_SAMPLE_REQUESTS);
    }
}
