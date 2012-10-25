package bbmri.model;

import bbmri.entities.Researcher;
import bbmri.entities.ResearcherDAOImpl;
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

    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    private ResearcherDAOImpl researcherDAO;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");

    private Researcher researcher;
    private Researcher loggedResearcher;

     public Researcher getLoggedResearcher() {
        return ctx.getLoggedResearcher();
    }

    public void setLoggedResearcher(Researcher loggedResearcher) {
        this.loggedResearcher = loggedResearcher;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

       @DefaultHandler
    public Resolution zobraz() {
        if (researcherDAO == null) {
            researcherDAO = new ResearcherDAOImpl();
        }
         return new ForwardResolution("/myAccount.jsp");
     }

     public Resolution update() {
        if (researcherDAO == null) {
            researcherDAO = new ResearcherDAOImpl();
        }
        Researcher res = researcherDAO.getResearcher(ctx.getLoggedResearcher().getId());
        if(researcher.getName() != null){
            res.setName(researcher.getName());
        }
        if(researcher.getSurname() != null){
            res.setSurname(researcher.getSurname());
        }
        researcherDAO.updateResearcher(res);
        return new RedirectResolution(this.getClass(), "zobraz");
    }
}
