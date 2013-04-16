package bbmri.action.Sample;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import bbmri.service.SampleService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.IntegerTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.apache.commons.lang.RandomStringUtils;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.4.13
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
public class SampleActionBean extends BasicActionBean {

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
        return new ForwardResolution("/sample_withdraw.jsp");
    }

    public Resolution withdrawSamples() {
        // TODO - variable amount of withdrawed samples
        Integer count = 1;
        sampleService.withdrawSample(sample.getId(), count);
        getContext().setSample(sample);
        return new ForwardResolution("/sample_withdraw.jsp");
    }

    public Resolution find() {
        results = sampleService.getSamplesByQuery(sample);
        getContext().setSample(sample);
        return new ForwardResolution("/sample_withdraw.jsp");
    }
}
