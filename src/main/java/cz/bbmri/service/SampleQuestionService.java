package cz.bbmri.service;

import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.entities.SampleRequest;
import cz.bbmri.entities.SampleReservation;
import cz.bbmri.entities.enumeration.RequestState;

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

}
