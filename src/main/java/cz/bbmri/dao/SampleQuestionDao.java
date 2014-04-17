package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.RequestState;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */
public interface SampleQuestionDao extends BasicDao<SampleQuestion> {

    List<SampleQuestion> getSampleRequests(Biobank biobank, RequestState requestState);

    List<SampleQuestion> getSampleReservations(Biobank biobank, RequestState requestState);

    List<SampleRequest> getSampleRequestsSorted(Project project, String orderByParam, boolean desc);

    List<SampleReservation> getSampleReservationsSorted(User user, String orderByParam, boolean desc);

    // CLOSED RESERVATIONS
    List<SampleReservation> getSampleReservationsOrderedByDate();

    List<SampleReservation> getSampleReservationsBySample(Sample sample, String orderByParam, boolean desc);
}
