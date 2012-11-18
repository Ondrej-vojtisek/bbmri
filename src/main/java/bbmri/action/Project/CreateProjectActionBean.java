package bbmri.action.Project;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:43
 * To change this template use File | Settings | File Templates.
 */

import bbmri.action.BasicActionBean;
import bbmri.entities.Project;
import bbmri.entities.Researcher;
import bbmri.service.ProjectService;
import bbmri.serviceImpl.ProjectServiceImpl;
import net.sourceforge.stripes.action.*;

import java.util.List;

@UrlBinding("/createproject/{$event}/{project.id}")
public class CreateProjectActionBean extends BasicActionBean {
    private Project project;
    private ProjectService projectService;

    public ProjectService getProjectService() {
        if (projectService == null) {
            projectService = new ProjectServiceImpl();
        }
        return projectService;
    }

    public Project getProject() {return project;}
    public void setProject(Project project) {this.project = project;}

    public Resolution create() {
        getProjectService().create(project, getLoggedResearcher());
        return new ForwardResolution("/allProjects.jsp");
    }

}
