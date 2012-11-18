package bbmri.action;

import bbmri.entities.Researcher;
import bbmri.service.BiobankService;
import bbmri.service.ProjectService;
import bbmri.service.ResearcherService;
import bbmri.serviceImpl.BiobankServiceImpl;
import bbmri.serviceImpl.ProjectServiceImpl;
import bbmri.serviceImpl.ResearcherServiceImpl;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:49
 * To change this template use File | Settings | File Templates.
 */
public class BasicActionBean implements ActionBean {
    private MyActionBeanContext ctx;
    private ResearcherService researcherService;
    private BiobankService biobankService;
     private ProjectService projectService;


     public BiobankService getBiobankService() {
        if (biobankService == null) {
            biobankService = new BiobankServiceImpl();
        }
        return biobankService;
    }

       public ProjectService getProjectService() {
        if (projectService == null) {
            projectService = new ProjectServiceImpl();
        }
        return projectService;
    }

     public ResearcherService getResearcherService() {
        if (researcherService == null) {
            researcherService = new ResearcherServiceImpl();
        }
        return researcherService;
    }

    public void setContext(ActionBeanContext ctx) {this.ctx = (MyActionBeanContext) ctx;}
    public MyActionBeanContext getContext() {return ctx;}

    public Researcher getLoggedResearcher() {
        return ctx.getLoggedResearcher();
    }

}
