package cz.bbmri.service;

import cz.bbmri.entities.Sample;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.enumeration.RequestState;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public interface SampleQuestionService extends BasicService<SampleQuestion>{

    SampleRequest create(SampleRequest sampleRequest, Long biobankId, Long projectId);

    SampleQuestion create(SampleQuestion sampleQuestion, Long biobankId);

    List<SampleQuestion> getSampleReservations(Long biobankId, RequestState requestState);

    List<SampleQuestion> getSampleRequests(Long biobankId, RequestState requestState);

    boolean assignReservationToProject(Long sampleQuestionId, Long projectId);

    List<SampleReservation> getSampleReservationsOrderedByDate();

    List<SampleQuestion> getSortedSampleQuestions(Long biobankId, String orderByParam, boolean desc);

    List<SampleReservation> getSortedSampleReservations(Long userId, String orderByParam, boolean desc);

    List<SampleReservation> getSampleReservationsBySample(Long sampleId, String orderByParam, boolean desc);

    boolean approveSampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean denySampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean closeSampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean deleteSampleQuestion(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean denyChosenSet(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean confirmChosenSet(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean setAsDelivered(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean createSampleRequest(SampleRequest sampleRequest, Long projectId, Long biobankId, ValidationErrors errors);

    boolean createSampleQuestion(SampleQuestion sampleQuestion, Long biobankId,
                                           ValidationErrors errors);

    boolean assignReservationToProject(Long sampleQuestionId, Long projectId, ValidationErrors errors);

    List<SampleRequest> getSortedSampleRequest(Long projectId, String orderByParam, boolean desc);


}
