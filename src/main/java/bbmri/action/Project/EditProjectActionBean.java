package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Attachment;
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
    @ValidateNestedProperties(value = {
            @Validate(on = {"create"}, field = "name", required = true,
                    minlength = 5, maxlength = 255),
            @Validate(on = {"create"}, field = "description", required = true,
                    minlength = 5, maxlength = 255),
            @Validate(on = {"create"}, field = "fundingOrganization", required = true,
                    minlength = 5, maxlength = 255),
    })
    private Project project;

    private FileBean agreement;

    public FileBean getAgreement() {
        return agreement;
    }

    public void setAgreement(FileBean agreement) {
        this.agreement = agreement;
    }

    @SpringBean
    private UserService userService;

    @SpringBean
    private ProjectService projectService;
    private List<User> users;
    private User user;
    private List<Long> selectedApprove;
    private List<Long> selected;
    private List<User> freeUsers;

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

    @DefaultHandler
    public Resolution zobraz() {
        return new ForwardResolution("/project_all.jsp");
    }

    public Resolution update() {
        projectService.update(project);
        refreshLoggedUser();
        return new ForwardResolution("/project_all.jsp");
    }

    public Resolution removeAll() {
        if (selected != null) {
            for (Long id : selected) {
                if (id.equals(getContext().getLoggedUser().getId())) {
                    /*you can't remove yourself*/
                    return new ForwardResolution("/project_all.jsp");
                }
                projectService.removeUserFromProject(id, getProject().getId());
            }
        }
        users = projectService.getAllAssignedUsers(getProject().getId());
        refreshLoggedUser();
        return new ForwardResolution("/project_all.jsp");
    }

    public Resolution assignAll() {
        if (selectedApprove != null) {
            for (Long resProject : selectedApprove) {
                projectService.assignUser(resProject, getProject().getId());
            }
        }
        users = projectService.getAllAssignedUsers(getProject().getId());
        refreshLoggedUser();
        return new ForwardResolution("/project_edit.jsp");
    }

    public Resolution changeOwnership() {
        projectService.changeOwnership(getContext().getProject().getId(), user.getId());
        refreshLoggedUser();
        return new ForwardResolution("/project_all.jsp");
    }

    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
    }

    public Resolution upload() {
        if (agreement != null) {
            Attachment attachment = new Attachment();
            attachment.setFileName(agreement.getFileName());
            attachment.setContentType(agreement.getContentType());
            attachment.setSize(agreement.getSize());
            Project projectDB = projectService.getById(getContext().getProject().getId());
            projectService.saveAgreement(getContext().getProject().getId(), attachment);
            try {
                agreement.save(new File("bbmri_data\\" + projectDB.getId().toString() + "\\"
                        + attachment.getId().toString()));
            } catch (IOException e) {
            }
        }
        return new ForwardResolution("/project_all.jsp");
    }

    public Resolution download() throws Exception {
        Attachment attachment = projectService.getAgreement(getContext().getProject().getId());
        String fileName = attachment.getFileName();
        String filePath = projectService.getAttachmentPath(attachment);
        return new StreamingResolution(attachment.getContentType(),
                new FileInputStream(filePath)).setFilename(fileName);
    }

}
