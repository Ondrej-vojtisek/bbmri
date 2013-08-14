package bbmri.action;

import bbmri.entities.*;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/sampleQuestion")
public class SampleQuestionActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String REQUEST = "/sample_request.jsp";
    private static final String MY_REQUESTS = "/samples_my_requests.jsp";
    private static final String REQUESTGROUP_DETAIL = "/requestGroup_detail.jsp";
    private static final String REQUESTGROUP_ALL = "/requestGroup_all.jsp";
    private static final String APPROVE_REQUEST = "/sample_approve_request.jsp";
    private static final String QUESTION_DETAIL = "/sample_question_detail.jsp";


    public List<SampleQuestion> getSampleQuestions() {
        return sampleQuestionService.getAllByBiobank(getLoggedUser().getBiobank());
    }

    private Biobank biobank;
    private Project project;

    private SampleQuestion sampleQuestion;

 /*
    @ValidateNestedProperties(value = {
            @Validate(field = "TNM", maxlength = 7),
            @Validate(field = "pTNM", maxlength = 7),
            @Validate(field = "grading", minvalue = 1, maxvalue = 8),
            @Validate(field = "tissueType", maxlength = 2),
            @Validate(field = "diagnosis", maxlength = 4)
    })  */
    private Sample sample;
    private List<Sample> results;
    private List<Long> selected;
    private RequestGroup requestGroup;

    public RequestGroup getRequestGroup() {
        return requestGroup;
    }

    public void setRequestGroup(RequestGroup requestGroup) {
        this.requestGroup = requestGroup;
    }

    public List<Biobank> getBiobanks() {
        return biobankService.all();
    }

    public Biobank getBiobank() {
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        if (project == null) {
            project = getContext().getProject();
        }
        return project;
    }

    public SampleQuestion getSampleQuestion() {
        if (sampleQuestion == null) {
            sampleQuestion = getContext().getSampleQuestion();
        }
        return sampleQuestion;
    }

    public void setSampleQuestion(SampleQuestion sampleQuestion) {
        this.sampleQuestion = sampleQuestion;
    }

    public List<Long> getSelected() {
        return selected;
    }

    public void setSelected(List<Long> selected) {
        this.selected = selected;
    }

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        getContext().setSampleQuestion(sampleQuestion);

        return new ForwardResolution(REQUEST);
    }

    @DontValidate
    @HandlesEvent("myRequests")
    public Resolution myRequest() {
        return new ForwardResolution(MY_REQUESTS);
    }

    @DontValidate
    @HandlesEvent("approveSampleRequest")
    public Resolution approveSampleRequest() {
        return new ForwardResolution(APPROVE_REQUEST);
    }

    @DontValidate
    @HandlesEvent("allRequestGroups")
    public Resolution allRequestGroups() {
        return new ForwardResolution(REQUESTGROUP_ALL);
    }

    public Resolution createSampleQuestion() {
        sampleQuestionService.create(sampleQuestion, biobank.getId(), project.getId());
        getContext().setSampleQuestion(null);
        return new RedirectResolution(bbmri.action.project.ProjectActionBean.class);
    }

    @DontValidate
    public Resolution detail() {
        sampleQuestion = sampleQuestionService.get(sampleQuestion.getId());
        getContext().setSampleQuestion(sampleQuestion);
        return new ForwardResolution(QUESTION_DETAIL);
    }

    @DontValidate
    public Resolution remove() {
        sampleQuestion = sampleQuestionService.get(sampleQuestion.getId());
        if (sampleQuestion != null) {
            sampleQuestion.setProcessed(true);
            sampleQuestionService.update(sampleQuestion);
        }
        return new RedirectResolution(APPROVE_REQUEST);
    }

    @DontValidate
    public Resolution back() {
        return new ForwardResolution(APPROVE_REQUEST);
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public List<Sample> getResults() {
        if (sample == null) {
            results = sampleService.getAllByBiobank(getLoggedUser().getBiobank().getId());
        } else {
            results = sampleService.getSamplesByQueryAndBiobank(sample, getLoggedUser().getBiobank());
        }
        return results;
    }

    public Integer getResultCount() {
        if (getResults() == null) {
            return 0;
        }
        return results.size();
    }

    public Resolution find() {
        logger.debug("Find sample- " + sample);


        if (sample != null) {
            results = sampleService.getSamplesByQueryAndBiobank(sample, getLoggedUser().getBiobank());
            getContext().setSampleQuestion(sampleQuestion);
            getContext().setSample(sample);
        }
        return new ForwardResolution(QUESTION_DETAIL);
    }

    @DontValidate
    public Resolution requestSelected() {
        getSampleQuestion();
        logger.debug("RequestSelected Selected> " + selected);
        List<Request> requests = new ArrayList<Request>();
        if (selected != null) {
            for (Long sampleId : selected) {
                Request request = requestService.create(sampleId, 1);
                requests.add(request);
            }
            RequestGroup requestGroup = requestGroupService.create(requests, sampleQuestion.getProject().getId());
            getContext().getMessages().add(
                    new SimpleMessage("Requests were created")
            );
            sampleQuestion.setRequestState(RequestState.APPROVED);
            sampleQuestion.setProcessed(true);
            sampleQuestionService.update(sampleQuestion);
            getContext().setRequestGroupId(requestGroup.getId());
            return new ForwardResolution(REQUESTGROUP_DETAIL);
        }
        return new ForwardResolution(APPROVE_REQUEST);

    }

    public List<Project> getMyProjects() {
        return projectService.getAllByUserWithRequests(getContext().getIdentifier());
    }

    @DontValidate
    public Resolution requestGroupDetail() {
        requestGroup = requestGroupService.get(requestGroup.getId());
        getContext().setRequestGroupId(requestGroup);
        return new ForwardResolution(REQUESTGROUP_DETAIL);
    }

    @DontValidate
    public List<RequestGroup> getAllRequestGroups() {
        return requestGroupService.getByBiobank(getLoggedUser().getBiobank().getId());

    }

    @DontValidate
    public Resolution reject() {

        getSampleQuestion();

        sampleQuestion.setProcessed(true);
        sampleQuestion.setRequestState(RequestState.DENIED);
        sampleQuestionService.update(sampleQuestion);
        getContext().getMessages().add(
                new SimpleMessage("Query for samples was rejected")
        );
        RequestGroup requestGroup = requestGroupService.create(null, sampleQuestion.getProject().getId());
        requestGroup.setRequestState(RequestState.DENIED);
        requestGroup.setBiobank(getLoggedUser().getBiobank());
        requestGroup.setProject(sampleQuestion.getProject());
        requestGroupService.update(requestGroup);

        return new RedirectResolution(APPROVE_REQUEST);

    }

}
