package cz.bbmri.action.sample;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Sample;
import cz.bbmri.entities.sample.Tissue;
import cz.bbmri.facade.SampleFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.2.14
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */

@HttpCache(allow = false)
@UrlBinding("/createSample/{$event}/{sampleId}")
public class CreateSampleActionBean extends PermissionActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private SampleFacade sampleFacade;

    private Long sampleId;

    private Sample sample;

    public Sample getSample() {
        if (sample == null) {
            if (sampleId != null) {
                //  sample = sampleFacade.get(sampleId);
            }
        }
        return sample;
    }

    private Tissue tissue;

    public Tissue getTissue() {
        return tissue;
    }

    public void setTissue(Tissue tissue) {
        this.tissue = tissue;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    /* Methods */
    @DontValidate
    @DefaultHandler
    @HandlesEvent("create") /* Necessary for stripes security tag*/
    @RolesAllowed({"administrator", "developer"})
    public Resolution create() {
        return new ForwardResolution("/webpages/sample/create.jsp");
    }

    @RolesAllowed({"administrator", "developer"})
    public Resolution createTissue() {

        logger.debug("TISSUE: " + sample);
        sampleFacade.create(sample);
        return new ForwardResolution(this.getClass(), "create");

//        if (!sampleFacade.create(tissue)) {
//            return new ForwardResolution(this.getClass());
//        }
//        successMsg(null);
//        return new RedirectResolution(this.getClass());
    }
}
