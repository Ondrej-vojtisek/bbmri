package bbmri.action;

import bbmri.DAOimpl.ProjectDAOImpl;
import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.Researcher;
import bbmri.service.ProjectService;
import bbmri.serviceImpl.ProjectServiceImpl;
import net.sourceforge.stripes.action.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


@UrlBinding("/project/{$event}/{project.id}")
public class ProjectActionBean implements ActionBean {

    private MyActionBeanContext ctx;
    private Researcher loggedResearcher;
    private List<Project> projects;
    private ProjectService projectService;
    private long projectId = 0;
    private List<Project> newProjects;
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setContext(ActionBeanContext ctx) {
        this.ctx = (MyActionBeanContext) ctx;
    }

    public MyActionBeanContext getContext() {
        return ctx;
    }

    public ProjectService getProjectService() {
        if (projectService == null) {
            projectService = new ProjectServiceImpl();
        }
        return projectService;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public List<Project> getProjects() {
        return getProjectService().getAll();
    }

    public List<Project> getNewProjects() {
        return getProjectService().getAllByProjectState(ProjectState.NEW);
    }

    public Researcher getLoggedResearcher() {
        return ctx.getLoggedResearcher();
    }

    public void setLoggedResearcher(Researcher loggedResearcher) {
        this.loggedResearcher = loggedResearcher;
    }

    @DefaultHandler
    public Resolution zobraz() {
        projects = getProjectService().getAll();
        return new ForwardResolution("/projects.jsp");
    }


    public Resolution approve() {
        getProjectService().approve(project.getId());
        return new ForwardResolution("/approveProject.jsp");
    }

    public Resolution join() {
        Researcher res = getProjectService().assignResearcher(project.getId(), getLoggedResearcher().getId());
        if(res != null){
            ctx.setLoggedResearcher(res);
        }
        return new RedirectResolution(this.getClass(), "zobraz");
    }



}


