package cz.bbmri.facade;

import cz.bbmri.entities.SampleQuestion;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public interface RequestFacade {

    SampleQuestion getSampleQuestion(Long sampleQuestionId);

    //List<RequestGroup> getRequestsByBiobankAndState(Long biobank);
}
