package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.RequestState;

import java.util.List;

/**
 * Interface to handle instances of SampleQuestion stored in database. Also classes extended from sampleQuestion are managed
 * by this interface.
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
public interface SampleQuestionDao extends BasicDao<SampleQuestion> {

    /**
     * Return all sampleRequests asociated with given biobank and with given state
     *
     * @param biobank
     * @param requestState
     * @return List of sampleRequest by given parameters
     */
    List<SampleRequest> getSampleRequests(Biobank biobank, RequestState requestState);

    /**
     * Return all sampleReservation asociated with given biobank and with given state
     *
     * @param biobank
     * @param requestState
     * @return List of sampleReservation by given parameters
     */
    List<SampleReservation> getSampleReservations(Biobank biobank, RequestState requestState);

    /**
     * Return all sampleRequests asociated with given project. Output is sorted by given parameters.
     *
     * @param project
     * @param orderByParam - select column by which will the result be sorted
     * @param desc         - flag determining if order will be DESC (true) or default ASC (false)
     * @return sorted list of all sampleRequest associated with specified project
     */
    List<SampleRequest> getSampleRequestsSorted(Project project, String orderByParam, boolean desc);

    /**
     * Return all sampleReservations asociated with given user. Output is sorted by given parameters.
     *
     * @param user
     * @param orderByParam - select column by which will the result be sorted
     * @param desc         - flag determining if order will be DESC (true) or default ASC (false)
     * @return sorted list of all sampleReservation associated with specified user
     */
    List<SampleReservation> getSampleReservationsSorted(User user, String orderByParam, boolean desc);

    /**
     * Return all sampleReservations ordered by date
     *
     * @return List of ordered sampleReservations
     */
    List<SampleReservation> getSampleReservationsOrderedByDate();

    /**
     * Return all instances of SampleReservation associated with sample ordered by given parameter. Desc param changes if it is ordered DESC or ASC
     *
     * @param sample
     * @param orderByParam - select column by which will the result be sorted
     * @param desc         - flag determining if order will be DESC (true) or default ASC (false)
     * @return sorted list of all sampleReservation associated with specified sample
     */
    List<SampleReservation> getSampleReservationsBySample(Sample sample, String orderByParam, boolean desc);
}
