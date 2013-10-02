package bbmri.service;

import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.SampleQuestion;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public interface SampleQuestionService extends BasicService<SampleQuestion>{

    SampleQuestion create(SampleQuestion sampleQuestion, Long biobankId, Long projectId);

    SampleQuestion withdraw(SampleQuestion sampleQuestion, Long biobankId);

}
