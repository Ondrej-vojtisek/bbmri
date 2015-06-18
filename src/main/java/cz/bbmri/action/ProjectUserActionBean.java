package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
import cz.bbmri.entity.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import javax.annotation.security.RolesAllowed;

@UrlBinding("/projectuser/{$event}/{projectId}/{userId}")
public class ProjectUserActionBean extends AuthorizationActionBean {

    @SpringBean
    private ProjectUserDAO projectUserDAO;

    @SpringBean
    private ProjectDAO projectDAO;

    @SpringBean
    private UserDAO userDAO;

    @SpringBean
    private PermissionDAO permissionDAO;

    @SpringBean
    private NotificationDAO notificationDAO;

    private Long userId;

    private Long projectId;

    private Integer permissionId;

    @Validate(on = {"confirmAdd"}, required = true)
    private String find;

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(userId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getFind() {
        return find;
    }

    public void setFind(String find) {
        this.find = find;
    }

    @HandlesEvent("remove")
    @RolesAllowed({"project_team_member if ${projectManager}", "developer"})
    public Resolution remove() {
        Project project = projectDAO.get(projectId);

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        User user = userDAO.get(userId);

        if (user == null) {
            return new ForwardResolution(View.User.NOTFOUND);
        }

        ProjectUser projectUser = projectUserDAO.get(project, user);

        if (projectUser == null) {
            return new ForwardResolution(View.ProjectUser.NOTFOUND);
        }

        projectUserDAO.remove(projectUser);

        // refresh
        user = userDAO.get(userId);

        if (project.getIsConfirmed()) {
            user.denominateProjectTeamMemberConfirmed();
        }

        user.denominateProjectTeamMember();

        userDAO.save(user);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.adminDeleted",
                user.getWholeName(), project.getName());

        notificationDAO.create(project.getOtherProjectUser(getLoggedUser()),
                NotificationType.PROJECT_ADMINISTRATOR, locMsg, project.getId());

        return new RedirectResolution(ProjectActionBean.class, "projectuser").addParameter("id", project.getId());
    }

    @HandlesEvent("setPermission")
    @RolesAllowed({"project_team_member if ${projectManager}", "developer"})
    public Resolution setPermission() {
        Project project = projectDAO.get(projectId);

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        User user = userDAO.get(userId);

        if (user == null) {
            return new ForwardResolution(View.User.NOTFOUND);
        }

        ProjectUser projectUser = projectUserDAO.get(project, user);

        if (projectUser == null) {
            return new ForwardResolution(View.ProjectUser.NOTFOUND);
        }

        Permission permission = permissionDAO.get(permissionId);
        if (permission == null) {
            return new ForwardResolution(View.Permission.NOTFOUND);
        }

        projectUser.setPermission(permission);

        projectUserDAO.save(projectUser);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.ProjectUserActionBean.permissionChanged",
                project.getName(), user.getWholeName(), permission.getName());

        notificationDAO.create(project.getOtherProjectUser(getLoggedUser()),
                NotificationType.PROJECT_ADMINISTRATOR, locMsg, project.getId());

        return new RedirectResolution(ProjectActionBean.class, "projectuser").addParameter("id", project.getId());
    }

    public String getRemoveQuestion() {
        if (getIsMyAccount()) {
            return this.getName() + ".questionRemoveMyself";
        }
        return this.getName() + ".questionRemoveProjectUser";
    }

    @DefaultHandler
    @HandlesEvent("add")
    @RolesAllowed({"project_team_member if ${projectManager}", "developer"})
    public Resolution add() {

        Project project = projectDAO.get(projectId);

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        return new ForwardResolution(View.ProjectUser.ADD);
    }


    @HandlesEvent("confirmAdd")
    @RolesAllowed({"project_team_member if ${projectManager}", "developer"})
    public Resolution confirmAdd() {
        Project project = projectDAO.get(projectId);

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        User user = userDAO.find(find);
        if (user == null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.ProjectUserActionBean.userNotFound"));
            return new ForwardResolution(ProjectUserActionBean.class, "add").addParameter("projectId", projectId);
        }
        userId = user.getId();

        if (projectUserDAO.get(project, user) != null) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.ProjectUserActionBean.userIsAlreadyAssigned"));
            return new ForwardResolution(View.ProjectUser.ADD);
        }

        Permission permission = permissionDAO.get(permissionId);
        if (permission == null) {
            return new ForwardResolution(View.Permission.NOTFOUND);
        }

        ProjectUser projectUser = new ProjectUser();
        projectUser.setUser(user);
        projectUser.setProject(project);
        projectUser.setPermission(permission);

        projectUserDAO.save(projectUser);

        user.nominateProjectTeamMember();

        if (project.getIsConfirmed()) {
            user.nominateProjectTeamMemberConfirmed();
        }

        userDAO.save(user);

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.ProjectUserActionBean.assignedAdministrator",
                project.getName(), user.getWholeName(), permission.getName());

        notificationDAO.create(project.getOtherProjectUser(getLoggedUser()),
                NotificationType.PROJECT_ADMINISTRATOR, locMsg, project.getId());

        return setPermission();
    }

}