package cz.bbmri.facade.impl;

import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.facade.RequestFacade;
import cz.bbmri.service.SampleQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.1.14
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
@Controller("requestFacade")
public class RequestFacadeImpl extends BasicFacade implements RequestFacade {

    @Autowired
    private SampleQuestionService sampleQuestionService;

    public SampleQuestion getSampleQuestion(Long sampleQuestionId){
        return sampleQuestionService.get(sampleQuestionId);
    }
}
