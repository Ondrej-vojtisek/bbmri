package bbmri.action.Project;

import bbmri.action.BasicActionBean;
import bbmri.entities.*;
import bbmri.io.ExcelImport;
import bbmri.service.NotificationService;
import bbmri.service.ProjectService;
import bbmri.service.UserService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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

@PermitAll
@UrlBinding("/Project")
public class ProjectActionBean extends BasicActionBean {

    private static final String ALL = "/project_all.jsp";
    private static final String MY_PROJECTS = "/project_my_projects.jsp";
    private static final String CREATE = "/project_create.jsp";
    private static final String CREATE_INFORMATION = "/project_create_information.jsp";
    private static final String CREATE_MTA = "/project_create_mta.jsp";
    private static final String EDIT = "/project_edit.jsp";
    private static final String SAMPLE_REQUEST = "/sample_request.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());


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
            return projectService.getAllByUser(getContext().getIdentifier());
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
    @DontValidate
    public Resolution display() {
        logger.debug("Try to view ProjectPage");

        //getProjects();
        return new ForwardResolution(MY_PROJECTS);
    }

    @HandlesEvent("displayAll")
        public Resolution displayAll() {
            return new ForwardResolution(ALL);
        }

        @HandlesEvent("createProject")
        public Resolution createProject(){
            return new ForwardResolution(CREATE);
        }

    @DontValidate
        public Resolution createInitial() {
             getContext().setProject(null);
             return new ForwardResolution(CREATE_INFORMATION);
         }

    @DontValidate
        public Resolution create() {
            Project projectNew = projectService.create(project, getLoggedUser());
            getContext().setProject(projectNew);
            return new ForwardResolution(CREATE_MTA);
        }

    @DontValidate
        public Resolution edit() {
            project = projectService.getById(project.getId());
            getContext().setProject(project);
            return new ForwardResolution(EDIT);
        }

    @DontValidate
        public Resolution requestSample() {
            project = projectService.getById(project.getId());
            // you can't request sample for not approved project

            if (project.getProjectState() != ProjectState.NEW) {
                getContext().setProject(project);
                getContext().setSampleQuestion(null);
                return new ForwardResolution(SAMPLE_REQUEST);
            }
            return new ForwardResolution(this.getClass(), "display");
        }

    @DontValidate
        public Resolution leave() {
            if (project == null) {
                return new RedirectResolution(this.getClass(), "display");
            }
            Project projectDB = projectService.getById(project.getId());
            User user = projectService.removeUserFromProject(getContext().getIdentifier(), project.getId());
            if (user != null) {
                getContext().setLoggedUser(user);
                getContext().getMessages().add(
                        new SimpleMessage("You have left project {0}", projectDB.getName())
                );
            }
            return new ForwardResolution(this.getClass(), "display");
        }

    @DontValidate
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
                getContext().getMessages().add(
                       new SimpleMessage("Select file!")
                     );
                return new ForwardResolution(CREATE_MTA);
            }


}
