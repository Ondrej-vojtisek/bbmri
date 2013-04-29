package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.entities.*;
import bbmri.io.ExcelImport;
import bbmri.service.NotificationService;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 3.4.13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/Project/{$event}/{project.id}")
public class ProjectActionBean extends BasicActionBean {

    @SpringBean
    private UserService userService;

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private NotificationService notificationService;

    private Project project;

    private FileBean attachmentFileBean;
    private Attachment attachment;

    public List<Project> getProjects() {
        return projectService.getAll();
    }

    public Project getProject() {
        if (project == null)
            project = getContext().getProject();
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getMyProjects() {
        return projectService.getAllByUser(getLoggedUser().getId());
    }

    public List<Notification> getNotifications() {
        return null;
    }

      public FileBean getAttachmentFileBean() {
             return attachmentFileBean;
         }

         public void setAttachmentFileBean(FileBean attachmentFileBean) {
             this.attachmentFileBean = attachmentFileBean;
         }

         public Attachment getAttachment() {
             return attachment;
         }

         public void setAttachment(Attachment attachment) {
             this.attachment = attachment;
         }

    @DefaultHandler
    public Resolution display() {
        //getProjects();
    return new ForwardResolution("/project_my_projects.jsp");
    }

    @HandlesEvent("displayAll")
    public Resolution displayAll() {
    return new ForwardResolution("/project_all.jsp");
    }

    @HandlesEvent("createProject")
    public Resolution createProject(){
        return new ForwardResolution("/project_create.jsp");
    }

    public Resolution createInitial() {
         getContext().setProject(null);
         return new ForwardResolution("/project_create_information.jsp");
     }

    public Resolution create() {
        Project projectNew = projectService.create(project, getLoggedUser());
        getContext().setProject(projectNew);
        return new ForwardResolution("/project_create_mta.jsp");
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
            getContext().setSampleQuestion(null);
            return new ForwardResolution("/sample_request.jsp");
        }
        return new ForwardResolution(this.getClass(), "display");
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
        return new ForwardResolution(this.getClass(), "display");
    }


    public Resolution uploadMTA() {
            if (attachmentFileBean != null) {
                Attachment attachment = new Attachment();
                attachment.setFileName(attachmentFileBean.getFileName());
                attachment.setContentType(attachmentFileBean.getContentType());
                attachment.setSize(attachmentFileBean.getSize());
                attachment.setAttachmentType(AttachmentType.MATERIAL_TRANSFER_AGREEMENT);
                Project projectDB = projectService.getById(getContext().getProject().getId());
                projectService.saveAttachment(getContext().getProject().getId(), attachment);
                try {
                    attachmentFileBean.save(new File("bbmri_data\\" + projectDB.getId().toString() + "\\"
                            + attachment.getId().toString() + attachment.getAttachmentType().toString()));
                    getContext().getMessages().add(
                            new SimpleMessage("File was uploaded")
                    );
                } catch (IOException e) {
                    getContext().getMessages().add(
                            new SimpleMessage("Exception: " + e)
                    );
                }
            }
            return new ForwardResolution(this.getClass(), "display");
        }

    public void refreshLoggedUser() {
        getContext().setLoggedUser(userService.getById(getLoggedUser().getId()));
    }



}
