package bbmri.action.project;

import bbmri.action.BasicActionBean;
import bbmri.entities.Attachment;
import bbmri.entities.Project;
import bbmri.entities.ProjectAdministrator;
import bbmri.entities.enumeration.AttachmentType;
import bbmri.entities.enumeration.Permission;
import bbmri.facade.ProjectFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 3.4.13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */

@UrlBinding("/project/{$event}/{id}")
public class ProjectActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private ProjectFacade projectFacade;

    private Project project;

    /* Project identifier */
    private Long id;

    private Attachment attachment;

    private Permission permission;

    private Long adminId;

    private Long attachmentId;

    /* Attachment upload in project detail/edit view */
    private FileBean attachmentFileBean;

    /* Attachment upload in project detail/edit view
     * Important to specify what is the purpose of uploaded file
      * */
    private AttachmentType attachmentType;

    public List<Project> getAll() {
        return projectFacade.all();
    }

    public List<Project> getMyProjects() {
        return projectFacade.getProjects(getContext().getMyId());
    }

    public Project getProject() {
        if (project == null) {
            if (id != null) {
                project = projectFacade.get(id);
            }
        }
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    //        public List<Project> getMyProjects() {
//            return null;
//        }

    //          public FileBean getAttachmentFileBean() {
//                 return attachmentFileBean;
//             }
//
//             public void setAttachmentFileBean(FileBean attachmentFileBean) {
//                 this.attachmentFileBean = attachmentFileBean;
//             }
//
    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public List<Attachment> getAttachments() {
        return projectFacade.getAttachments(id);
    }

    public Set<ProjectAdministrator> getAdministrators() {

        if (id == null) {
            return null;
        }
        return getProject().getProjectAdministrators();
    }


//    public Set<ProjectAdministrator> getProjectRoles() {
//
//           if (id == null) {
//               return null;
//           }
//           return getProject().getProjectAdministrators();
//       }

    public boolean getAllowedManager() {
        return projectFacade.hasPermission(Permission.MANAGER, id, getContext().getMyId());
    }

    public boolean getAllowedEditor() {
        return projectFacade.hasPermission(Permission.EDITOR, id, getContext().getMyId());
    }

    public boolean getAllowedExecutor() {
        return projectFacade.hasPermission(Permission.EXECUTOR, id, getContext().getMyId());
    }

    public boolean getAllowedVisitor() {
        return projectFacade.hasPermission(Permission.VISITOR, id, getContext().getMyId());
    }

    public FileBean getAttachmentFileBean() {
        return attachmentFileBean;
    }

    public void setAttachmentFileBean(FileBean attachmentFileBean) {
        this.attachmentFileBean = attachmentFileBean;
    }

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    private Resolution administratorsResolution(boolean forward) {
        if (forward) {
            if (getAllowedManager()) {
                return new ForwardResolution(this.getClass(), "editAdministrators").addParameter("id", id);
            }
            return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS_READ).addParameter("id", id);
        }

        if (getAllowedManager()) {
            return new RedirectResolution(this.getClass(), "editAdministrators").addParameter("id", id);
        }
        return new RedirectResolution(this.getClass(), "administrators").addParameter("id", id);
    }


    @DontValidate
    @HandlesEvent("allProjects")
    @RolesAllowed({"administrator", "developer"})
    public Resolution display() {
        return new ForwardResolution(PROJECT_ALL);
    }

    @DefaultHandler
    @DontValidate
    @HandlesEvent("myProjects")
    @RolesAllowed({"administrator", "developer", "project_team_member"})
    public Resolution myProjects() {
        return new ForwardResolution(PROJECT_MY);
    }

    @PermitAll
    @DontValidate
    @HandlesEvent("create")
    public Resolution create() {
        return new ForwardResolution(CreateProjectActionBean.class);
    }

    @DontValidate
    @HandlesEvent("detail")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedVisitor}"})
    public Resolution detail() {
        return new ForwardResolution(PROJECT_DETAIL_READ);
    }

    @DontValidate
    @HandlesEvent("attachments")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedVisitor}"})
    public Resolution attachments() {
        return new ForwardResolution(PROJECT_DETAIL_ATTACHMENTS);
    }

    @DontValidate
    @HandlesEvent("administrators")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedVisitor}"})
    public Resolution administrators() {
        return administratorsResolution(true);
    }

    @DontValidate
    @HandlesEvent("editAdministrators")
    @RolesAllowed({"project_team_member if ${allowedManager}"})
    public Resolution editAdministrators() {
        return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS_WRITE);
    }

    @DontValidate
    @HandlesEvent("edit")
    @RolesAllowed({"project_team_member if ${allowedEditor}"})
    public Resolution edit() {
        return new ForwardResolution(PROJECT_DETAIL_WRITE).addParameter("id", id);
    }

    @HandlesEvent("update")
    @RolesAllowed({"project_team_member if ${allowedEditor}"})
    public Resolution update() {
        projectFacade.updateProject(project);
        return new RedirectResolution(PROJECT_DETAIL_WRITE).addParameter("id", id);
    }

    @HandlesEvent("downloadAttachment")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedVisitor}"})
    public Resolution downloadAttachment() {
        try {
            return projectFacade.downloadFile(attachment.getId());
        } catch (FileNotFoundException ex) {
            getContext().getMessages().add(
                    new SimpleMessage("File does not exist.")

            );
            return new ForwardResolution(this.getClass(), "attachments");
        }
    }

    @HandlesEvent("deleteAttachment")
    @RolesAllowed({"project_team_member if ${allowedEditor}"})
    public Resolution deleteAttachment() {
        projectFacade.deleteAttachment(attachmentId);
        return new RedirectResolution(this.getClass(), "attachments").addParameter("id", id);
    }


    @DontValidate
    @HandlesEvent("setPermission")
    @RolesAllowed({"project_team_member if ${allowedManager}"})
    public Resolution setPermission() {
        // projectFacade.changeBiobankAdministratorPermission(administratorId, permission, getContext().getMyId());
        // It changes data - redirect necessary
        return administratorsResolution(false);
    }

    @DontValidate
    @HandlesEvent("removeAdministrator")
    @RolesAllowed({"project_team_member if ${allowedManager}"})
    public Resolution removeAdministrator() {
        // projectFacade.removeBiobankAdministrator(administratorId, getContext().getMyId());
        return administratorsResolution(false);
    }

    @DontValidate
    @HandlesEvent("delete")
    @RolesAllowed({"developer"})
    public Resolution delete() {
        projectFacade.removeProject(id);
        return new RedirectResolution(this.getClass());
    }

    @DontValidate
    @HandlesEvent("addAdministrator")
    @RolesAllowed({"project_team_member if ${allowedManager}"})
    public Resolution addAdministrator() {
        projectFacade.assignAdministratorToProject(id, getContext().getMyId(), adminId, permission);
        return administratorsResolution(false);
    }

    @DontValidate
    @HandlesEvent("addAttachment")
    @RolesAllowed({"project_team_member if ${allowedEditor}"})
    public Resolution addAttachment() {
        return new ForwardResolution(PROJECT_DETAIL_ATTACHMENT_ADD);
    }

    @HandlesEvent("mtaUpload")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedEditor}"})
    public Resolution mtaUpload() {
        // TODO: obalit try catch, pridat confirm hlasky
        projectFacade.createAttachment(attachmentFileBean, attachmentType, id);
        return new RedirectResolution(this.getClass(), "attachments").addParameter("id", id);
    }


