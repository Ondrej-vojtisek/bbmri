package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Attachment;
import bbmri.entities.AttachmentType;
import bbmri.entities.Project;
import bbmri.entities.User;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/editproject/{$event}/{project.id}")
public class EditProjectActionBean extends BasicActionBean {

    private static final String EDIT = "/project_edit.jsp";

    @ValidateNestedProperties(value = {
            @Validate(on = {"create"}, field = "name", required = true),
            @Validate(on = {"create"}, field = "annotation", required = true),
            @Validate(on = {"create"}, field = "fundingOrganization", required = true),
    })
    private Project project;
    @SpringBean
    private UserService userService;

    @SpringBean
    private ProjectService projectService;
    private List<User> users;
    private User user;
    private List<Long> selectedApprove;
    private List<Long> selected;
    private List<User> freeUsers;
    private Attachment attachment;

    public List<User> getFreeUsers() {
        this.freeUsers = projectService.getAllNotAssignedUsers(getProject().getId());
        return freeUsers;
    }

    public void setFreeUsers(List<User> freeUsers) {
        this.freeUsers = freeUsers;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        this.users = projectService.getAllAssignedUsers(project.getId());
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Project getProject() {
        if (project == null)
            project = getContext().getProject();
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Attachment getAttachment() {
               return attachment;
           }

    public void setAttachment(Attachment attachment) {
               this.attachment = attachment;
    }

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(EDIT);
    }

    public Resolution update() {
        projectService.update(project);
        refreshLoggedUser();
        return new ForwardResolution(ProjectActionBean.class);
    }

    public Resolution removeAll() {
        Integer removed = 0;
        if (selected != null) {
            for (Long id : selected) {
                if (id.equals(getContext().getIdentifier())) {
                    /*you can't remove yourself*/
                    return new ForwardResolution(ProjectActionBean.class);
                }
                projectService.removeUserFromProject(id, getProject().getId());
                removed++;
            }
        }
        getContext().getMessages().add(
                                 new SimpleMessage("{0} users removed", removed)
                         );
        users = projectService.getAllAssignedUsers(getProject().getId());
        refreshLoggedUser();
        return new RedirectResolution(ProjectActionBean.class);
    }

    public Resolution assignAll() {
        Integer assigned = 0;
        if (selectedApprove != null) {
            for (Long resProject : selectedApprove) {
                projectService.assignUser(resProject, getProject().getId());
                assigned++;
            }
        }
        getContext().getMessages().add(
                       new SimpleMessage("{0} users assigned", assigned)
               );
        users = projectService.getAllAssignedUsers(getProject().getId());
        refreshLoggedUser();
        return new RedirectResolution(this.getClass(), "display");
    }

    public Resolution changeOwnership() {
        projectService.changeOwnership(getContext().getProject().getId(), user.getId());
        getContext().getMessages().add(
                              new SimpleMessage("Ownership of project was changed")
                      );
        refreshLoggedUser();
        return new RedirectResolution(ProjectActionBean.class);
    }

    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getContext().getIdentifier()));
    }

    public List<Attachment> getAttachments() {
              return projectService.getAttachmentsByProject(getProject().getId());
          }

    public Resolution download() throws Exception {
               System.err.println("Attachment ID : " + attachment.getId());
               attachment = projectService.getAttachmentById(attachment.getId());
               String fileName = attachment.getFileName();
               String filePath = projectService.getAttachmentPath(attachment);
               return new StreamingResolution(attachment.getContentType(),
                       new FileInputStream(filePath)).setFilename(fileName);
           }

}
