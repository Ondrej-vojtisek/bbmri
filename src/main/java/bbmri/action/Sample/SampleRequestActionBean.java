package bbmri.action.Sample;

import bbmri.action.BasicActionBean;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.Request;
import bbmri.entities.Sample;
import bbmri.service.RequestGroupService;
import bbmri.service.RequestService;
import bbmri.service.SampleService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.util.ArrayList;
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

    @SpringBean
    private RequestService requestService;

    @SpringBean
    private SampleService sampleService;

    @SpringBean
    private RequestGroupService requestGroupService;

    private List<Long> selected;

    private Sample sampleQuery;

    public Sample getSampleQuery() {
        return sampleQuery;
    }

    public void setSampleQuery(Sample sampleQuery) {
        this.sampleQuery = sampleQuery;
    }

    public void setSelected(List<Long> selected) {
        this.selected = selected;
    }

    public List<Long> getSelected() {
        return selected;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Sample getSample() {
        return sample;
    }

    public Integer getCount() {
        return sampleService.getCount();
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

    public List<Sample> getAllSamples() {
            return sampleService.getAll();
        }

    public List<Sample> getAllSamplesByBiobank() {
        if (getLoggedUser().getBiobank() == null) {
            return null;
        }
        return sampleService.getAllByBiobank(getLoggedUser().getBiobank().getId());
    }

    private List<Sample> results;

    public List<Sample> getResults() {
        return results;
    }

    public Integer getResultCount() {
        if (results == null) {
            return 0;
        }
        return results.size();
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

    @DefaultHandler
    public Resolution display() {
        if(sampleQuery == null){
            sampleQuery = getContext().getSample();
        }
        if(sampleQuery != null){
            results = sampleService.getSamplesByQuery(sampleQuery);
        }
        return new ForwardResolution("/sample_withdraw.jsp");
    }

    public Resolution request() {
        if (getProject().getProjectState() == ProjectState.NEW) {
            return new ForwardResolution("/project_my_projects.jsp");
        }
        requestService.create(sample.getId());
        getContext().getMessages().add(
                       new SimpleMessage("Request for sample id = {0} was created", sample.getId())
               );
        return new ForwardResolution("/project_my_projects.jsp");
    }

    public Resolution find() {
        results = sampleService.getSamplesByQuery(sampleQuery);
        return new ForwardResolution("/sample_withdraw.jsp");
    }

    public Resolution createParametrizedRequest() {
        if (sample != null) {
            results = sampleService.getSamplesByQuery(sample);
        }
        return new ForwardResolution("/sample_withdraw.jsp");
    }

    /*TODO: change num of requested to variable value*/
    public Resolution requestSelected() {
        List<Request> requests = new ArrayList<Request>();
        if (selected != null) {
            for (Long sampleId : selected) {
                Request request = requestService.create(sampleId);
                requests.add(request);
            }

            requestGroupService.create(requests, getProject().getId());
            getContext().getMessages().add(
                           new SimpleMessage("Requests were created")
                   );
        }

        return new ForwardResolution("/project_my_projects.jsp");
    }
}
