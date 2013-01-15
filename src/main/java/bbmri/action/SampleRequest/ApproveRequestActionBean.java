package bbmri.action.SampleRequest;

import bbmri.action.BasicActionBean;
import bbmri.action.MyActionBeanContext;
import bbmri.entities.Biobank;
import bbmri.entities.Request;
import bbmri.entities.RequestState;
import bbmri.entities.User;
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
    private List<Request> requests;
    private Request request;

    @SpringBean
    private RequestService requestService;

    public List<Request> getRequests() {
        Biobank biobank = getLoggedUser().getBiobank();
        if (biobank == null) {
            return null;
        }
        requests = requestService.getAllNewByBiobank(biobank.getId());
        return requests;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Resolution approve() {
        requestService.changeRequestState(request.getId(), RequestState.APPROVED);
        return new ForwardResolution("/sample_approve_request.jsp");
    }

    public Resolution reject() {
        requestService.changeRequestState(request.getId(), RequestState.DENIED);
        return new ForwardResolution("/sample_approve_request.jsp");
    }
}
