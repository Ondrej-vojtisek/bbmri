package bbmri.action;

import bbmri.entities.*;
import bbmri.service.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 2.4.13
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
//@UrlBinding("/sampleQuestion/{$event}/{sampleQuestion.id}")
public class SampleQuestionActionBean extends BasicActionBean {

    private static final String REQUEST = "/sample_request.jsp";
    private static final String MY_REQUESTS = "/samples_my_requests.jsp";
    private static final String REQUESTGROUP_DETAIL = "/requestGroup_detail.jsp";
    private static final String REQUESTGROUP_ALL = "/requestGroup_all.jsp";
    private static final String APPROVE_REQUEST = "/sample_approve_request.jsp";
    private static final String QUESTION_DETAIL = "/sample_question_detail.jsp";

    @SpringBean
    private SampleQuestionService sampleQuestionService;

    @SpringBean
    private BiobankService biobankService;

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private SampleService sampleService;

    @SpringBean
    private RequestGroupService requestGroupService;

    @SpringBean
    private RequestService requestService;

    public List<SampleQuestion> getSampleQuestions(){
        return sampleQuestionService.getAllByBiobank(getLoggedUser().getBiobank());
    }

    private Biobank biobank;
    private Project project;
    private SampleQuestion sampleQuestion;
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

    public List<Biobank> getBiobanks(){
        return biobankService.getAll();
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
        if(sampleQuestion == null){
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

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(REQUEST);
    }

    @HandlesEvent("myRequests")
       public Resolution myRequest() {
           return new ForwardResolution(MY_REQUESTS);
       }

    @HandlesEvent("approveSampleRequest")
          public Resolution approveSampleRequest() {
              return new ForwardResolution(APPROVE_REQUEST);
          }

    @HandlesEvent("allRequestGroups")
              public Resolution allRequestGroups() {
                  return new ForwardResolution(REQUESTGROUP_ALL);
              }

    public Resolution createSampleQuestion(){
        sampleQuestionService.create(sampleQuestion, biobank.getId(), project.getId());
        getContext().setSampleQuestion(null);
        return new RedirectResolution(bbmri.action.Project.ProjectActionBean.class);
    }

      public Resolution detail() {
          sampleQuestion = sampleQuestionService.getById(sampleQuestion.getId());
          getContext().setSampleQuestion(sampleQuestion);
          return new ForwardResolution(QUESTION_DETAIL);
      }

    public Resolution remove() {
         sampleQuestion = sampleQuestionService.getById(sampleQuestion.getId());
         if(sampleQuestion != null){
             sampleQuestion.setProcessed(true);
             sampleQuestionService.update(sampleQuestion);
         }
         return new RedirectResolution(APPROVE_REQUEST);
      }


    public Resolution back() {
        return new ForwardResolution(APPROVE_REQUEST);
    }

    public Sample getSample() {
        if(sample == null){
                sample = getContext().getSample();
        }
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public List<Sample> getResults() {
          if(results == null){
              Sample sampleQuery = getSample();
              if(sampleQuery != null){
                  results = sampleService.getSamplesByQuery(sampleQuery);
              }
          }
          return results;
      }

    public Integer getResultCount() {
          if (getResults() == null) {
              return 0;
          }
          return results.size();
      }


    public Resolution find(){
        results = sampleService.getSamplesByQueryAndBiobank(sample, getLoggedUser().getBiobank());
        getContext().setSampleQuestion(sampleQuestion);
        return new ForwardResolution(QUESTION_DETAIL);
    }

    /*TODO: change num of requested to variable value*/
        public Resolution requestSelected() {

            List<Request> requests = new ArrayList<Request>();
            if (selected != null) {
                for (Long sampleId : selected) {
                    Request request = requestService.create(sampleId, 1);
                    requests.add(request);
                }

                requestGroupService.create(requests, sampleQuestion.getProject().getId());
                getContext().getMessages().add(
                               new SimpleMessage("Requests were created")
                       );
            }

            sampleQuestion.setProcessed(true);
            sampleQuestionService.update(sampleQuestion);

            return new RedirectResolution(APPROVE_REQUEST);
        }

    public List<Project> getMyProjects() {
          return projectService.getAllByUserWithRequests(getLoggedUser().getId());
      }

    public Resolution requestGroupDetail(){
        requestGroup = requestGroupService.getById(requestGroup.getId());
        getContext().setRequestGroup(requestGroup);
        return new ForwardResolution(REQUESTGROUP_DETAIL);
    }

    public List<RequestGroup> getAllRequestGroups(){
        return requestGroupService.getByBiobankAndState(getLoggedUser().getBiobank().getId(), RequestState.NEW);

    }

    public Resolution releaseSamples(){
        RequestGroup requestGroupDB = requestGroupService.getById(requestGroup.getId());
        if(requestGroupDB != null){

            List<Request> requests = requestGroupService.getRequestsByRequestGroup(requestGroupDB.getId());
            //TODO
            Integer numOfRequested;
            for(int i = 0; i < requests.size(); i++){

                if(requests.get(i).getNumOfRequested() == null){
                    numOfRequested = 1;
                }else{
                    numOfRequested = requests.get(i).getNumOfRequested();
                }

                sampleService.decreaseCount(requests.get(i).getSample().getId(), numOfRequested);
            }
            requestGroupService.changeRequestState(requestGroupDB.getId(), RequestState.EQUIPPED);
        }
        return new RedirectResolution(REQUESTGROUP_ALL);
    }

}
