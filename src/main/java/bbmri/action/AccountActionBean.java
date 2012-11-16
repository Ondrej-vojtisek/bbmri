package bbmri.action;

import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Researcher;
import bbmri.service.ResearcherService;
import bbmri.serviceImpl.ResearcherServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.controller.LifecycleStage;

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
    private String password;
    private String password2;
    private Researcher researcher;
    private Researcher loggedResearcher;

      public String getPassword() {
          return password;
      }

      public void setPassword(String password) {
          this.password = password;
      }

      public String getPassword2() {
          return password2;
      }

     public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    public Researcher getLoggedResearcher() {
        loggedResearcher = ctx.getLoggedResearcher();
        return loggedResearcher;
    }

    public ResearcherService getResearcherService() {
        if (researcherService == null) {
            researcherService = new ResearcherServiceImpl();
        }
        return researcherService;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    @DefaultHandler
    public Resolution zobraz() {

        return new ForwardResolution("/myAccount.jsp");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"update", "changePassword"})
    public void fillInputs() {
        researcher = getLoggedResearcher();
    }

    public Resolution update() {
        if(researcher.getName() != null)
            loggedResearcher.setName(researcher.getName());

        if(researcher.getSurname() != null)
            loggedResearcher.setSurname(researcher.getSurname());

        getResearcherService().update(loggedResearcher);
        ctx.setLoggedResearcher(loggedResearcher);
        return new RedirectResolution(this.getClass(), "zobraz");
    }

    public Resolution changePassword(){
        if(password != null && password2 != null){
            if(password.equals(password2))
                getLoggedResearcher().setPassword(password);
                getResearcherService().update(getLoggedResearcher());
        }
        researcher = getLoggedResearcher();
        return new RedirectResolution(this.getClass(), "zobraz");
    }

}
