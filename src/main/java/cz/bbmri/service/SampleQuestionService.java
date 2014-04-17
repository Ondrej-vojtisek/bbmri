package cz.bbmri.service;

import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.service.simpleService.Get;
import net.sourceforge.stripes.validation.ValidationErrors;


/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface SampleQuestionService extends Get<SampleQuestion> {

    boolean remove(Long id, ValidationErrors errors, Long loggedUserId);

    boolean approve(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);

    boolean deny(Long sampleQuestionId, ValidationErrors errors, Long loggedUserId);
}
