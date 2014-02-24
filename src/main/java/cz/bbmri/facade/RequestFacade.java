package cz.bbmri.facade;

import cz.bbmri.entities.SampleRequest;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

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

    boolean closeSampleRequest(Long sampleRequestId, ValidationErrors errors, Long loggedUserId);

    boolean deleteSampleRequest(Long sampleRequestId, ValidationErrors errors, Long loggedUserId);

    boolean denyChosenSet(Long sampleRequestId, ValidationErrors errors, Long loggedUserId);

    boolean confirmChosenSet(Long sampleRequestId, ValidationErrors errors, Long loggedUserId);

    boolean setAsDelivered(Long sampleRequestId, ValidationErrors errors, Long loggedUserId);

    List<SampleRequest> getNewSampleRequests(Long biobankId);

    boolean createRequests(List<Long> sampleIds, Long sampleRequestId, ValidationErrors errors, List<Message> messages);

    boolean changeRequestedAmount(Long requestId, boolean increase, int difference, ValidationErrors errors);

    boolean deleteRequest(Long requestId, ValidationErrors errors);
}
