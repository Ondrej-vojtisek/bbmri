package bbmri.action;

import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Project;
import bbmri.entities.Researcher;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.HandlesEvent;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 24.10.12
 * Time: 0:45
 * To change this template use File | Settings | File Templates.
 */
public class MyActionBeanContext extends ActionBeanContext {
    public void setLoggedResearcher(Researcher loggedResearcher) {
        getRequest().getSession().setAttribute("loggedResearcher", loggedResearcher);
    }

    public Researcher getLoggedResearcher() {
        return (Researcher) getRequest().getSession().getAttribute("loggedResearcher");
    }

    public ResearcherDAOImpl getResearcherDAOImpl() {
        return (ResearcherDAOImpl) getRequest().getSession().getAttribute("researcherDAOImpl");
    }

    public void setResearcherDAOImpl(ResearcherDAOImpl researcherDAOImpl) {
        getRequest().getSession().setAttribute("researcherDAOImpl", researcherDAOImpl);
    }

     public Project getProject() {
        return (Project) getRequest().getSession().getAttribute("project");
    }

    public void setProject(Project project) {
        getRequest().getSession().setAttribute("project", project);
    }

}
