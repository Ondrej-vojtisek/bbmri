package bbmri.action;

import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Researcher;
import bbmri.service.ResearcherService;
import bbmri.serviceImpl.ResearcherServiceImpl;
import net.sourceforge.stripes.action.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 14:12
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/account/{$event}/{researcher.id}")
public class AccountActionBean implements ActionBean {

    private MyActionBeanContext ctx;

    private ResearcherService researcherService;

    public void setContext(ActionBeanContext ctx) {this.ctx = (MyActionBeanContext) ctx;}
    public MyActionBeanContext getContext() {return ctx;}

    private Researcher researcher;
    private Researcher loggedResearcher;

    public Researcher getLoggedResearcher() {return ctx.getLoggedResearcher();}

     public ResearcherService getResearcherService(){
        if(researcherService == null){
            researcherService = new ResearcherServiceImpl();
        }
        return researcherService;
    }

    public Researcher getResearcher() {return researcher;}
    public void setResearcher(Researcher researcher) {this.researcher = researcher;}

       @DefaultHandler
    public Resolution zobraz() {

         return new ForwardResolution("/myAccount.jsp");
     }

     public Resolution update() {
        researcher.setId(getLoggedResearcher().getId());
        ctx.setLoggedResearcher(getResearcherService().update(researcher));
        return new RedirectResolution(this.getClass(), "zobraz");
    }
}
