package cz.bbmri.service;

import cz.bbmri.entities.Request;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * API for handling Requests
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface RequestService extends Get<Request> {

    /**
     * Remove request identified by given ID. Allocated sample aliquots are freed (number of available samples are
     * returned to state before request).
     * This variant of method is used from auto triggered event (reservation validity check)
     *
     * @param requestId    - ID of request
     * @return true/false
     */
    boolean remove(Long requestId);

    /**
     * Remove request identified by given ID. Allocated sample aliquots are freed (number of available samples are
     * returned to state before request).
     *
     * @param requestId    - ID of request
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - id of user who initiated event
     * @return true/false
     */
    boolean remove(Long requestId, ValidationErrors errors, Long loggedUserId);

    /**
     * Create request for all samples given in sampleIds. All request is initiated with default amount of requested
     * aliquots.
     *
     * @param sampleIds        - list of sample identifiers. For each sample will be created one request
     * @param sampleQuestionId - set of requested samples is associated with this sampleQuestion
     * @param errors           - in case of error, error messages will be stored into errors
     * @param messages         - where store details about operation
     * @param loggedUserId     - id of user who initiated event
     * @return true/false
     */
    boolean createRequests(List<Long> sampleIds, Long sampleQuestionId, ValidationErrors errors,
                           List<Message> messages, Long loggedUserId);

    /**
     * Change amount of requested aliquots
     *
     * @param requestId    - ID of request which number of requested aliquots will be changed
     * @param increase     - true means increase, false decrease
     * @param difference   - difference against previous number
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - id of user who initiated event
     * @return true/false
     */
    boolean changeRequestedAmount(Long requestId, boolean increase, int difference,
                                  ValidationErrors errors, Long loggedUserId);

}
