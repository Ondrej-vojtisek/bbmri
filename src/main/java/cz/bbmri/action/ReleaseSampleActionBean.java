package cz.bbmri.action;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.RequestState;
import net.sourceforge.stripes.action.UrlBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/releaseSample/{$event}/{loggedUser.id}")
public class ReleaseSampleActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String REQUESTGROUP_DETAIL = "/requestGroup_detail.jsp";

    private Sample sample;
    private List<Sample> samples;
    private Biobank biobank;

    public Biobank getBiobank() {
       //TODO
       /* if (biobank == null) {
            BiobankAdministrator ba = getLoggedUser().getBiobankAdministrator();
            biobank = biobankService.get(ba.getBiobank().getId());

        }

        return biobank; */
        return null;
    }



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
        return requestGroupService.getByBiobankAndState(getBiobank().getId(), RequestState.DELIVERED);
    }

    public List<RequestGroup> getRequestGroups() {
        return requestGroupService.getByBiobankAndState(getBiobank().getId(), RequestState.APPROVED);
    }
    /*
    public Resolution release() {
        requestGroupService.changeRequestState(requestGroup.getId(), RequestState.EQUIPPED);
        getContext().getMessages().add(
                new SimpleMessage("Samples from request group with id = {0} was equipped", requestGroup.getId())
        );
        return new ForwardResolution("/sample_release.jsp");
    }  */

}
