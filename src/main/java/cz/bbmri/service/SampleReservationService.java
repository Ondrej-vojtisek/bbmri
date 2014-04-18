package cz.bbmri.service;

import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.RequestState;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface SampleReservationService extends SampleQuestionService {

    boolean setAsExpired(Long Id);

    SampleReservation get(Long id);

    List<SampleReservation> getSampleReservationsBySample(Long sampleId, String orderByParam, boolean desc);

    boolean createSampleReservation(SampleReservation sampleReservation, Long biobankId, User user,
                                    ValidationErrors errors);

    List<SampleReservation> getSampleReservations(Long biobankId, RequestState requestState);

    boolean assignReservationToProject(Long sampleQuestionId, Long projectId, ValidationErrors errors);

    List<SampleReservation> getSampleReservationsOrderedByDate();

    List<SampleReservation> getSortedSampleReservations(Long userId, String orderByParam, boolean desc);

    boolean close(Long Id, ValidationErrors errors);

}
