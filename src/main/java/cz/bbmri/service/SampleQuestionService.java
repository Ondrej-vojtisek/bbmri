package cz.bbmri.service;

import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;


/**
 * API for handling SampleQuestions
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface SampleQuestionService extends Get<SampleQuestion> {

    /**
     * Remove sampleQuestion and all requests
     *
     * @param id           - ID of sampleQuestion to be removed
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - ID of event initiator. Notification about new file will be sent to project team except
     *                     initiator (BROADCAST)
     * @return true/false
     */
    boolean remove(Long id, ValidationErrors errors, Long loggedUserId);

    /**
     * Mark sampleQuestion as approved (change sampleQuestion state)
     *
     * @param sampleQuestionId - ID of sampleQuestion
     * @param errors           - in case of error, error messages will be stored into errors
     * @param loggedUserId     - ID of event initiator. Notification about new file will be sent to project team except
     *                         initiator (BROADCAST)
     * @return true/false
     */
    boolean approve(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    /**
     * Mark sampleQuestion as denied (change sampleQuestion state)
     *
     * @param sampleQuestionId - ID of sampleQuestion
     * @param errors           - in case of error, error messages will be stored into errors
     * @param loggedUserId     - ID of event initiator. Notification about new file will be sent to project team except
     *                         initiator (BROADCAST)
     * @return true/false
     */
    boolean deny(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    /**
        * Return list of sorted sampleQuestions associated with biobank
        *
        * @param biobankId    - ID of project
        * @param orderByParam
        * @param desc
        * @return sorted list of sampleRequests
        */
       List<SampleQuestion> getSortedSampleQuestions(Long biobankId, String orderByParam, boolean desc);
}
