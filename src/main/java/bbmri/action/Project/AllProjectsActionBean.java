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
import net.sourceforge.stripes.controller.LifecycleStage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Map;


@UrlBinding("/allprojects/{$event}/{project.id}")
public class AllProjectsActionBean extends BasicActionBean {

    private Project project;
    private List<Project> projects;

    public List<Project> getProjects() {
        projects = getProjectService().getAll();
        return projects;
    }
    public void setProjects(List<Project> projects) {this.projects = projects;}

    public Project getProject() {
        if (project == null)
            project = getContext().getProject();
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @DefaultHandler
    public Resolution display() {
        getProjects();
        return new ForwardResolution("/allProjects.jsp");
    }

    public Resolution create() {
        getProjectService().create(project, getLoggedResearcher());
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution edit() {
        project = getProjectService().getById(project.getId());
        getContext().setProject(project);

        return new ForwardResolution("/editProject.jsp");
    }

    public Resolution requestSample() {
        project = getProjectService().getById(project.getId());
        // you can't request sample for not approved project
        if(project.getProjectState()  != ProjectState.NEW){
            getContext().setProject(project);
            return new ForwardResolution("/sampleRequests.jsp");
        }
         return new ForwardResolution("/allProjects.jsp");
    }

    public Resolution leave() {
        Researcher res = getProjectService().removeResearcherFromProject(project.getId(), getLoggedResearcher().getId());
        if (res != null) {
            getContext().setLoggedResearcher(res);
        }
        return new ForwardResolution("/allProjects.jsp");
    }

    public Resolution join() {
        Researcher res = getProjectService().assignResearcher(project.getId(), getLoggedResearcher().getId());
        if(res != null){
            getContext().setLoggedResearcher(res);
        }
        return new RedirectResolution(this.getClass(), "display");
    }

}


