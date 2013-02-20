package bbmri.action.SampleRequest;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.Request;
import bbmri.entities.RequestGroup;
import bbmri.entities.RequestState;
import bbmri.service.RequestGroupService;
import bbmri.service.RequestService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/approveSampleRequest/{$event}/{request.id}")
public class ApproveRequestActionBean extends BasicActionBean {
    private List<RequestGroup> requestGroups;
    private Request request;

    @SpringBean
    private RequestService requestService;

    @SpringBean
    private RequestGroupService requestGroupService;

    private RequestGroup requestGroup;

    public List<RequestGroup> getRequestGroups() {
        Biobank biobank = getLoggedUser().getBiobank();
        if (biobank == null) {
            return null;
        }

        requestGroups = requestGroupService.getByBiobankAndState(biobank.getId(), RequestState.NEW);
        return requestGroups;
    }

    public RequestGroup getRequestGroup() {
        if (requestGroup == null) {
            requestGroup = getContext().getRequestGroup();
        }
        return requestGroup;
    }

    public void setRequestGroup(RequestGroup requestGroup) {
        this.requestGroup = requestGroup;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @DefaultHandler
    public Resolution display() {
        getRequestGroups();
        return new ForwardResolution("/sample_approve_request.jsp");
    }

    public Resolution approve() {
        requestGroupService.changeRequestState(requestGroup.getId(), RequestState.APPROVED);
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution reject() {
        requestGroupService.changeRequestState(requestGroup.getId(), RequestState.DENIED);
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution detail() {
        requestGroup = requestGroupService.getById(requestGroup.getId());
        getContext().setRequestGroup(requestGroup);
        return new ForwardResolution("/requestGroup_detail.jsp");
    }
}
