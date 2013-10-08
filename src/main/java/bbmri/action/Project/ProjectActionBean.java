package bbmri.action.project;

import bbmri.action.BasicActionBean;
import bbmri.entities.*;
import bbmri.entities.enumeration.AttachmentType;
import bbmri.entities.enumeration.ProjectState;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import java.io.File;
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

    private static final String ALL = "/webpages/project/project_all.jsp";
    private static final String MY_PROJECTS = "/webpages/project/project_my_projects.jsp";
    private static final String CREATE = "/webpages/project/project_create.jsp";
    private static final String CREATE_INFORMATION = "/webpages/project/project_create_information.jsp";
    private static final String CREATE_MTA = "/webpages/project/project_create_mta.jsp";
    private static final String EDIT = "/webpages/project/project_edit.jsp";
    private static final String SAMPLE_REQUEST = "/webpages/project/sample_request.jsp";

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());



    @ValidateNestedProperties(value = {
             @Validate(on = {"create"},
                     field = "name",
                     required = true),
             @Validate(on = {"create"},
                     field = "fundingOrganization",
                     required = true),
             @Validate(on = {"create"},
                     field = "approvedBy",
                     required = true),
             @Validate(on = {"create"}, field = "mainInvestigator",
                     required = true),
             @Validate(on = {"create"}, field = "homeInstitution",
                     required = true),
             @Validate(on = {"create"}, field = "annotation",
                                required = true),
             @Validate(on = {"create"}, field = "approvalStorage",
                               required = true)

     })
    private Project project;
        private FileBean attachmentFileBean;

        private Attachment attachment;

        public List<Project> getProjects() {
            return projectService.all();
        }

        public Project getProject() {
            return project;
        }

        public void setProject(Project project) {
            this.project = project;
        }

        public List<Project> getMyProjects() {
//            List<Project> projects = new ArrayList<Project>();
//            for(ProjectAdministrator pa : getLoggedUser().getProjectAdministrators()){
//                projects.add(pa.getProject());
//            }
//            return projects;
            return null;

        //    return getLoggedUser().getProjects();
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

        public Resolution create() {
            Project projectNew = projectService.create(project, getLoggedUser().getId());
            getContext().setProject(projectNew);
            return new ForwardResolution(CREATE_MTA);
        }

    @DontValidate
        public Resolution edit() {
            project = projectService.get(project.getId());
            getContext().setProject(project);
            return new ForwardResolution(EDIT);
        }

    @DontValidate
        public Resolution requestSample() {
            project = projectService.get(project.getId());
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
            Project projectDB = projectService.get(project.getId());
            projectService.removeAdministrator(project.getId(), getLoggedUser().getId(), getContext().getMyId());
          //  User user = projectService.removeUserFromProject(getContext().getMyId(), project.getId());
          /*  if (user != null) {
                getContext().setLoggedUser(user);
                getContext().getMessages().add(
                        new SimpleMessage("You have left project {0}", projectDB.getName())
                );
            } */
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
                    Project projectDB = projectService.get(getContext().getProject().getId());
                    attachmentService.create(getContext().getProject().getId(), attachment);
                    //projectService.saveAttachment(getContext().getProject().getId(), attachment);
                    try {

                        attachmentFileBean.save(new File(Attachment.ROOT_DIR_PATH + projectDB.getId().toString() + File.separator
                                + projectDB.getId().toString() + attachment.getAttachmentType().toString()));
                        getContext().getMessages().add(
                                new SimpleMessage("File was uploaded")
                        );
                    } catch (IOException e) {
                        getContext().getMessages().add(
                                new SimpleMessage("Exception: " + e)
                        );
                    }
                }
                return new ForwardResolution(MY_PROJECTS);
            }


}
