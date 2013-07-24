package bbmri.action.Sample;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.Request;
import bbmri.entities.RequestGroup;
import bbmri.entities.RequestState;
import bbmri.service.RequestGroupService;
import bbmri.service.RequestService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/approveSampleRequest/{$event}/{request.id}")
public class ApproveRequestActionBean extends BasicActionBean {

    private static final String DETAIL = "/requestGroup_detail.jsp";
    private static final String APPROVE_REQUEST = "/sample_approve_request.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private List<RequestGroup> requestGroups;
    private Request request;

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

    public List<Request> getRequests(){
        return requestGroupService.getRequestsByRequestGroup(getRequestGroup().getId());
    }

    @DefaultHandler
    public Resolution display() {
        getRequestGroups();
        return new ForwardResolution(APPROVE_REQUEST);
    }

    public Resolution approve() {
        requestGroupService.changeRequestState(requestGroup.getId(), RequestState.APPROVED);
        getContext().getMessages().add(
                new SimpleMessage("Request group with id = {0} was APPROVED", requestGroup.getId())
        );
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution reject() {
        requestGroupService.changeRequestState(requestGroup.getId(), RequestState.DENIED);
        getContext().getMessages().add(
                       new SimpleMessage("Request group with id = {0} was DENIED", requestGroup.getId())
               );
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution detail() {
        requestGroup = requestGroupService.getById(requestGroup.getId());
        getContext().setRequestGroup(requestGroup);
        return new ForwardResolution(DETAIL);
    }

    public Resolution back(){
            return new ForwardResolution(this.getClass(), "display");
        }
}
