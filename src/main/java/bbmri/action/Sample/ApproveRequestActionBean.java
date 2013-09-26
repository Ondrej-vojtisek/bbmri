package bbmri.action.sample;

import bbmri.action.BasicActionBean;
import bbmri.entities.*;
import net.sourceforge.stripes.action.*;
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
@UrlBinding("/approveSampleRequest")
public class ApproveRequestActionBean extends BasicActionBean {

    private static final String DETAIL = "/requestGroup_detail.jsp";
    private static final String APPROVE_REQUEST = "/sample_approve_request.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private List<RequestGroup> requestGroups;
    private Request request;

    private RequestGroup requestGroup;

    public List<RequestGroup> getRequestGroups() {
        BiobankAdministrator ba = getLoggedUser().getBiobankAdministrator();
        Biobank biobank = biobankService.get(ba.getBiobank().getId());
        if (biobank == null) {
            return null;
        }

        requestGroups = requestGroupService.getByBiobankAndState(biobank.getId(), RequestState.NEW);
        return requestGroups;
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
        RequestGroup rqg = requestGroupService.get(getContext().getRequestGroupId());
        return rqg.getRequests();
       // return requestGroupService.getRequestsByRequestGroup(getContext().getRequestGroupId());
    }
    @DontValidate
    @DefaultHandler
    public Resolution display() {
        getRequestGroups();
        return new ForwardResolution(APPROVE_REQUEST);
    }
    @DontValidate
    public Resolution approve() {
        requestGroupService.approveRequestState(requestGroup.getId());
        getContext().getMessages().add(
                new SimpleMessage("Request group with id = {0} was APPROVED", requestGroup.getId())
        );
        return new RedirectResolution(this.getClass(), "display");
    }
    @DontValidate
    public Resolution reject() {
        requestGroupService.denyRequestState(requestGroup.getId());
        getContext().getMessages().add(
                       new SimpleMessage("Request group with id = {0} was DENIED", requestGroup.getId())
               );
        return new RedirectResolution(this.getClass(), "display");
    }
    @DontValidate
    public Resolution detail() {
        requestGroup = requestGroupService.get(requestGroup.getId());
        getContext().setRequestGroupId(requestGroup);
        return new ForwardResolution(DETAIL);
    }
    @DontValidate
    public Resolution back(){
            return new ForwardResolution(this.getClass(), "display");
        }
}
