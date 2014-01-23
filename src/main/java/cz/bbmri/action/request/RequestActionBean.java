package cz.bbmri.action.request;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.SampleQuestion;
import cz.bbmri.facade.RequestFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 21.1.14
 * Time: 13:24
 * To change this template use File | Settings | File Templates.
 */

@HttpCache(allow = false)
@UrlBinding("/request/{$event}/{sampleQuestionId}")
public class RequestActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private RequestFacade requestFacade;

    /* SampleQuestion identifier */
    private Long sampleQuestionId;

    private SampleQuestion sampleQuestion;

    public Long getSampleQuestionId() {
        return sampleQuestionId;
    }

    public void setSampleQuestionId(Long sampleQuestionId) {
        this.sampleQuestionId = sampleQuestionId;
    }

    public SampleQuestion getSampleQuestion() {
        logger.debug("getSampleQuestion entry");

        if (sampleQuestion == null) {

            logger.debug("getSampleQuestion null");

            if (sampleQuestionId != null) {

                logger.debug("getSampleQuestion sampleQuestion null");

                sampleQuestion = requestFacade.getSampleQuestion(sampleQuestionId);
            }
        }
        return sampleQuestion;
    }


    @DontValidate
    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer", "project_team_member"})
    public Resolution detail(){
        return new ForwardResolution(REQUEST_DETAIL);
    }
}
