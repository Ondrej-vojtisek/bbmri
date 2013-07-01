package bbmri.action.Sample;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.service.SampleService;
import bbmri.webEntities.IdAndCount;
import bbmri.webEntities.SampleRequestWrapper;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.IntegerTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.apache.commons.lang.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.4.13
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/Sample/{$event}/{sample.id}")
public class SampleActionBean extends BasicActionBean {

    private static final String WITHDRAW = "/sample_withdraw.jsp.jsp";
    private static final String ALL = "/sample_all.jsp";
    private static final String EDIT = "/sample_edit.jsp";


    @SpringBean
    private SampleService sampleService;

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

    private List<Long> selectedSamples;

    public List<Long> getSelectedSamples() {
        return selectedSamples;
    }

    public List<Sample> getSamples(){
        Biobank biobank = getLoggedUser().getBiobank();
        if(biobank != null){
            return sampleService.getAllByBiobank(biobank.getId());
        }
        return null;
    }

    public Integer getSamplesSize(){
        return getSamples().size();
    }

    public void setSelectedSamples(List<Long> selectedSamples) {
        this.selectedSamples = selectedSamples;
    }

    private List<Sample> results;

    public Sample getSample() {
        if(sample == null){
            sample = getContext().getSample();
        }
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Integer getCount() {
        return sampleService.getCount();
    }

    public List<Sample> getResults() {
        if(results == null){
            Sample sampleQuery = getSample();
            if(sampleQuery != null){
                results = sampleService.getSamplesByQueryAndBiobank(sampleQuery, getLoggedUser().getBiobank());
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

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(WITHDRAW);
    }

    @HandlesEvent("allSamples")
    public Resolution allSamples(){
        return new ForwardResolution(ALL);
    }

    public Resolution withdrawSamples() {
        // TODO - variable amount of withdrawed samples
         System.err.println("SAMPLES: " + selectedSamples);

    /*    Integer count = 1;
        sampleService.withdrawSample(sample.getId(), count);
        getContext().setSample(sample);
        */
        return new ForwardResolution(WITHDRAW);
    }

   // private List<Integer> releasedCount;

    private int[] releasedCount;

    public int[] getReleasedCount() {
        return releasedCount;
    }

    public void setReleasedCount(int[] releasedCount) {
        this.releasedCount = releasedCount;
    }

    /*
    public List<Integer> getReleasedCount() {
        return releasedCount;
    }
    public void setReleasedCount(List<Integer> releasedCount) {
        this.releasedCount = releasedCount;
    }   */

    public Resolution withdrawSamples2() {
        System.err.println("SAMPLES: " + selectedSamples);

        //releasedCount = null;
        selectedSamples = null;
        return new ForwardResolution(WITHDRAW);
       }

    public Resolution find() {
        results = sampleService.getSamplesByQueryAndBiobank(sample, getLoggedUser().getBiobank());
        getContext().setSample(sample);
        return new ForwardResolution(WITHDRAW);
    }

    @HandlesEvent("save")
    public Resolution save(){
        sampleService.update(sample);
        return new ForwardResolution(ALL);
    }

    @HandlesEvent("cancel")
    public Resolution cancel(){
        return new ForwardResolution(ALL);
    }

    @HandlesEvent("edit")
    public Resolution edit() {
        sample = sampleService.getById(sample.getId());
        getContext().setSample(sample);
        return new ForwardResolution(EDIT);
    }
}
