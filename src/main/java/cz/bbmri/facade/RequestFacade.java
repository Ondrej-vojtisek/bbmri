package cz.bbmri.facade;

import cz.bbmri.entities.SampleQuestion;
import net.sourceforge.stripes.validation.ValidationErrors;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 30.9.13
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 */
public interface RequestFacade {

    SampleQuestion getSampleQuestion(Long sampleQuestionId);

    boolean approveSampleQuestion(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean denySampleQuestion(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    //List<RequestGroup> getRequestsByBiobankAndState(Long biobank);
}
