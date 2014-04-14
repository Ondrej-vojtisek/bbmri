package cz.bbmri.action.project;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.comparator.PermissionComparator;
import cz.bbmri.entities.comparator.PermissionUserComparator;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.ProjectService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.3.14
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/project/administrators/{$event}/{projectId}")
public class ProjectAdministratorsActionBean extends PermissionActionBean<ProjectAdministrator> {

    @SpringBean
    private ProjectService projectService;

    private Permission permission;

    private Long adminId;


    public static Breadcrumb getBreadcrumb(boolean active, Long projectId) {
        return new Breadcrumb(ProjectAdministratorsActionBean.class.getName(),
                "administratorsResolution", false, "cz.bbmri.action.project.ProjectActionBean.administrators",
                active, "projectId", projectId);
    }

    public ProjectAdministratorsActionBean() {
        setPagination(new MyPagedListHolder<ProjectAdministrator>(new ArrayList<ProjectAdministrator>()));
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.ROLE_DETAIL,
                ComponentManager.PROJECT_DETAIL));
        getPagination().setIdentifierParam("projectId");
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Long getId() {
        return adminId;
    }

    public void setId(Long adminId) {
        this.adminId = adminId;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("administratorsResolution")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectVisitor}"})
    public Resolution administratorsResolution() {

        getBreadcrumbs().add(ProjectActionBean.getProjectsBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(false, projectId));
        getBreadcrumbs().add(ProjectAdministratorsActionBean.getBreadcrumb(true, projectId));

        if (projectId != null) {
            getPagination().setIdentifier(projectId);
        }
        initiatePagination();

        if (getOrderParam() == null) {
            // default sorter
            getPagination().setOrderParam("user.surname");
            getPagination().setComparator(new PermissionUserComparator());

        } else if (getOrderParam().equals("user.surname")) {
            // pagination orderParam was already set
            getPagination().setComparator(new PermissionUserComparator());

        } else if (getOrderParam().equals("permission")) {
            // pagination orderParam was already set
            getPagination().setComparator(new PermissionComparator());
        }
        getPagination().setEvent("administratorsResolution");
        // Administrators are stored in Set
        getPagination().setSource(new ArrayList<ProjectAdministrator>(getProject().getProjectAdministrators()));
        return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS)
                .addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("editAdministrators")
    @RolesAllowed({"project_team_member if ${allowedProjectManager}"})
    public Resolution editAdministrators() {
        return new ForwardResolution(this.getClass(), "administratorsResolution")
                .addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("setPermission")
    @RolesAllowed({"project_team_member if ${alloweProjectdManager}"})
    public Resolution setPermission() {
        if (!projectService.changeAdministratorPermission(adminId, permission,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "administratorsResolution")
                    .addParameter("projectId", projectId);
        }
        // It changes data - redirect necessary
        successMsg(null);
        return new RedirectResolution(this.getClass(), "administratorsResolution")
                .addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("removeAdministrator")
    @RolesAllowed({"project_team_member if ${allowedProjectManager or isMyAccount}"})
    //project_team_member if ${allowedManager},
    public Resolution removeAdministrator() {
        if (!projectService.removeAdministrator(adminId,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "administratorsResolution")
                    .addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "administratorsResolution")
                .addParameter("projectId", projectId);
    }

    public String getRemoveQuestion() {
        if (getIsMyAccount()) {
            return this.getName() + ".questionRemoveMyself";
        }
        return this.getName() + ".questionRemoveAdministrator";
    }

    @DontValidate
    @HandlesEvent("addAdministrator")
    @RolesAllowed({"project_team_member if ${allowedProjectManager}"})
    public Resolution addAdministrator() {
        if (!projectService.assignAdministrator(projectId, adminId, permission,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "administratorsResolution")
                    .addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "administratorsResolution")
                .addParameter("projectId", projectId);
    }

}
