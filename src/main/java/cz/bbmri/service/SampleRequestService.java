package cz.bbmri.service;

import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.enumeration.RequestState;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.4.14
 * Time: 0:43
 * To change this template use File | Settings | File Templates.
 */
public interface SampleRequestService extends SampleQuestionService {

    boolean close(Long Id, ValidationErrors errors, Long loggedUserId);

    boolean createSampleRequest(SampleRequest sampleRequest, Long projectId, Long biobankId,
                                ValidationErrors errors);

    List<SampleQuestion> getSampleRequests(Long biobankId, RequestState requestState);

    boolean denyChosenSet(Long sampleRequestId, ValidationErrors errors);

    boolean confirmChosenSet(Long sampleRequestId, ValidationErrors errors);

    boolean setAsDelivered(Long sampleRequestId, ValidationErrors errors);

    List<SampleRequest> getSortedSampleRequest(Long projectId, String orderByParam, boolean desc);
}
