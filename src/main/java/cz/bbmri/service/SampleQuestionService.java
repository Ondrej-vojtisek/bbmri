package cz.bbmri.service;

import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;


/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public interface SampleQuestionService extends Get<SampleQuestion> {

    boolean remove(Long id, ValidationErrors errors, Long loggedUserId);

    boolean approve(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean deny(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);
}
