package bbmri.action.SampleRequest;

import bbmri.action.BasicActionBean;
import bbmri.entities.*;
import bbmri.service.ProjectService;
import bbmri.service.ResearcherService;
import bbmri.service.SampleService;
import bbmri.serviceImpl.ProjectServiceImpl;
import bbmri.serviceImpl.SampleServiceImpl;
import net.sourceforge.stripes.action.*;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/sampleRequests/{$event}/{sample.id}")
public class SampleRequestActionBean extends BasicActionBean {
    private Sample sample;

    private List<Sample> samples;
    private Project project;

    public void setSample(Sample sample){this.sample = sample;}
    public Sample getSample(){return sample;}

    public void setProject (Project project){this.project = project;}
    public Project getProject(){
        if(project == null){
            project = getContext().getProject();
        }
        return project;
    }


    public List<Sample> getSamples(){
        return getSampleService().getAll();
    }

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/sampleRequests.jsp");
    }

      public Resolution request() {
         if(getProject().getProjectState() == ProjectState.NEW){
            return new ForwardResolution("/allProjects.jsp");
         }
          getRequestService().create(new Request(), getProject().getId(), sample.getId());
          return new ForwardResolution("/allProjects.jsp");
      }
}
