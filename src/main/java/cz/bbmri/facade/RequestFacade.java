package cz.bbmri.facade;

import cz.bbmri.entities.SampleRequest;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public interface RequestFacade {

    SampleRequest getSampleRequest(Long sampleRequestId);

    boolean approveSampleRequest(Long sampleRequestId, ValidationErrors errors, Long loggedUserId);

    boolean denySampleRequest(Long sampleRequestId, ValidationErrors errors, Long loggedUserId);

    boolean deleteSampleRequest(Long sampleRequestId, ValidationErrors errors, Long loggedUserId);

    //List<RequestGroup> getRequestsByBiobankAndState(Long biobank);
}
