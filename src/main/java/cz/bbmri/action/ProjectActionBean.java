package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.ProjectDAO;
import cz.bbmri.entity.Attachment;
import cz.bbmri.entity.Project;
import cz.bbmri.entity.Question;
import cz.bbmri.entity.Role;
import cz.bbmri.entity.webEntities.Breadcrumb;
import cz.bbmri.entity.webEntities.MyPagedListHolder;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/project/{$event}/{id}")
public class ProjectActionBean extends AuthorizationActionBean {

    @SpringBean
    private ProjectDAO projectDAO;

    private Long id;

    private Project project;

    private MyPagedListHolder<Project> pagination = new MyPagedListHolder<Project>(new ArrayList<Project>());

    private MyPagedListHolder<Attachment> attachmentPagination = new MyPagedListHolder<Attachment>(new ArrayList<Attachment>());

    private MyPagedListHolder<Question> questionPagination = new MyPagedListHolder<Question>(new ArrayList<Question>());

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(ProjectActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.Project.projects", active);
    }

    public static Breadcrumb getMyBreadcrumb(boolean active) {
        return new Breadcrumb(ProjectActionBean.class.getName(), "myProjects", false, "cz.bbmri.entity.Project.myProjects",
                active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Project project) {
        return new Breadcrumb(ProjectActionBean.class.getName(), "detail", true, project.getName(),
                active, "id", project.getId());
    }

    public static Breadcrumb getAttachmentsBreadcrumb(boolean active, Project project) {
        return new Breadcrumb(ProjectActionBean.class.getName(), "attachments", false, "cz.bbmri.entity.Attachment.attachments",
                active, "id", project.getId());
    }

    public static Breadcrumb getProjectUserBreadcrumb(boolean active, Project project) {
        return new Breadcrumb(ProjectActionBean.class.getName(), "projectuser", false, "cz.bbmri.entity.ProjectUser.projectUsers",
                active, "id", project.getId());
    }

    public static Breadcrumb getQuestionsBreadcrumbs(boolean active, Project project) {
        return new Breadcrumb(ProjectActionBean.class.getName(), "questions", false, "cz.bbmri.entity.Question.questions",
                active, "id", project.getId());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        setAuthProjectId(id);
        this.id = id;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        if (project == null) {
            if (id != null) {
                project = projectDAO.get(id);
            }
        }

        return project;
    }

    public MyPagedListHolder<Project> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Project> pagination) {
        this.pagination = pagination;
    }

    public MyPagedListHolder<Attachment> getAttachmentPagination() {
        return attachmentPagination;
    }

    public void setAttachmentPagination(MyPagedListHolder<Attachment> attachmentPagination) {
        this.attachmentPagination = attachmentPagination;
    }

    public MyPagedListHolder<Question> getQuestionPagination() {
        return questionPagination;
    }

    public void setQuestionPagination(MyPagedListHolder<Question> questionPagination) {
        this.questionPagination = questionPagination;
    }

    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"developer", "admin"})
    public Resolution all() {

        getBreadcrumbs().add(ProjectActionBean.getAllBreadcrumb(true));

        if (getOrderParam() == null) {
            // default
            getPagination().setOrderParam("created");
        }

        pagination.initiate(getPage(), getOrderParam(), isDesc());
//        pagination.setSource(new ArrayList<Project>(projectDAO.allOrderedBy(pagination.getOrderParam(), pagination.getDesc())));
        pagination.setSource(projectDAO.all());
        pagination.setEvent("all");

        return new ForwardResolution(View.Project.ALL);

    }


    @HandlesEvent("myProjects")
    @RolesAllowed({"authorized", "developer"})
    public Resolution myProjects() {

        getBreadcrumbs().add(ProjectActionBean.getMyBreadcrumb(true));

//        pagination.initiate(getPage(), getOrderParam(), isDesc());
//        if (getOrderParam() == null) {
//            // default
//            getPagination().setOrderParam("created");
//        }
        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(projectDAO.getByUser(getLoggedUser()));
        pagination.setEvent("myProjects");

        return new ForwardResolution(View.Project.ALL);

    }

    @HandlesEvent("detail")
    @RolesAllowed({"project_team_member if ${projectVisitor}"})
    public Resolution detail() {

        getProject();

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        getBreadcrumbs().add(ProjectActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(true, project));

        return new ForwardResolution(View.Project.DETAIL);
    }

    @HandlesEvent("attachments") /* Necessary for stripes security tag*/
    @RolesAllowed({"developer", "admin", "project_team_member if ${projectVisitor}"})
    public Resolution attachments() {
        getProject();

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        getBreadcrumbs().add(ProjectActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(false, getProject()));
        getBreadcrumbs().add(ProjectActionBean.getAttachmentsBreadcrumb(true, getProject()));

        attachmentPagination.initiate(getPage(), getOrderParam(), isDesc());
        attachmentPagination.setSource(new ArrayList<Attachment>(project.getAttachment()));
        attachmentPagination.setEvent("attachments");
        attachmentPagination.setIdentifier(project.getId());
        attachmentPagination.setIdentifierParam("id");

        return new ForwardResolution(View.Project.ATTACHMENTS);
    }

    @HandlesEvent("projectuser") /* Necessary for stripes security tag*/
    @RolesAllowed({"developer", "admin", "project_team_member if ${projectVisitor}"})
    public Resolution projectuser() {
        getProject();

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        getBreadcrumbs().add(ProjectActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(false, getProject()));
        getBreadcrumbs().add(ProjectActionBean.getProjectUserBreadcrumb(true, getProject()));

        return new ForwardResolution(View.Project.PROJECTUSER);
    }

    @HandlesEvent("questions") /* Necessary for stripes security tag*/
    @RolesAllowed({"developer", "admin", "project_team_member if ${projectVisitor}"})
    public Resolution questions() {
        getProject();

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        getBreadcrumbs().add(ProjectActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(false, getProject()));
        getBreadcrumbs().add(ProjectActionBean.getQuestionsBreadcrumbs(true, getProject()));

        return new ForwardResolution(View.Project.QUESTIONS);
    }

}

