package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.BiobankDAO;
import cz.bbmri.dao.ProjectDAO;
import cz.bbmri.dao.QuestionDAO;
import cz.bbmri.dao.QuestionDAO;
import cz.bbmri.entity.*;
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
@UrlBinding("/question/{$event}/{id}")
public class QuestionActionBean extends AuthorizationActionBean {

    @SpringBean
    private QuestionDAO questionDAO;

    @SpringBean
    private ProjectDAO projectDAO;

    private Long id;

    private Long projectId;

    private Question question;

    private MyPagedListHolder<Question> pagination = new MyPagedListHolder<Question>(new ArrayList<Question>());

    private MyPagedListHolder<Request> requestPagination = new MyPagedListHolder<Request>(new ArrayList<Request>());

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(QuestionActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.Question.questions", active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Question question) {
        return new Breadcrumb(QuestionActionBean.class.getName(), "detail", false, "cz.bbmri.entity.Question.question",
                active, "id", question.getId());
    }

    public static Breadcrumb getAddBreadcrumb(boolean active, Project project) {
        return new Breadcrumb(QuestionActionBean.class.getName(), "add", false, "" +
                "cz.bbmri.entity.Question.add", active, "projectId", project.getId());
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        if (question == null) {
            if (id != null) {
                question = questionDAO.get(id);
            }
        }

        return question;
    }

    public MyPagedListHolder<Question> getPagination() {
        return pagination;
    }

    public void setPagination(MyPagedListHolder<Question> pagination) {
        this.pagination = pagination;
    }

    public MyPagedListHolder<Request> getRequestPagination() {
        return requestPagination;
    }

    public void setRequestPagination(MyPagedListHolder<Request> requestPagination) {
        this.requestPagination = requestPagination;
    }

    @DefaultHandler
    @HandlesEvent("all")
    @RolesAllowed({"developer"})
    public Resolution all() {

        getBreadcrumbs().add(QuestionActionBean.getAllBreadcrumb(true));

        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setSource(new ArrayList<Question>(questionDAO.all()));
        pagination.setEvent("all");

        return new ForwardResolution(View.Question.ALL);

    }

    @HandlesEvent("detail")
    @RolesAllowed({"project_team_member if ${projectVisitor}", "developer"})
    public Resolution detail() {

        getQuestion();

        if (question == null) {
            return new ForwardResolution(View.Question.NOTFOUND);
        }

        getBreadcrumbs().add(QuestionActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(QuestionActionBean.getDetailBreadcrumb(true, question));

        return new ForwardResolution(View.Question.DETAIL);
    }

    @HandlesEvent("add")
    @RolesAllowed({"project_team_member_confirmed if ${projectExecutor}"})
    public Resolution add() {

        Project project = projectDAO.get(projectId);
        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        getBreadcrumbs().add(QuestionActionBean.getAddBreadcrumb(true, project));

        return new ForwardResolution(View.Withdraw.ADD);

    }

}

