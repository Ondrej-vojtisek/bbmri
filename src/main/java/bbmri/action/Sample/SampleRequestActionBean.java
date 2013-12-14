package bbmri.action.sample;

import bbmri.action.base.BasicActionBean;
import bbmri.entities.*;
import bbmri.entities.enumeration.ProjectState;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/sampleRequests")
public class SampleRequestActionBean extends BasicActionBean {

    private static final String WITHDRAW = "/webpages/sample/sample_withdraw.jsp";
    private static final String MY_PROJECTS = " /webpages/project/project_my_projects.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private Project project;

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
        return sampleService.count();
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
            return sampleService.all();
        }

    public List<Sample> getAllSamplesByBiobank() {
        //TODO
      /*  BiobankAdministrator ba = getLoggedUser().getBiobankAdministrator();
        Biobank biobank = biobankService.get(ba.getBiobank().getId());

        if (biobank == null) {
            return null;
        }
        return sampleService.getAllByBiobank(biobank.getId());  */
        return null;
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

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        if(sampleQuery == null){
            sampleQuery = getContext().getSample();
        }
        if(sampleQuery != null){
            results = sampleService.getSamplesByQuery(sampleQuery);
        }


        return new ForwardResolution(WITHDRAW);
    }
    @DontValidate
    public Resolution request() {
        if (getProject().getProjectState() == ProjectState.NEW) {
            return new ForwardResolution(MY_PROJECTS);
        }
        requestService.create(sample.getId());
        getContext().getMessages().add(
                       new SimpleMessage("Request for sample id = {0} was created", sample.getId())
               );
        return new RedirectResolution(MY_PROJECTS);
    }

    public Resolution find() {
        results = sampleService.getSamplesByQuery(sampleQuery);
        return new ForwardResolution(WITHDRAW);
    }
    @DontValidate
    public Resolution createParametrizedRequest() {
        if (sample != null) {
            results = sampleService.getSamplesByQuery(sample);
        }
        return new ForwardResolution(WITHDRAW);
    }
    @DontValidate
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

        return new RedirectResolution(MY_PROJECTS);
    }
}
