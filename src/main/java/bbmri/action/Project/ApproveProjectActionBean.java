package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.PermitAll;
import java.util.List;

@PermitAll

@UrlBinding("/approveproject")
public class ApproveProjectActionBean extends BasicActionBean {

    private static final String APPROVE = "/project_approve.jsp";
    private static final String DETAIL = "/project_detail.jsp";

    private List<Project> projects;

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
    @DontValidate
     public Resolution display() {
         return new ForwardResolution(APPROVE);
     }
    @DontValidate
    public Resolution approve() {
        projectService.approve(project.getId(), getContext().getIdentifier());
        getContext().getMessages().add(
                              new SimpleMessage("Project {0} was approved", project.getName())
                      );
        return new ForwardResolution(APPROVE);
    }

    @DontValidate
       public Resolution deny() {
          // projectService.(project.getId(), getContext().getIdentifier());
           getContext().getMessages().add(
                                 new SimpleMessage("Project {0} was denied", project.getName())
                         );
           return new ForwardResolution(APPROVE);
       }

    @DontValidate
    public Resolution detail() {
           project = projectService.get(project.getId());
           getContext().setProject(project);
           return new ForwardResolution(DETAIL);
       }

}


