package bbmri.action;

import bbmri.entities.Biobank;
import bbmri.entities.RequestGroup;
import bbmri.entities.RequestState;
import bbmri.entities.Sample;
import bbmri.service.RequestGroupService;
import bbmri.service.RequestService;
import bbmri.service.SampleService;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/releaseSample/{$event}/{loggedUser.id}")
public class ReleaseSampleActionBean extends BasicActionBean {

    private Sample sample;
    private List<Sample> samples;
    private Biobank biobank;

    public Biobank getBiobank() {
        if (biobank == null) {
            biobank = getLoggedUser().getBiobank();
        }
        return biobank;
    }

    @SpringBean
    private RequestService requestService;

    @SpringBean
    private SampleService sampleService;

    @SpringBean
    private RequestGroupService requestGroupService;

    private RequestGroup requestGroup;

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public RequestGroup getRequestGroup() {
        return requestGroup;
    }

    public void setRequestGroup(RequestGroup requestGroup) {
        this.requestGroup = requestGroup;
    }


    public List<RequestGroup> getReleasedRequestGroups() {
        return requestGroupService.getByBiobankAndState(getBiobank().getId(), RequestState.EQUIPPED);
    }

    public List<RequestGroup> getRequestGroups() {
        return requestGroupService.getByBiobankAndState(getBiobank().getId(), RequestState.APPROVED);
    }

    public Resolution release() {
        requestGroupService.changeRequestState(requestGroup.getId(), RequestState.EQUIPPED);
        getContext().getMessages().add(
                new SimpleMessage("Samples from request group with id = {0} was equipped", requestGroup.getId())
        );
        return new ForwardResolution("/sample_release.jsp");
    }

}
