package bbmri.action.Project;

import bbmri.DAOimpl.ProjectDAOImpl;
import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.action.BasicActionBean;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.Researcher;
import bbmri.service.ProjectService;
import bbmri.serviceImpl.ProjectServiceImpl;
import net.sourceforge.stripes.action.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


@UrlBinding("/approveproject/{$event}/{project.id}")
public class ApproveProjectActionBean extends BasicActionBean {

    private List<Project> projects;
    private ProjectService projectService;
    private Project project;

    public Project getProject() {return project;}
    public void setProject(Project project) {this.project = project;}

    public ProjectService getProjectService() {
        if (projectService == null) {
            projectService = new ProjectServiceImpl();
        }
        return projectService;
    }

    public List<Project> getProjects() {return getProjectService().getAllByProjectState(ProjectState.NEW);}

    public Resolution approve() {
        getProjectService().approve(project.getId());
        return new ForwardResolution("/approveProject.jsp");
    }
}


