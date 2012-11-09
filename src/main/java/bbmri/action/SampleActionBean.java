package bbmri.action;

import bbmri.entities.Project;
import bbmri.entities.Researcher;
import bbmri.entities.Sample;
import bbmri.service.ProjectService;
import bbmri.service.ResearcherService;
import bbmri.service.SampleService;
import bbmri.serviceImpl.ProjectServiceImpl;
import bbmri.serviceImpl.SampleServiceImpl;
import net.sourceforge.stripes.action.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/sample/{$event}/{sample.id}")
public class SampleActionBean implements ActionBean {

    private MyActionBeanContext ctx;
    private ResearcherService researcherService;
    private ProjectService projectService;
    private List<Project> myApprovedProjects;
    private long projectId;
    private Sample sample;
    private SampleService sampleService;
    private List<Sample> samples;

    public void setSample(Sample sample){this.sample = sample;}
    public Sample getSample(){return sample;}
    public void setProjectId(long projectId) {this.projectId = projectId;}
    public long getProjectId() {return projectId;}
    public void setContext(ActionBeanContext ctx) {this.ctx = (MyActionBeanContext) ctx;}
    public MyActionBeanContext getContext() {return ctx;}
    public Researcher getLoggedResearcher() {return ctx.getLoggedResearcher();}

    public ProjectService getProjectService() {
        if (projectService == null) {
            projectService = new ProjectServiceImpl();
        }
        return projectService;
    }

     public SampleService getSampleService() {
        if (sampleService == null) {
            sampleService = new SampleServiceImpl();
        }
        return sampleService;
    }

    public List<Project> getMyApprovedProjects() {
        return getProjectService().getAllApprovedByResearcher(getLoggedResearcher());
    }

    public List<Sample> getSamples(){
        return getSampleService().getAll();
    }

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/sampleRequests.jsp");
    }

    public Resolution selectForEdit() {
        //  researcherService = getProjectService().getAllAssignedResearchers(projectId);
        return new ForwardResolution("/sampleRequests.jsp");
    }
}
