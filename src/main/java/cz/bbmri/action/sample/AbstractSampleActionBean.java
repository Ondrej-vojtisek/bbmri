package cz.bbmri.action.sample;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Sample;
import cz.bbmri.facade.SampleFacade;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 13.3.14
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractSampleActionBean<T> extends PermissionActionBean<T> {

    @SpringBean
    protected SampleFacade sampleFacade;

    private Sample sample;

    public Sample getSample() {
        if (sample == null) {
            if (sampleId != null) {
                sample = sampleFacade.get(sampleId);
            }
        }
        return sample;
    }

    private Long sampleId;

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }
}
