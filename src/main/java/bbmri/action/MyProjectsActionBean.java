package bbmri.action;

import bbmri.DAOimpl.ProjectDAOImpl;
import bbmri.DAOimpl.ResearcherDAOImpl;
import bbmri.entities.Project;
import bbmri.entities.Researcher;
import bbmri.service.ProjectService;
import bbmri.serviceImpl.ProjectServiceImpl;
import net.sourceforge.stripes.action.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


@UrlBinding("/myprojects/{$event}/{myprojects.id}")
public class MyProjectsActionBean implements ActionBean {

    private MyActionBeanContext ctx;
    private Researcher loggedResearcher;
    private Project project;
    private List<Project> projects;
    private ProjectService projectService;
    private long researcherId;
    private long projectId = 0;
    private long id;
    private List<Researcher> researchers;

    public List<Researcher> getResearchers() {
        return researchers;
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<Project> getProjects() {
        projects = getProjectService().getAllByResearcher(ctx.getLoggedResearcher());
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Researcher getLoggedResearcher() {
        return ctx.getLoggedResearcher();
    }

    public void setLoggedResearcher(Researcher loggedResearcher) {
        this.loggedResearcher = loggedResearcher;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getResearcherId() {
        return researcherId;
    }

    public void setResearcherId(Long researcherId) {
        this.researcherId = researcherId;
    }

    @DefaultHandler
    public Resolution zobraz() {
        getProjects();
        return new ForwardResolution("/myProjects.jsp");
    }

    public Resolution create() {
        getProjectService().create(project, getLoggedResearcher());
        return new RedirectResolution(this.getClass(), "zobraz");
    }

    public Resolution assignResearcher() {
        getProjectService().assignResearcher(researcherId, projectId);
        if (researcherId == getLoggedResearcher().getId()) {
            /*you can't remove yourself*/
            researchers = getProjectService().getAllAssignedResearchers((Long) projectId);
            return new ForwardResolution("/myProjects.jsp");
        }
        researchers = getProjectService().getAllAssignedResearchers((Long) projectId);
        return new ForwardResolution("/myProjects.jsp");
    }

    public Resolution selectForEdit() {
        researchers = getProjectService().getAllAssignedResearchers(projectId);
        return new ForwardResolution("/myProjects.jsp");
    }

    public Resolution removeResearcherFromProject() {
        if (researcherId == getLoggedResearcher().getId()) {
            /*you can't remove yourself*/
            researchers = getProjectService().getAllAssignedResearchers((Long) projectId);
            return new ForwardResolution("/myProjects.jsp");
        }
        getProjectService().removeResearcherFromProject((Long) researcherId, (Long) projectId);
        researchers = getProjectService().getAllAssignedResearchers((Long) projectId);
        return new ForwardResolution("/myProjects.jsp");
    }

    public Resolution update() {
        project.setId((Long) projectId);
        getProjectService().update(project);
        return new ForwardResolution("/myProjects.jsp");
    }

}


