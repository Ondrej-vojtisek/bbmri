package bbmri.action;

import bbmri.entities.Biobank;
import bbmri.entities.Researcher;
import bbmri.service.BiobankService;
import bbmri.service.ResearcherService;
import bbmri.serviceImpl.BiobankServiceImpl;
import bbmri.serviceImpl.ResearcherServiceImpl;
import net.sourceforge.stripes.action.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/biobank/{$event}/{biobank.id}")
public class BiobankActionBean implements ActionBean {
     private MyActionBeanContext ctx;
     private BiobankService biobankService;
     private Biobank biobank;
     private List<Biobank> biobanks;

    public List<Biobank> getBiobanks() {
          return getBiobankService().getAll();
      }

     public void setContext(ActionBeanContext ctx) {this.ctx = (MyActionBeanContext) ctx;}
     public MyActionBeanContext getContext() {return ctx;}

      public BiobankService getBiobankService(){
        if(biobankService == null){
            biobankService = new BiobankServiceImpl();
        }
        return biobankService;
    }

    public Biobank getBiobank() {return biobank;}
    public void setBiobank(Biobank biobank){this.biobank = biobank;}
    public Researcher getLoggedResearcher() {return ctx.getLoggedResearcher();}

     @DefaultHandler
    public Resolution zobraz() {
         biobanks = getBiobankService().getAll();
         return new ForwardResolution("/biobanks.jsp");
     }

     public Resolution create() {
        if(getLoggedResearcher().getBiobank() != null){
            // we cannot be an admin of a new biobank
            return new RedirectResolution(this.getClass(), "zobraz");
        }

        getBiobankService().create(biobank, ctx.getLoggedResearcher());
        ctx.getLoggedResearcher().setBiobank(biobank);
        return new RedirectResolution(this.getClass(), "zobraz");
    }
}
