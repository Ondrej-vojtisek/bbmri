package bbmri.action;

import bbmri.entities.Biobank;
import bbmri.entities.Sample;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 21.11.12
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/releaseSample/{$event}/{loggedUser.id}")
public class ReleaseSampleActionBean extends BasicActionBean {

    private Sample sample;
    private List<Sample> samples;
    private Biobank biobank;

    public Biobank getBiobank() {
        if (biobank == null) {
            biobank = getLoggedUser().getBiobank();
        }
        return biobank;
    }

    public void setBiobank(Biobank biobank) {
        this.biobank = biobank;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public List<Sample> getSamples() {
        samples = getRequestService().getAllReleasableSamplesByBiobank(getBiobank().getId());
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    public Resolution release() {
        getSampleService().decreaseCount(sample.getId(), 1);

        return new ForwardResolution("/releaseSample.jsp");
    }

}
