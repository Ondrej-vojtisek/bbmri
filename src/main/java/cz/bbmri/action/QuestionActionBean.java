package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.QuestionDAO;
import cz.bbmri.dao.QuestionDAO;
import cz.bbmri.entity.Attachment;
import cz.bbmri.entity.Question;
import cz.bbmri.entity.Request;
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
public class QuestionActionBean extends ComponentActionBean {

    @SpringBean
    private QuestionDAO questionDAO;

    private Long id;

    private Question question;

    private MyPagedListHolder<Question> pagination;

    private MyPagedListHolder<Request> requestPagination;

    public static Breadcrumb getAllBreadcrumb(boolean active) {
        return new Breadcrumb(QuestionActionBean.class.getName(), "all", false, "" +
                "cz.bbmri.entity.Question.questions", active);
    }

    public static Breadcrumb getDetailBreadcrumb(boolean active, Question question) {
        return new Breadcrumb(QuestionActionBean.class.getName(), "detail", false, "cz.bbmri.entity.Question.question",
                active, "id", question.getId());
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

        pagination = new MyPagedListHolder<Question>(new ArrayList<Question>(questionDAO.all()));
        pagination.initiate(getPage(), getOrderParam(), isDesc());
        pagination.setEvent("all");

        return new ForwardResolution(View.Question.ALL);

    }

    @HandlesEvent("detail")
    @RolesAllowed({"developer", "admin"})
    public Resolution detail() {

        getQuestion();

        if (question == null) {
            return new ForwardResolution(View.Question.NOTFOUND);
        }

        getBreadcrumbs().add(QuestionActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(QuestionActionBean.getDetailBreadcrumb(true, question));

        return new ForwardResolution(View.Question.DETAIL);
    }

}

