package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Notification;
import bbmri.entities.Project;
import bbmri.entities.ProjectState;
import bbmri.entities.User;
import bbmri.service.NotificationService;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.util.List;


@UrlBinding("/allprojects/{$event}/{project.id}")
public class AllProjectsActionBean extends BasicActionBean {

    @SpringBean
    private UserService userService;

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private NotificationService notificationService;

    private Project project;
    private List<Project> projects;

    public List<Project> getProjects() {
        projects = projectService.getAll();
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Project getProject() {
        if (project == null)
            project = getContext().getProject();
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getMyProjects(){
        return projectService.getAllByUserWithRequests(getLoggedUser().getId());
    }

    public List<Notification> getNotifications(){
      //  List<Notification> notifications = notificationService.getAllNewByRecipient(getLoggedUser().getId());
      //  System.err.println("\n\n\n\n" + notifications);
      //  return notifications;
        return null;
    }

    @DefaultHandler
    public Resolution display() {
        getProjects();
        return new ForwardResolution("/project_my_projects.jsp");
    }

    public Resolution create() {
        projectService.create(project, getLoggedUser());
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution edit() {
        project = projectService.getById(project.getId());
        getContext().setProject(project);
        return new ForwardResolution("/project_edit.jsp");
    }

    public Resolution requestSample() {
        project = projectService.getById(project.getId());
        // you can't request sample for not approved project

        if (project.getProjectState() != ProjectState.NEW) {
            getContext().setProject(project);
            return new ForwardResolution("/sample_request.jsp");
        }
        return new ForwardResolution("/project_my_projects.jsp");
    }

    public Resolution leave() {
        if (project == null) {
            return new RedirectResolution(this.getClass(), "display");
        }
        Project projectDB = projectService.getById(project.getId());
        User user = projectService.removeUserFromProject(getLoggedUser().getId(), project.getId());
        if (user != null) {
            getContext().setLoggedUser(user);
            getContext().getMessages().add(
                                  new SimpleMessage("You have left project {0}", projectDB.getName())
                          );
        }
        refreshLoggedUser();
        return new ForwardResolution("/project_my_projects.jsp");
    }

    public Resolution join() {
        if (project == null) {
            return new RedirectResolution(this.getClass(), "display");
        }
        Project projectDB = projectService.getById(project.getId());
        if (projectDB.getUsers() == null) {
            return new RedirectResolution(this.getClass(), "display");
        }
        /*
        projectService.assignUser(getLoggedUser().getId(), projectDB.getId());
        getContext().getMessages().add(
                              new SimpleMessage("You have joined project {0}", projectDB.getName())
                      );
        refreshLoggedUser();
        */
        getContext().getMessages().add(
                                    new SimpleMessage("Message has been sent to project leader.")
                            );

        Notification notification = new Notification();
        notification.setSenderId(getLoggedUser().getId());
        notification.setRecipientId(projectDB.getOwner().getId());
        notification.setMessage("I would like to join your project team. " + getLoggedUser().getWholeName());
        notification.setVisited(false);
        notificationService.create(notification);
        return new ForwardResolution(this.getClass(), "display");
    }

    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
    }
}


