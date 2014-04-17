package cz.bbmri.action.project;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.ProjectService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

@UrlBinding("/project/{$event}/{projectId}")
public class ProjectActionBean extends PermissionActionBean<Project> {


    @SpringBean
    private ProjectService projectService;

    @ValidateNestedProperties(value = {
            @Validate(field = "name",
                    required = true, on = "update"),
            @Validate(field = "principalInvestigator",
                    required = true, on = "update"),
            @Validate(field = "annotation",
                    required = true, on = "update")
    })
    private Project project;

    public static Breadcrumb getProjectsBreadcrumb(boolean active) {
        return new Breadcrumb(ProjectActionBean.class.getName(),
                "myProjects", false, "cz.bbmri.entities.Project.projects", active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Long projectId) {
          return new Breadcrumb(ProjectActionBean.class.getName(),
                  "detail", false, "cz.bbmri.entities.Project.project", active,
                  "projectId", projectId);
      }

    public ProjectActionBean() {
        setPagination(new MyPagedListHolder<Project>(new ArrayList<Project>()));
        setComponentManager(new ComponentManager(
                ComponentManager.PROJECT_DETAIL,
                ComponentManager.PROJECT_DETAIL));
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @DontValidate
    @HandlesEvent("display")
    @RolesAllowed({"administrator", "developer"})
    public Resolution display() {

        getBreadcrumbs().add(ProjectActionBean.getProjectsBreadcrumb(true));

        initiatePagination();
        if (getOrderParam() == null) {
            // default
            getPagination().setOrderParam("name");
        }
        getPagination().setEvent("display");
        getPagination().setSource(projectService.allOrderedBy(getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(PROJECT_ALL);
    }

    @DefaultHandler
    @DontValidate
    @HandlesEvent("myProjects")
    @PermitAll
    public Resolution myProjects() {

        getBreadcrumbs().add(ProjectActionBean.getProjectsBreadcrumb(true));

        initiatePagination();
        if (getOrderParam() == null) {
                   // default
                   getPagination().setOrderParam("name");
               }
        getPagination().setEvent("myProjects");
        getPagination().setSource(projectService.getProjectsSortedByUser(
                getContext().getMyId(),
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(PROJECT_MY);
    }

    @PermitAll
    @DontValidate
    @HandlesEvent("create")
    public Resolution create() {
        return new ForwardResolution(CreateProjectActionBean.class);
    }

    @DontValidate
    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectVisitor}"})
    public Resolution detail() {

        getBreadcrumbs().add(ProjectActionBean.getProjectsBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(true, projectId));

        return new ForwardResolution(PROJECT_DETAIL_GENERAL);
    }

    @HandlesEvent("update")
    @RolesAllowed({"project_team_member if ${allowedProjectEditor}"})
    public Resolution update() {
        if (!projectService.update(project,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "detail").addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("delete")
    @RolesAllowed({"developer"})
    public Resolution delete() {

        if (!projectService.remove(projectId,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass());
        }
        successMsg();
        return new RedirectResolution(this.getClass());
    }

    @HandlesEvent("approve")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution approve() {

        // If project is mine than I can't approve it

        if (getAllowedProjectVisitor()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.project.ProjectActionBean.approveMyProject"));
            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }

        if (!projectService.approve(projectId,
                getContext().getMyId(),
                getContext().getValidationErrors())) {

            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }

        successMsg();
        return new RedirectResolution(this.getClass(), "detail").addParameter("projectId", projectId);
    }

    @HandlesEvent("deny")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution deny() {
        if (getAllowedProjectVisitor()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.project.ProjectActionBean.denyMyProject"));
            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }

        if (!projectService.deny(projectId,
                getContext().getMyId(),
                getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "detail").addParameter("projectId", projectId);
    }

    @HandlesEvent("markAsFinished")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor}"})
    public Resolution markAsFinished() {
        if (!projectService.finish(projectId,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "detail").addParameter("projectId", projectId);
    }

}
