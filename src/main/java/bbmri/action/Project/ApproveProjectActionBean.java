package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.action.MyActionBeanContext;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;


@UrlBinding("/approveproject/{$event}/{project.id}")
public class ApproveProjectActionBean extends BasicActionBean {

    private List<Project> projects;

    @SpringBean
    private UserService userService;

    @SpringBean
    private ProjectService projectService;

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getProjects() {
        return projectService.getAllByProjectState(ProjectState.NEW);
    }

    public Resolution approve() {
        projectService.approve(project.getId());
        refreshLoggedUser();
        return new ForwardResolution("/project_approve.jsp");
    }

    public void refreshLoggedUser(){
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
     }
}


