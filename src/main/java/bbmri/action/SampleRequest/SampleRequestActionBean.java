package bbmri.action.SampleRequest;

import bbmri.action.BasicActionBean;
import bbmri.entities.*;
import bbmri.service.ProjectService;
import bbmri.service.ResearcherService;
import bbmri.service.SampleService;
import bbmri.serviceImpl.ProjectServiceImpl;
import bbmri.serviceImpl.SampleServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

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

    private Project project;

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Sample getSample() {
        return sample;
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

    public SampleService getSampleService() {
        if (sampleService == null) {
            sampleService = new SampleServiceImpl();
        }
        return sampleService;
    }

    private List<Sample> results;

    public List<Sample> getResults() {
        return results;
    }

    @ValidateNestedProperties(value = {
            @Validate(on = {"find"},
                    field = "TNM",
                    maxlength = 7),
            @Validate(on = {"find"},
                    field = "pTNM",
                     maxlength = 7),
            @Validate(on = {"find"},
                    field = "grading",
                    minvalue = 1, maxvalue = 8),
            @Validate(on = {"find"}, field = "tissueType",
                    maxlength = 2),
            @Validate(on = {"find"}, field = "diagnosis",
                    maxlength = 4)
    })
    private Sample sample;
    private SampleService sampleService;

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/sampleRequests.jsp");
    }

    public Resolution request() {
        if (getProject().getProjectState() == ProjectState.NEW) {
            return new ForwardResolution("/allProjects.jsp");
        }
        getRequestService().create(new Request(), getProject().getId(), sample.getId());
        return new ForwardResolution("/allProjects.jsp");
    }

    public Resolution find() {
        if (sample != null) {
            System.out.println(sample);
            results = getSampleService().getSamplesByQuery(sample);
        }
        return new ForwardResolution("/sampleRequests.jsp");
    }

}