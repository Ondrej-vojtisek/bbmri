package cz.bbmri.facade;

import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.SampleReservation;
import net.sourceforge.stripes.action.Message;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public interface RequestFacade {

    SampleQuestion getSampleQuestion(Long sampleQuestionId);

    boolean approveSampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean denySampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean closeSampleRequest(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean deleteSampleQuestion(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean denyChosenSet(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean confirmChosenSet(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean setAsDelivered(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean createRequests(List<Long> sampleIds, Long sampleQuestionId, ValidationErrors errors, List<Message> messages);

    boolean changeRequestedAmount(Long requestId, boolean increase, int difference, ValidationErrors errors);

    boolean deleteRequest(Long requestId, ValidationErrors errors);

    List<SampleQuestion> getNewSampleRequests(Long biobankId);

  //  List<SampleReservation> getSampleReservations(Long userId);

    boolean createSampleRequest(SampleRequest sampleRequest, Long projectId, Long biobankId, ValidationErrors errors);

    boolean createSampleQuestion(SampleQuestion sampleQuestion, Long biobankId,
                                           ValidationErrors errors);

    boolean assignReservationToProject(Long sampleQuestionId, Long projectId, ValidationErrors errors);


    List<SampleQuestion> getSortedSampleQuestions(Long biobankId, String orderByParam, boolean desc);

    List<SampleRequest> getSortedSampleRequest(Long projectId, String orderByParam, boolean desc);

    List<SampleReservation> getSortedSampleReservation(Long userId, String orderByParam, boolean desc);
}
