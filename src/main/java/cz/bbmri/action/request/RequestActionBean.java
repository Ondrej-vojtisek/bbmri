package cz.bbmri.action.request;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.action.project.ProjectActionBean;
import cz.bbmri.entities.RequestGroup;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.facade.RequestFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.1.14
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */

@HttpCache(allow = false)
@UrlBinding("/request/{$event}/{sampleRequestId}")
public class RequestActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private RequestFacade requestFacade;

    /* SampleQuestion identifier */
    private Long sampleRequestId;

    private SampleRequest sampleRequest;

    public Long getSampleRequestId() {
        return sampleRequestId;
    }

    public void setSampleRequestId(Long sampleRequestId) {
        this.sampleRequestId = sampleRequestId;
    }

    public SampleRequest getSampleRequest() {

        if (sampleRequest == null) {

            if (sampleRequestId != null) {

                sampleRequest = requestFacade.getSampleRequest(sampleRequestId);
            }
        }
        return sampleRequest;
    }

    public List<RequestGroup> getRequestGroups() {
        return getSampleRequest().getRequestGroups();
    }

    public boolean getIsSampleRequestNew() {
        return getSampleRequest().isNew();
    }

    public boolean getIsSampleRequestApproved() {
        return getSampleRequest().isApproved();
    }

    public boolean getIsSampleRequestDenied() {
        return getSampleRequest().isDenied();
    }

    private boolean isSampleRequestDelivered() {
        return getSampleRequest().isDelivered();
    }


    @DontValidate
    @DefaultHandler
    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer", "project_team_member", "biobank_operator"})
    public Resolution detail() {
        return new ForwardResolution(REQUEST_DETAIL);
    }


    /* Here can be self approve - withdraw */
    @DontValidate
    @HandlesEvent("approve")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution approve() {
        if (!requestFacade.approveSampleRequest(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class).addParameter("sampleRequestId", sampleRequestId);
        }
        successMsg(null);
        return new RedirectResolution(RequestActionBean.class).addParameter("sampleRequestId", sampleRequestId);
    }

    @DontValidate
    @HandlesEvent("deny")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution deny() {
        if (!requestFacade.denySampleRequest(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class).addParameter("sampleRequestId", sampleRequestId);
        }
        successMsg(null);
        return new RedirectResolution(RequestActionBean.class).addParameter("sampleRequestId", sampleRequestId);
    }

    @DontValidate
    @HandlesEvent("delete")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor && isSampleRequestNew}"})
    public Resolution delete() {
        Long projectId = getSampleRequest().getProject().getId();
        if (!requestFacade.deleteSampleRequest(sampleRequestId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(RequestActionBean.class).addParameter("sampleRequestId", sampleRequestId);
        }
        successMsg(null);
        return new RedirectResolution(ProjectActionBean.class, "sampleRequests").addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("createRequestGroup")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution createRequestGroup() {
        return new ForwardResolution(RequestActionBean.class).addParameter("sampleRequestId", sampleRequestId);
    }



}
