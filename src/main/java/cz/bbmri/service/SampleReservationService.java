package cz.bbmri.service;

import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.User;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * API for handling SampleReservations
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface SampleReservationService extends SampleQuestionService {

    /**
     * Change state of request as expired
     *
     * @param Id - ID of sampleReservation
     * @return true/false
     */
    boolean setAsExpired(Long Id);

    /**
     * Return instance of sampleReservation by its unique identifier
     *
     * @param id - identifier
     * @return sampleReservation with given ID or null
     */
    SampleReservation get(Long id);

    /**
     * Return sorted list of sampleReservations associated with given Sample
     *
     * @param sampleId     - ID of sample
     * @param orderByParam
     * @param desc
     * @return sorted list of sampleReservations
     */
    List<SampleReservation> getSampleReservationsBySample(Long sampleId, String orderByParam, boolean desc);

    /**
     * Store instance of sampleReservation in DB
     *
     * @param sampleReservation - new instance of sampleReservation
     * @param biobankId         - ID of biobank, where are samples requested
     * @param user              - user who is reserving samples for his future project
     * @param errors            - in case of error, error messages will be stored into errors
     * @return true/false
     */
    boolean createSampleReservation(SampleReservation sampleReservation, Long biobankId, User user,
                                    ValidationErrors errors);

//    /**
//     * Return list of SampleReservations by biobank and state of reservation
//     *
//     * @param biobankId    - ID of biobank
//     * @param requestState - state of
//     * @return list of SampleReservations
//     */
//    List<SampleReservation> getSampleReservations(Long biobankId, RequestState requestState);

    /**
     * Change sampleReservation to sampleRequest -> the project why sampleReservation have been created was uploaded.
     *
     * @param sampleQuestionId - ID of sampleQuestion
     * @param projectId        - which project will be assigned
     * @param errors           - in case of error, error messages will be stored into errors
     * @param loggedUserId     - id of user who initiated event
     * @return true/false
     */
    boolean assignReservationToProject(Long sampleQuestionId, Long projectId,
                                       ValidationErrors errors, Long loggedUserId);

    /**
     * Return list of sampleReservations ordered by date of creation
     *
     * @return ordered list of sampleReservation
     */
    List<SampleReservation> getSampleReservationsOrderedByDate();

    /**
     * Return sorted list of all sampleReservations of given user
     *
     * @param userId       - ID of user
     * @param orderByParam
     * @param desc
     * @return list of sampleReservations
     */
    List<SampleReservation> getSortedSampleReservations(Long userId, String orderByParam, boolean desc);

    /**
     * Change state of sampleReservation to closed
     *
     * @param Id           - ID of sampleReservation
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - id of user who initiated event
     * @return true/false
     */
    boolean close(Long Id, ValidationErrors errors, Long loggedUserId);

}
