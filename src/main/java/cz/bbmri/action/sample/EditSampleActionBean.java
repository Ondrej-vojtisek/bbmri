package cz.bbmri.action.sample;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.Sample;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 25.7.13
 * Time: 14:21
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/EditSample")
@HttpCache(allow = false)
public class EditSampleActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private static final String EDIT = "/webpages/sample/sample_edit.jsp";
    private static final String ALL = "/webpages/sample/sample_all.jsp";

    private Sample sample;

    public Sample getSample() {
//        if(sample == null){
//            sample = getContext().getSample();
//        }
        return sample;
    }

    public void setSample(Sample sample) {
          this.sample = sample;
      }

    @DontValidate
      @DefaultHandler
      public Resolution display() {
          logger.debug("DISPLAY");
          return new ForwardResolution(EDIT);
      }


    @DontValidate
    @HandlesEvent("save")
    public Resolution save(){
        sampleService.update(sample);
        return new ForwardResolution(ALL);
    }

    @DontValidate
        @HandlesEvent("cancel")
        public Resolution cancel(){
            return new ForwardResolution(ALL);
        }

}
