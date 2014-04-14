package cz.bbmri.service;

import cz.bbmri.entities.Request;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.facade.exceptions.InsuficientAmountOfSamplesException;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public interface RequestService extends BasicService<Request> {

    int createRequests(List<Long> sampleIds, Long sampleQuestionId) throws InsuficientAmountOfSamplesException;

    boolean createRequests(List<Long> sampleIds, Long sampleQuestionId, ValidationErrors errors, List<Message> messages);

    boolean changeRequestedAmount(Long requestId, boolean increase, int difference, ValidationErrors errors);

    boolean deleteRequest(Long requestId, ValidationErrors errors);
}
