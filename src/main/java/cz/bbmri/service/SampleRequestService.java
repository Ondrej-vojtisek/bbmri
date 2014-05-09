package cz.bbmri.service;

import cz.bbmri.entities.SampleRequest;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * API for handling SampleRequests
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface SampleRequestService extends SampleQuestionService {

    /**
     * Change state of sampleRequest as closed. It means that set of samples has been chosen.
     *
     * @param Id           - ID of sampleRequest
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - ID of event initiator. Notification about new file will be sent to project team except
     *                     initiator (BROADCAST)
     * @return true/false
     */
    boolean close(Long Id, ValidationErrors errors, Long loggedUserId);

    /**
     * Store sampleRequest in DB
     *
     * @param sampleRequest - new instance of sampleRequest
     * @param projectId     - ID of project, defining context in which are data requested
     * @param biobankId     - ID of biobank, where are samples requested
     * @param errors        - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean createSampleRequest(SampleRequest sampleRequest, Long projectId, Long biobankId,
                                ValidationErrors errors);

    /**
     * Change state of sampleRequest again to approved. Set of samples is not enough for project
     *
     * @param sampleRequestId - ID of sampleRequest
     * @param errors          - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean denyChosenSet(Long sampleRequestId, ValidationErrors errors);

    /**
     * Change state of sampleRequest to confirmed
     *
     * @param sampleRequestId - ID of sampleRequest
     * @param errors          - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean confirmChosenSet(Long sampleRequestId, ValidationErrors errors);

    /**
     * Change state of sampleRequest to delivered
     *
     * @param sampleRequestId - ID of sampleRequest
     * @param errors          - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean setAsDelivered(Long sampleRequestId, ValidationErrors errors);


    /**
     * Return list of sorted sampleRequest associated with project
     *
     * @param projectId    - ID of project
     * @param orderByParam
     * @param desc
     * @return sorted list of sampleRequests
     */
    List<SampleRequest> getSortedSampleRequest(Long projectId, String orderByParam, boolean desc);


}
