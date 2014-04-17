package cz.bbmri.service;

import cz.bbmri.entities.Request;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface RequestService extends Get<Request> {

    boolean remove(Long requestId, ValidationErrors errors);

    boolean createRequests(List<Long> sampleIds, Long sampleQuestionId, ValidationErrors errors, List<Message> messages);

    boolean changeRequestedAmount(Long requestId, boolean increase, int difference, ValidationErrors errors);

}
