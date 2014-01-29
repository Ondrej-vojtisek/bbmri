package cz.bbmri.action.request;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.facade.BiobankFacade;
import cz.bbmri.facade.RequestFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.1.14
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */

@HttpCache(allow = false)
@UrlBinding("/request/{$event}/{sampleQuestionId}")
public class RequestActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private RequestFacade requestFacade;

    @SpringBean
    private BiobankFacade biobankFacade;

    public boolean getAllowedManager() {
        return biobankFacade.hasPermission(Permission.MANAGER, biobankId, getContext().getMyId());
    }

    public boolean getAllowedEditor() {
        return biobankFacade.hasPermission(Permission.EDITOR, biobankId, getContext().getMyId());
    }

    public boolean getAllowedExecutor() {
        return biobankFacade.hasPermission(Permission.EXECUTOR, biobankId, getContext().getMyId());
    }

    public boolean getAllowedVisitor() {
        return biobankFacade.hasPermission(Permission.VISITOR, biobankId, getContext().getMyId());
    }

    private Long biobankId;

    /* SampleQuestion identifier */
    private Long sampleQuestionId;

    private SampleQuestion sampleQuestion;

    public Long getSampleQuestionId() {
        return sampleQuestionId;
    }

    public void setSampleQuestionId(Long sampleQuestionId) {
        this.sampleQuestionId = sampleQuestionId;
    }

    public Long getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(Long biobankId) {
        this.biobankId = biobankId;
    }

    public SampleQuestion getSampleQuestion() {

        if (sampleQuestion == null) {

            if (sampleQuestionId != null) {

                sampleQuestion = requestFacade.getSampleQuestion(sampleQuestionId);
            }
        }
        return sampleQuestion;
    }

    public boolean getIsNew() {
        return getSampleQuestion().isNew();
    }

    public boolean getIsApproved() {
        return getSampleQuestion().isApproved();
    }

    public boolean getIsDenied() {
        return getSampleQuestion().isDenied();
    }

    private boolean isDelivered() {
        return getSampleQuestion().isDelivered();
    }


    @DontValidate
    @DefaultHandler
    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer", "project_team_member", "biobank_operator"})
    public Resolution detail() {
        return new ForwardResolution(REQUEST_DETAIL);
    }

    @DontValidate
    @HandlesEvent("approve")
    @RolesAllowed({"biobank_operator if ${allowedExecutor}"})
    public Resolution approve() {
        if (!requestFacade.approveSampleQuestion(sampleQuestionId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class).addParameter("sampleQuestionId", sampleQuestionId);
        }
        successMsg(null);
        return new RedirectResolution(RequestActionBean.class).addParameter("sampleQuestionId", sampleQuestionId);
    }

    @DontValidate
    @HandlesEvent("deny")
    @RolesAllowed({"biobank_operator if ${allowedExecutor}"})
    public Resolution deny() {
        if (!requestFacade.denySampleQuestion(sampleQuestionId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class).addParameter("sampleQuestionId", sampleQuestionId);
        }
        successMsg(null);
        return new RedirectResolution(RequestActionBean.class).addParameter("sampleQuestionId", sampleQuestionId);
    }

    @DontValidate
    @HandlesEvent("delete")
    @RolesAllowed({"project_team_member"})     // if ${allowedExecutor}
    public Resolution delete() {
        Long projectId = getSampleQuestion().getProject().getId();
        return new RedirectResolution(ProjectActionBean.class, "detail").addParameter("id", projectId);
    }
}
