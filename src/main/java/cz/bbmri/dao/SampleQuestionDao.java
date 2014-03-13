package cz.bbmri.dao;

import cz.bbmri.entities.*;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.enumeration.RequestState;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public interface SampleQuestionDao extends BasicDao<SampleQuestion> {

    List<SampleQuestion> getSampleRequests(Biobank biobank, RequestState requestState);

    List<SampleQuestion> getSampleReservations(Biobank biobank, RequestState requestState);

    List<SampleQuestion> getSortedSampleQuestions(Biobank biobank, String orderByParam, boolean desc);

    List<SampleRequest> getSampleRequestsSorted(Project project, String orderByParam, boolean desc);

    List<SampleReservation> getSampleReservationsSorted(User user, String orderByParam, boolean desc);

    // CLOSED RESERVATIONS
    List<SampleReservation> getSampleReservationsOrderedByDate();

    List<SampleReservation> getSampleReservationsBySample(Sample sample, String orderByParam, boolean desc);
}
