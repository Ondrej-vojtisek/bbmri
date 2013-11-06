package bbmri.action.project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Attachment;
import bbmri.entities.enumeration.Permission;
import bbmri.entities.Project;
import bbmri.entities.User;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ori
 * Date: 17.11.12
 * Time: 20:25
 * To change this template use File | Settings | File Templates.
 */
@PermitAll
@UrlBinding("/editproject")
public class EditProjectActionBean extends BasicActionBean {

    private static final String EDIT = "/webpages/project/project_edit.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @ValidateNestedProperties(value = {
                @Validate(on = {"update"},
                        field = "name",
                        required = true),
                @Validate(on = {"update"},
                        field = "fundingOrganization",
                        required = true),
                @Validate(on = {"update"},
                        field = "approvedBy",
                        required = true),
                @Validate(on = {"update"}, field = "mainInvestigator",
                        required = true),
                @Validate(on = {"update"}, field = "homeInstitution",
                        required = true),
                @Validate(on = {"update"}, field = "annotation",
                                   required = true),
                @Validate(on = {"update"}, field = "approvalStorage",
                                  required = true)

        })
    private Project project;
    private List<User> users;
    private User user;
    private List<Long> selectedApprove;
    private List<Long> selected;
    private List<User> freeUsers;
    private Attachment attachment;

    public List<User> getFreeUsers() {
        /*
        TODO - udelat na zaklade vyhledavani uzivatelu

        this.freeUsers = projectService.getAllNotAssignedUsers(getProject().getId());
        return freeUsers;  */

        return null;
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
        //TODO
        //users = projectService.get(project.getId()).getUsers();
        //return users;
        return null;
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
    @DontValidate

    @DefaultHandler
    public Resolution display() {
        return new ForwardResolution(EDIT);
    }

    public Resolution update() {
        projectService.update(project);
        return new ForwardResolution(ProjectActionBean.class);
    }

    @DontValidate
    public Resolution removeAll() {
        Integer removed = 0;
        if (selected != null) {
            for (Long id : selected) {
                if (id.equals(getContext().getMyId())) {
                    /*you can't remove yourself*/
                    return new ForwardResolution(ProjectActionBean.class);
                }
                projectService.removeAdministrator(getProject().getId(), getLoggedUser().getId(), id);
                removed++;
            }
        }
        getContext().getMessages().add(
                                 new SimpleMessage("{0} users removed", removed)
                         );
        //TODO
        //users = getProject().getUsers();

        return new RedirectResolution(ProjectActionBean.class);
    }

    @DontValidate
    public Resolution assignAll() {
        Integer assigned = 0;
        if (selectedApprove != null) {
            for (Long resProject : selectedApprove) {
                // TODO: Manager can't be default
                projectService.changeAdministratorPermission(getProject().getId(), getLoggedUser().getId(), resProject, Permission.MANAGER);
                assigned++;
            }
        }
        getContext().getMessages().add(
                       new SimpleMessage("{0} users assigned", assigned)
               );
        //TODO
        //users = getProject().getUsers();
        return new RedirectResolution(this.getClass(), "display");
    }

    @DontValidate
    public Resolution changeOwnership() {
        /*projectService.changeOwnership(getContext().getProject().getId(), user.getId());
        getContext().getMessages().add(
                              new SimpleMessage("Ownership of project was changed")
                      );
                      */
        getContext().getMessages().add(
                                     new SimpleMessage("Ownership of project was not changed - TODO this in code"));
        return new RedirectResolution(ProjectActionBean.class);
    }

    public List<Attachment> getAttachments() {
              //return getProject().getAttachments();
        /* Nemuze zde byt pouze getProject().getAttachment kvuli org.hibernate.LazyInitializationException */

              return attachmentService.getAttachmentsByProject(getProject().getId());
          }

//    @DontValidate
//    public Resolution download() throws Exception {
//               System.err.println("Attachment ID : " + attachment.getId());
//               attachment = attachmentService.get(attachment.getId());
//               String fileName = attachment.getFileName();
//               String filePath = attachmentService.getAttachmentPath(attachment);
//               return new StreamingResolution(attachment.getContentType(),
//                       new FileInputStream(filePath)).setFilename(fileName);
//           }

}
