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
@UrlBinding("/Sample/{$event}/{project.id}")
public class SampleActionBean extends BasicActionBean {

    @SpringBean
    private SampleService sampleService;

    @ValidateNestedProperties(value = {
            @Validate(on = {"create"},
                    field = "sampleID", required = true,
                    minlength = 13, maxlength = 13),
            @Validate(on = {"create"},
                    field = "TNM", required = true,
                    minlength = 7, maxlength = 7),
            @Validate(on = {"create"},
                    field = "pTNM", required = true,
                    minlength = 7, maxlength = 7),
            @Validate(on = {"create"},
                    field = "grading", required = true,
                    minvalue = 1, maxvalue = 8),
            @Validate(on = {"create"}, field = "tissueType", required = true,
                    minlength = 2, maxlength = 2),
            @Validate(on = {"create"}, field = "numOfSamples", required = true,
                    minvalue = 1),
            @Validate(on = {"create"}, field = "numOfAvailable", required = true,
                    minvalue = 1),
            @Validate(on = {"create"}, field = "diagnosis", required = true,
                    minlength = 4, maxlength = 4),
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

    @Validate(converter = IntegerTypeConverter.class, on = {"generateRandomSample"},
            required = true, minvalue = 1, maxvalue = 100)
    private Integer numOfRandom;

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

    public Integer getNumOfRandom() {
        return numOfRandom;
    }

    public void setNumOfRandom(Integer numOfRandom) {
        this.numOfRandom = numOfRandom;
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

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution("/sample_create.jsp");
    }

    public Resolution create() {
        Biobank biobank = getLoggedUser().getBiobank();
        if (biobank != null) {
            sampleService.create(sample, biobank.getId());
            getContext().getMessages().add(
                    new SimpleMessage("Added 1 sample")
            );
        }
        return new ForwardResolution(this.getClass(), "display");
    }

    public Resolution generateRandomSample() {
        Biobank biobank = getLoggedUser().getBiobank();
        Integer added = 0;
        if (biobank != null) {
            for (int i = 0; i < numOfRandom; i++) {
                generateSample();
                sampleService.create(sample, biobank.getId());
                added = i + 1;
            }
        }
        getContext().getMessages().add(
                new SimpleMessage("Added {0} sample(s)", added)
        );
        return new ForwardResolution(this.getClass(), "display");
    }

    public void generateSample() {
        RandomStringUtils randomStringUtils = new RandomStringUtils();
        Random generator = new Random();
        sample = new Sample();
        sample.setDiagnosis(randomStringUtils.random(4, true, true));
        sample.setGrading(generator.nextInt(8) + 1);
        sample.setNumOfAvailable(generator.nextInt(20) + 1);
        sample.setNumOfSamples(sample.getNumOfAvailable() + generator.nextInt(10));
        sample.setSampleID(randomStringUtils.random(13, true, true));
        sample.setTNM(randomStringUtils.random(7, true, true));
        sample.setpTNM(randomStringUtils.random(7, true, true));
        sample.setTissueType(randomStringUtils.random(2, true, true));
    }

    public Resolution amortizeSamples() {
        // TODO - variable amount of amortized samples
        System.err.println("Number of amortized: " + amortizeNumber);
        System.err.println("Sample: " + sample);
        Integer count = 1;
        sampleService.amortizeSample(sample.getId(), count);
        getContext().setSample(sample);
        amortizeNumber = null;
        return new ForwardResolution("/sample_amortize.jsp");
    }

    public Resolution find() {
        results = sampleService.getSamplesByQuery(sample);
        getContext().setSample(sample);
        return new ForwardResolution("/sample_amortize.jsp");
    }

    private Integer amortizeNumber;

    public Integer getAmortizeNumber() {
        return amortizeNumber;
    }

    public void setAmortizeNumber(Integer amortizeNumber) {
        this.amortizeNumber = amortizeNumber;
    }
}
