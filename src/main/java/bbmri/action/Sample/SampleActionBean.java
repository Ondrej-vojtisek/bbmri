package bbmri.action.sample;

import bbmri.action.BasicActionBean;
import bbmri.entities.*;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 6.4.13
 * Time: 19:50
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/Sample")
public class SampleActionBean extends BasicActionBean {

    private static final String WITHDRAW = "/webpages/sample/sample_withdraw.jsp";
    private static final String ALL = "/webpages/sample/sample_all.jsp";
    private static final String REQUESTGROUP_DETAIL = "/requestGroup_detail.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

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
       /* BiobankAdministrator ba = getLoggedUser().getBiobankAdministrator();
        Biobank biobank = biobankService.get(ba.getBiobank().getId());
        if(biobank != null){
            return sampleService.getAllByBiobank(biobank.getId());
        }
        */
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
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public Integer getCount() {
        return sampleService.count();
    }

    public List<Sample> getResults() {
        /*
        TODO
        BiobankAdministrator ba = getLoggedUser().getBiobankAdministrator();
        Biobank biobank = biobankService.get(ba.getBiobank().getId());

        if(sample == null){
           results = sampleService.getAllByBiobank(biobank.getId());
        }else{
            sample.setBiobank(biobank);
            results = sampleService.getSamplesByQuery(sample);
        }

        return results;
        */
        return null;
    }

    public Integer getResultCount() {
        if (getResults() == null) {
            return 0;
        }
        return results.size();
    }

    @DontValidate
    @DefaultHandler
    public Resolution display() {
        logger.debug("DISPLAY");
        return new ForwardResolution(WITHDRAW);
    }

    @DontValidate
    @HandlesEvent("allSamples")
    public Resolution allSamples(){
        return new ForwardResolution(ALL);
    }

    @DontValidate
    public Resolution withdrawSamples() {
        getContext().setSample(null);
        return new RedirectResolution(WITHDRAW);
    }

    private int[] releasedCount;

    public int[] getReleasedCount() {
        return releasedCount;
    }

    public void setReleasedCount(int[] releasedCount) {
        this.releasedCount = releasedCount;
    }

    public Resolution find() {
        return new ForwardResolution(WITHDRAW);
    }

    @DontValidate
    @HandlesEvent("edit")
    public Resolution edit() {
        sample = sampleService.get(sample.getId());
        getContext().setSample(sample);
        return new ForwardResolution(EditSampleActionBean.class);
    }

    @DontValidate
        public Resolution requestSelected() {

            logger.debug("selected: " + selectedSamples);

            return new ForwardResolution(WITHDRAW);

        }
}
