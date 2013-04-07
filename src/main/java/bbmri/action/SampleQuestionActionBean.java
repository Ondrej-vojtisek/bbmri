package bbmri.action;

import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.SampleQuestion;
import bbmri.service.BiobankService;
import bbmri.service.ProjectService;
import bbmri.service.SampleQuestionService;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.integration.spring.SpringBean;

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

    public List<SampleQuestion> getSampleQuestions(){
        return sampleQuestionService.getAllByBiobank(getLoggedUser().getBiobank());
    }

    private Biobank biobank;
    private Project project;
    private SampleQuestion sampleQuestion;

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

}
