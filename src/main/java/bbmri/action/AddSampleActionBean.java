package bbmri.action;

import bbmri.entities.Biobank;
import bbmri.entities.Project;
import bbmri.entities.Researcher;
import bbmri.entities.Sample;
import bbmri.service.ProjectService;
import bbmri.service.ResearcherService;
import bbmri.service.SampleService;
import bbmri.serviceImpl.ProjectServiceImpl;
import bbmri.serviceImpl.SampleServiceImpl;
import net.sourceforge.stripes.action.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 7.11.12
 * Time: 0:34
 * To change this template use File | Settings | File Templates.
 */
public class AddSampleActionBean implements ActionBean{

        private MyActionBeanContext ctx;
        private Sample sample;
        private SampleService sampleService;

        public void setSample(Sample sample){this.sample = sample;}
        public Sample getSample(){return sample;}
        public void setContext(ActionBeanContext ctx) {this.ctx = (MyActionBeanContext) ctx;}
        public MyActionBeanContext getContext() {return ctx;}
        public Researcher getLoggedResearcher() {return ctx.getLoggedResearcher();}
        public SampleService getSampleService() {
        if (sampleService == null) {
            sampleService = new SampleServiceImpl();
        }
        return sampleService;
    }

        @DefaultHandler
        public Resolution zobraz() {
            return new ForwardResolution("/addSample.jsp");
        }

        public Resolution create() {

            Biobank biobank = getLoggedResearcher().getBiobank();
            if(biobank != null){
                getSampleService().create(sample, biobank.getId());
            }
            return new RedirectResolution("/addSample.jsp");
        }


    }


