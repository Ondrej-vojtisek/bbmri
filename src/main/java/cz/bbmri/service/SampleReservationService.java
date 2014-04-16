package cz.bbmri.service;

import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.User;
import cz.bbmri.entities.enumeration.RequestState;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.4.14
 * Time: 0:17
 * To change this template use File | Settings | File Templates.
 */
public interface SampleReservationService extends SampleQuestionService {

    boolean setAsExpired(Long Id);

    SampleReservation get(Long id);

    List<SampleReservation> getSampleReservationsBySample(Long sampleId, String orderByParam, boolean desc);

    boolean createSampleReservation(SampleReservation sampleReservation, Long biobankId, User user,
                                    ValidationErrors errors);

    List<SampleQuestion> getSampleReservations(Long biobankId, RequestState requestState);

    boolean assignReservationToProject(Long sampleQuestionId, Long projectId, ValidationErrors errors);

    List<SampleReservation> getSampleReservationsOrderedByDate();

    List<SampleReservation> getSortedSampleReservations(Long userId, String orderByParam, boolean desc);

    boolean close(Long Id, ValidationErrors errors);

}
