package bbmri.action;

import bbmri.DAOimpl.ProjectDAOImpl;
import bbmri.DAOimpl.ResearcherDAOImpl;
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


@UrlBinding("/myprojects/{$event}/{myprojects.id}")
public class MyProjectsActionBean implements ActionBean {

    private MyActionBeanContext ctx;
    private Researcher loggedResearcher;
    private Project project;
    private List<Project> projects;
    private ProjectService projectService;
    private List<Researcher> researchers;
    private List<Project> myProjects;
    private Researcher researcher;
    private List<Long> selectedApprove;
    private List<Long> selected;
    private List<Researcher> freeResearchers;


     public List<Researcher> getFreeResearchers() {
        this.freeResearchers = getProjectService().getAllNotAssignedResearchers(getProject().getId());
        return freeResearchers;
    }

    public void setFreeResearchers(List<Researcher> freeResearchers) {
        this.freeResearchers = freeResearchers;
    }


    public List<Long> getSelectedApprove() {
        return selectedApprove;
    }

    public void setSelectedApprove(List<Long> selectedApprove) {
        this.selectedApprove = selectedApprove;
    }

    public List<Long> getSelected() {
        return selected;
    }

    public void setSelected(List<Long> selected) {
        this.selected = selected;
    }

    public Researcher getResearcher() {
        return researcher;
    }

    public void setResearcher(Researcher researcher) {
        this.researcher = researcher;
    }

    public List<Project> getMyProjects() {
        myProjects = getProjectService().getAllWhichResearcherAdministrate(ctx.getLoggedResearcher().getId());
        return myProjects;
    }

    public void setMyProjects(List<Project> myProjects) {
        this.myProjects = myProjects;
    }

    public List<Researcher> getResearchers() {
        this.researchers = getProjectService().getAllAssignedResearchers(project.getId());
        return researchers;
    }

    public void setResearchers(List<Researcher> researchers) {
        this.researchers = researchers;
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
        projects = getProjectService().getAllByResearcher(ctx.getLoggedResearcher().getId());
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
        if (project == null)
            project = ctx.getProject();
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public Resolution removeResearcherFromProject() {
        if (researcher.getId().equals(ctx.getLoggedResearcher().getId())) {
            /*you can't remove yourself*/
            researchers = getProjectService().getAllAssignedResearchers(getProject().getId());
            return new ForwardResolution("/myProjects.jsp");
        }
        getProjectService().removeResearcherFromProject(researcher.getId(), getProject().getId());
        researchers = getProjectService().getAllAssignedResearchers(getProject().getId());
        return new ForwardResolution("/myProjects.jsp");
    }

    public Resolution edit() {
        project = getProjectService().getById(project.getId());
        ctx.setProject(project);

        return new ForwardResolution("/editProject.jsp");
    }

    public Resolution requestSample() {
        project = getProjectService().getById(project.getId());
        // you can't request sample for not approved project
        if(project.getProjectState()  != ProjectState.NEW){
            ctx.setProject(project);
            return new ForwardResolution("/sampleRequests.jsp");
        }
         return new ForwardResolution("/projectWorker.jsp");
    }

    public Resolution leave() {
        Researcher res = getProjectService().removeResearcherFromProject(project.getId(), getLoggedResearcher().getId());
        if (res != null) {
            ctx.setLoggedResearcher(res);
        }

        return new ForwardResolution("/projectWorker.jsp");
    }

    @Before(stages = LifecycleStage.BindingAndValidation, on = {"update1", "removeResearcherFromProject"})
    public void fillInputs() {
    }

    public Resolution update() {
        getProjectService().update(project);
        return new RedirectResolution(this.getClass(), "zobraz");
    }

    public Resolution removeAll() {
        if (selected != null) {
            for (Long id : selected) {
                  if (id.equals(ctx.getLoggedResearcher().getId())) {
                    /*you can't remove yourself*/
                   return new ForwardResolution("/myProjects.jsp");
            }
                getProjectService().removeResearcherFromProject(id, getProject().getId());
            }
        }
        researchers = getProjectService().getAllAssignedResearchers(getProject().getId());
        return new RedirectResolution(this.getClass(), "zobraz");
    }

    public Resolution assignAll() {
        if (selectedApprove != null) {

            System.out.println("Pocet prvku " + selectedApprove.size());

            for(Long resProject : selectedApprove) {
                getProjectService().assignResearcher(resProject, getProject().getId());
            }
        }
        researchers = getProjectService().getAllAssignedResearchers(getProject().getId());
        return new RedirectResolution(this.getClass(), "zobraz");
    }


    /*Unfortunately not working*/
     public Resolution changeOwnership() {
        project = getProjectService().changeOwnership(getProject().getId(), researcher.getId());
        ctx.setProject(project);
        System.out.println("Admin: " + project.getResearchers().get(0));

        researchers = getProjectService().getAllAssignedResearchers(getProject().getId());
        myProjects = getProjectService().getAllWhichResearcherAdministrate(ctx.getLoggedResearcher().getId());
        System.out.println("MyProjects" + getMyProjects().toString());

        return new RedirectResolution(this.getClass(), "zobraz");
    }

}


