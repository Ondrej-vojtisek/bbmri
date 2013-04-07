package bbmri.action;

import bbmri.entities.*;
import bbmri.service.*;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
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
public class SampleQuestionActionBean extends BasicActionBean {

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
        return new ForwardResolution("/sample_request.jsp");
    }

    public Resolution createSampleQuestion(){
        sampleQuestionService.create(sampleQuestion, biobank.getId(), project.getId());
        return new ForwardResolution(bbmri.action.Project.ProjectActionBean.class);
    }

      public Resolution detail() {
          sampleQuestion = sampleQuestionService.getById(sampleQuestion.getId());
          getContext().setSampleQuestion(sampleQuestion);
          return new ForwardResolution("/sample_question_detail.jsp");
      }

    public Resolution remove() {
         sampleQuestion = sampleQuestionService.getById(sampleQuestion.getId());
         if(sampleQuestion != null){
             sampleQuestion.setProcessed(true);
             sampleQuestionService.update(sampleQuestion);
         }
         return new ForwardResolution("/sample_approve_request.jsp");
      }


    public Resolution back() {
        return new ForwardResolution("/sample_approve_request.jsp");
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
        System.err.println("Sample " + sample);

        results = sampleService.getSamplesByQueryAndBiobank(sample, getLoggedUser().getBiobank());



      //  getContext().setSample(sample);
        getContext().setSampleQuestion(sampleQuestion);
        return new ForwardResolution("/sample_question_detail.jsp");
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

            return new ForwardResolution("/sample_approve_request.jsp");
        }

    public List<Project> getMyProjects() {
          return projectService.getAllByUserWithRequests(getLoggedUser().getId());
      }

    public Resolution requestGroupDetail(){
        requestGroup = requestGroupService.getById(requestGroup.getId());
        getContext().setRequestGroup(requestGroup);
        return new ForwardResolution("/requestGroup_detail.jsp");
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
        return new ForwardResolution("/requestGroup_all.jsp");
    }

}
