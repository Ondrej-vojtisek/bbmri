package bbmri.action.requestGroup;

import bbmri.action.BasicActionBean;
import bbmri.entities.Request;
import bbmri.entities.RequestGroup;
import bbmri.entities.Sample;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.7.13
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/requestGroupDetail")
public class RequestGroupDetailActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String REQUESTGROUP_DETAIL = "/webpages/requestGroup/requestGroup_detail.jsp";
    private static final String REQUEST_DETAIL = "/requestDetail.jsp";
    private static final String REQUEST_DETAIL_EXPORT = "/webpages/requestGroup/requestGroup_detail_export.jsp";

    public RequestGroup getRequestGroup() {
        return getRequestGroupBSC();
    }

    public List<Request> getRequests() {
        return requestGroupService.eagerGet(getRequestGroup().getId(), true).getRequests();
    }

    @ValidateNestedProperties(value = {
                @Validate(on = {"update"}, required = true,
                        field = "numOfRequested",
                        minvalue = 1, maxvalue = 100)
        })
    private Request request;

    public Request getRequest() {
        if(request == null){
            request = getRequestBSC();
        }
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Sample getSample() {
        return getRequest().getSample();
    }

    @DefaultHandler
    @DontValidate
    public Resolution requestGroupDetail() {
        return new ForwardResolution(REQUESTGROUP_DETAIL);
    }

    @HandlesEvent("edit")
    @DontValidate
    public Resolution edit() {
        Long requestId = Long.parseLong(getContext().getRequest().getParameter("request"));
        Request request = requestService.get(requestId);
        getContext().setRequestId(requestId);

        return new ForwardResolution(REQUEST_DETAIL);
    }

    @DontValidate
    public Resolution export() {
        return new ForwardResolution(REQUEST_DETAIL_EXPORT);
    }

    @DontValidate
    public Resolution cancel() {
        return new ForwardResolution(REQUESTGROUP_DETAIL);
    }

       public Resolution update() {
        requestService.update(request);
        return new ForwardResolution(REQUESTGROUP_DETAIL);
       }
}
