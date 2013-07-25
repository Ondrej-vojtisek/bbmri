package bbmri.action;

import bbmri.entities.Request;
import bbmri.entities.RequestGroup;
import net.sourceforge.stripes.action.*;
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
public class RequestGroupDetailActionBean extends BasicActionBean{

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String REQUESTGROUP_DETAIL = "/requestGroup_detail.jsp";
    private static final String REQUEST_DETAIL = "/requestDetail.jsp";
    private static final String REQUEST_DETAIL_EXPORT = "/requestGroup_detail_export.jsp";

    public RequestGroup getRequestGroup(){
        return getRequestGroupBSC();
    }

    public List<Request> getRequests(){
          return requestGroupService.getRequestsByRequestGroup(getRequestGroup().getId());
      }


    @DefaultHandler
    @DontValidate
    public Resolution requestGroupDetail(){
        return new ForwardResolution(REQUESTGROUP_DETAIL);
    }

        @DontValidate
        public Resolution edit(){
            return new ForwardResolution(REQUEST_DETAIL);
        }

    @DontValidate
    public Resolution export(){
        return new ForwardResolution(REQUEST_DETAIL_EXPORT);
    }
}