//    @DontValidate
//    public Resolution download() throws Exception {
//        //   System.err.println("Attachment ID : " + attachment.getId());
//        attachment = attachmentService.get(attachment.getId());
//        String fileName = attachment.getFileName();
//        String filePath = attachmentService.getAttachmentPath(attachment);
//        return new StreamingResolution(attachment.getContentType(),
//                new FileInputStream(filePath)).setFilename(fileName);
//    }


//    @HandlesEvent("displayAll")
//    public Resolution displayAll() {
//            return new ForwardResolution(ALL);
//    }

//        @HandlesEvent("createProject")
//        public Resolution createProject(){
//            return new ForwardResolution(CREATE);
//        }

//    @DontValidate
//        public Resolution createInitial() {
//             getContext().setProject(null);
//             return new ForwardResolution(CREATE_INFORMATION);
//         }

//        public Resolution create() {
//            Project projectNew = projectService.create(project, getLoggedUser().getId());
//            getContext().setProject(projectNew);
//            return new ForwardResolution(CREATE_MTA);
//        }

//    @DontValidate
//        public Resolution edit() {
//            project = projectService.get(project.getId());
//            getContext().setProject(project);
//            return new ForwardResolution(EDIT);
//        }

//    @DontValidate
//        public Resolution requestSample() {
//            project = projectService.get(project.getId());
//            // you can't request sample for not approved project
//
//            if (project.getProjectState() != ProjectState.NEW) {
//                getContext().setProject(project);
//                getContext().setSampleQuestion(null);
//                return new ForwardResolution(SAMPLE_REQUEST);
//            }
//            return new ForwardResolution(this.getClass(), "display");
//        }

//    @DontValidate
//        public Resolution leave() {
//            if (project == null) {
//                return new RedirectResolution(this.getClass(), "display");
//            }
//            Project projectDB = projectService.get(project.getId());
//            projectService.removeAdministrator(project.getId(), getLoggedUser().getId(), getContext().getMyId());
//          //  User user = projectService.removeUserFromProject(getContext().getMyId(), project.getId());
//          /*  if (user != null) {
//                getContext().setLoggedUser(user);
//                getContext().getMessages().add(
//                        new SimpleMessage("You have left project {0}", projectDB.getName())
//                );
//            } */
//            return new ForwardResolution(this.getClass(), "display");
//        }

//    @DontValidate
//        public Resolution uploadMTA() {
//                if (attachmentFileBean != null) {
//                    Attachment attachment = new Attachment();
//                    attachment.setFileName(attachmentFileBean.getFileName());
//                    attachment.setContentType(attachmentFileBean.getContentType());
//                    attachment.setSize(attachmentFileBean.getSize());
//                    attachment.setAttachmentType(AttachmentType.MATERIAL_TRANSFER_AGREEMENT);
//                    Project projectDB = projectService.get(getContext().getProject().getId());
//                    attachmentService.create(getContext().getProject().getId(), attachment);
//                    //projectService.saveAttachment(getContext().getProject().getId(), attachment);
//                    try {
//
//                        attachmentFileBean.save(new File(Attachment.ROOT_DIR_PATH + projectDB.getId().toString() + File.separator
//                                + projectDB.getId().toString() + attachment.getAttachmentType().toString()));
//                        getContext().getMessages().add(
//                                new SimpleMessage("File was uploaded")
//                        );
//                    } catch (IOException e) {
//                        getContext().getMessages().add(
//                                new SimpleMessage("Exception: " + e)
//                        );
//                    }
//                }
//                return new ForwardResolution(MY_PROJECTS);
//            }


}
