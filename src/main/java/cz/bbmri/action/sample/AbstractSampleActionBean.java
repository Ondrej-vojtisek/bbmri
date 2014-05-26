package cz.bbmri.action.sample;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.sample.Sample;
import cz.bbmri.service.SampleService;
import net.sourceforge.stripes.integration.spring.SpringBean;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

abstract class AbstractSampleActionBean<T> extends PermissionActionBean<T> {

    @SpringBean
    private SampleService sampleService;

    private Sample sample;

    public Sample getSample() {
        if (sample == null) {
            if (sampleId != null) {
                sample = sampleService.get(sampleId);
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
