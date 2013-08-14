package bbmri.action.sample;

import bbmri.action.BasicActionBean;
import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.IntegerTypeConverter;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 16.4.13
 * Time: 23:06
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/SampleCreate")
public class CreateSampleActionBean extends BasicActionBean {

    private static final String CREATE = "/sample_create.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

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
     })
    private Sample sample;

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    @Validate(converter = IntegerTypeConverter.class, on = {"generateRandomSample"},
            required = true, minvalue = 1, maxvalue = 100)
    private Integer numOfRandom;

    public Integer getNumOfRandom() {
        return numOfRandom;
    }

    public void setNumOfRandom(Integer numOfRandom) {
        this.numOfRandom = numOfRandom;
    }

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(CREATE);
    }

    public Resolution create() {
        Biobank biobank = getLoggedUser().getBiobank();

        logger.debug("Sample: " + sample);


        if (biobank != null) {
            sampleService.create(sample, biobank.getId());
            getContext().getMessages().add(
                    new SimpleMessage("Added 1 sample")
            );
        }
        return new ForwardResolution(this.getClass(), "display");
    }

    @DontValidate
    public Resolution generateRandomSample() {
        Biobank biobank = getLoggedUser().getBiobank();
        Integer added = 0;
        if (biobank != null) {
            for (int i = 0; i < numOfRandom; i++) {
                generateSample(i);
                sampleService.create(sample, biobank.getId());
                added = i + 1;
            }
        }
        getContext().getMessages().add(
                new SimpleMessage("Added {0} sample(s)", added)
        );
        return new ForwardResolution(this.getClass(), "display");
    }

    @DontValidate
    public void generateSample(Integer i) {
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
        sample.setBiopticalReportYear("2013");
        sample.setBiopticalReportNumber(i.toString());
    }
}
