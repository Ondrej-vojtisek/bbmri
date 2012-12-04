package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.service.ProjectService;
import bbmri.serviceImpl.ProjectServiceImpl;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;


@UrlBinding("/approveproject/{$event}/{project.id}")
public class ApproveProjectActionBean extends BasicActionBean {

    private List<Project> projects;

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
        return new ForwardResolution("/approveProject.jsp");
    }
}


