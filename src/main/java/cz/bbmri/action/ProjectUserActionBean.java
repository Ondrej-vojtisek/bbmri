package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.ProjectDAO;
import cz.bbmri.dao.ProjectUserDAO;
import cz.bbmri.dao.PermissionDAO;
import cz.bbmri.dao.UserDAO;
import cz.bbmri.entity.Project;
import cz.bbmri.entity.ProjectUser;
import cz.bbmri.entity.Permission;
import cz.bbmri.entity.User;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;

@UrlBinding("/projectuser/{$event}/{id}")
public class ProjectUserActionBean extends ComponentActionBean {

    @SpringBean
    private ProjectUserDAO projectUserDAO;

    @SpringBean
    private ProjectDAO projectDAO;

    @SpringBean
    private UserDAO userDAO;

    @SpringBean
    private PermissionDAO permissionDAO;

    private Long userId;

    private Long projectId;

    private Integer permissionId;

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

    @HandlesEvent("remove")
    @RolesAllowed({"project_operator", "developer"})
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

        if (user == null) {
            return new ForwardResolution(View.ProjectUser.NOTFOUND);
        }

        projectUserDAO.remove(projectUser);

        return new RedirectResolution(ProjectActionBean.class, "projectuser").addParameter("id", project.getId());
    }

    @HandlesEvent("setPermission")
    @RolesAllowed({"project_operator", "developer"})
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

        if (user == null) {
            return new ForwardResolution(View.ProjectUser.NOTFOUND);
        }

        Permission permission = permissionDAO.get(permissionId);
        if (user == null) {
            return new ForwardResolution(View.Permission.NOTFOUND);
        }

        projectUser.setPermission(permission);

        projectUserDAO.save(projectUser);

        return new RedirectResolution(ProjectActionBean.class, "projectuser").addParameter("id", project.getId());
    }

    public String getRemoveQuestion() {
        if (getIsMyAccount()) {
            return this.getName() + ".questionRemoveMyself";
        }
        return this.getName() + ".questionRemoveProjectUser";
    }

    @HandlesEvent("add")
    @RolesAllowed({"project_operator", "developer"})
    public Resolution add() {

        return new ForwardResolution(View.ProjectUser.ADD);
    }

}