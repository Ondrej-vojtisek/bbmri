package cz.bbmri.action.project;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.enumeration.ProjectState;
import net.sourceforge.stripes.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.util.List;

@PermitAll

@UrlBinding("/approveproject")
public class ApproveProjectActionBean extends BasicActionBean {

    private static final String APPROVE = "/webpages/project/project_approve.jsp";
    private static final String DETAIL = "/webpages/project/project_detail.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

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
        projectService.approve(project.getId(), getContext().getMyId());
        getContext().getMessages().add(
                              new SimpleMessage("Project {0} was approved", project.getName())
                      );
        return new ForwardResolution(APPROVE);
    }

    @DontValidate
       public Resolution deny() {
          // projectService.(project.getId(), getContext().getMyId());
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


