package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;


@UrlBinding("/approveproject/{$event}/{project.id}")
public class ApproveProjectActionBean extends BasicActionBean {

    private static final String APPROVE = "/project_approve.jsp";
    private static final String DETAIL = "/project_detail.jsp";

    private List<Project> projects;

    @SpringBean
    private UserService userService;

    @SpringBean
    private ProjectService projectService;

    private Project project;

    public Project getProject() {
        if(project == null){
            project = getContext().getProject();
        }
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getProjects() {
        return projectService.getAllByProjectState(ProjectState.NEW);
    }

    @DefaultHandler
     public Resolution display() {
         return new ForwardResolution(APPROVE);
     }

    public Resolution approve() {
        projectService.approve(project.getId(), getContext().getLoggedUser().getId());
        getContext().getMessages().add(
                              new SimpleMessage("Project id={0} was approved", project.getName())
                      );
        refreshLoggedUser();
        return new ForwardResolution(APPROVE);
    }

    public Resolution detail() {
           project = projectService.getById(project.getId());
           getContext().setProject(project);
           return new ForwardResolution(DETAIL);
       }

    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
    }

    public Resolution back(){
        return new ForwardResolution(this.getClass(), "display");
    }
}


