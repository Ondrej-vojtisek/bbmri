package bbmri.action;

import bbmri.entities.Researcher;
import bbmri.service.ProjectService;
import bbmri.service.ResearcherService;
import bbmri.serviceImpl.ProjectServiceImpl;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.UrlBinding;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 5.11.12
 * Time: 23:46
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/sample/{$event}/{sample.id}")
public class SampleActionBean implements ActionBean {

    private MyActionBeanContext ctx;

    private ResearcherService researcherService;

    public void setContext(ActionBeanContext ctx) {this.ctx = (MyActionBeanContext) ctx;}
    public MyActionBeanContext getContext() {return ctx;}
    public Researcher getLoggedResearcher() {return ctx.getLoggedResearcher();}

    private ProjectService projectService;

     public ProjectService getProjectService(){
        if(projectService == null){
            projectService = new ProjectServiceImpl();
        }
        return projectService;
    }

}
