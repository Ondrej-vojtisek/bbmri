package cz.bbmri.action.project;

import cz.bbmri.action.base.BasicActionBean;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.ProjectAdministrator;
import cz.bbmri.entities.enumeration.AttachmentType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.entities.enumeration.ProjectState;
import cz.bbmri.facade.ProjectFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
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

@HttpCache(allow = false)
@UrlBinding("/project/{$event}/{id}")
public class ProjectActionBean extends BasicActionBean {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @SpringBean
    private ProjectFacade projectFacade;


    @ValidateNestedProperties(value = {
            @Validate(field = "name",
                    required = true, on = "update"),
            @Validate(field = "principalInvestigator",
                    required = true, on = "update"),
            @Validate(field = "annotation",
                    required = true, on = "update")
    })
    private Project project;

    /* Project identifier */
    private Long id;

    private Attachment attachment;

    private Permission permission;

    private Long adminId;

    private Long attachmentId;

    private Long userAdminId;

    /* Attachment upload in project detail/edit view */
    private FileBean attachmentFileBean;

    /* Attachment upload in project detail/edit view
     * Important to specify what is the purpose of uploaded file
      * */
    private AttachmentType attachmentType;

    public Long getUserAdminId() {
        return userAdminId;
    }

    public void setUserAdminId(Long userAdminId) {
        this.userAdminId = userAdminId;
    }

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



    /* When the project is marked as finished than it can't be edited or changes in any way */

    public boolean getAllowedManager() {
        return projectFacade.hasPermission(Permission.MANAGER, id, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedEditor() {
        return projectFacade.hasPermission(Permission.EDITOR, id, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedExecutor() {
        return projectFacade.hasPermission(Permission.EXECUTOR, id, getContext().getMyId()) && !isFinished();
    }

    public boolean getAllowedVisitor() {
        return projectFacade.hasPermission(Permission.VISITOR, id, getContext().getMyId());
    }

    public boolean getIsMyAccount() {
        return getContext().getMyId().equals(userAdminId);
    }

    public boolean getBiobankExecutor() {
        return projectFacade.hasBiobankExecutePermission(getContext().getMyId());
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

    public boolean getIsNew() {
        return getProject().getProjectState().equals(ProjectState.NEW);
    }

    public boolean getIsStarted() {
        return getProject().getProjectState().equals(ProjectState.STARTED);
    }

    private boolean isFinished() {
        return getProject().getProjectState().equals(ProjectState.FINISHED);
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
    @PermitAll
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
        return new ForwardResolution(PROJECT_DETAIL_GENERAL);
    }

    @DontValidate
    @HandlesEvent("attachments")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedVisitor}"})
    public Resolution attachments() {
        return new ForwardResolution(PROJECT_DETAIL_ATTACHMENTS);
    }

    @DontValidate
    @HandlesEvent("administratorsResolution")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedVisitor}"})
    public Resolution administratorsResolution() {
        return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS).addParameter("id", id);
    }

    @DontValidate
    @HandlesEvent("editAdministrators")
    @RolesAllowed({"project_team_member if ${allowedManager}"})
    public Resolution editAdministrators() {
        return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS).addParameter("id", id);
    }

    /*Here only bcs of permission definition*/
    @DontValidate
    @HandlesEvent("edit")
    @RolesAllowed({"project_team_member if ${allowedEditor}"})
    public Resolution edit() {
        return new ForwardResolution(PROJECT_DETAIL_GENERAL).addParameter("id", id);
    }

    @HandlesEvent("update")
    @RolesAllowed({"project_team_member if ${allowedEditor}"})
    public Resolution update() {
        if(!projectFacade.updateProject(project, getContext().getMyId())){
            return new ForwardResolution(PROJECT_DETAIL_GENERAL).addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(PROJECT_DETAIL_GENERAL).addParameter("id", id);
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
        if (!projectFacade.deleteAttachment(attachmentId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "attachments").addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "attachments").addParameter("id", id);
    }


    @DontValidate
    @HandlesEvent("setPermission")
    @RolesAllowed({"project_team_member if ${allowedManager}"})
    public Resolution setPermission() {
        if(!projectFacade.changeAdministratorPermission(adminId, permission, getContext().getValidationErrors(), getContext().getMyId())){
            return new ForwardResolution(this.getClass(), "administratorsResolution").addParameter("id", id);
        }
        // It changes data - redirect necessary
        successMsg(null);
        return new RedirectResolution(this.getClass(), "administratorsResolution").addParameter("id", id);
    }

    @DontValidate
    @HandlesEvent("removeAdministrator")
    @RolesAllowed({"project_team_member if ${allowedManager or isMyAccount}"}) //project_team_member if ${allowedManager},
    public Resolution removeAdministrator() {
        if(!projectFacade.removeAdministrator(adminId, getContext().getValidationErrors(), getContext().getMyId())){
            return new ForwardResolution(this.getClass(), "administratorsResolution").addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "administratorsResolution").addParameter("id", id);
    }

    public String getRemoveQuestion(){
        if(getIsMyAccount()){
            return this.getName() + ".questionRemoveMyself";
        }
        return this.getName() + ".questionRemoveAdministrator";
    }

    @DontValidate
    @HandlesEvent("delete")
    @RolesAllowed({"developer"})
    public Resolution delete() {
        if (!projectFacade.removeProject(id,
                getContext().getPropertiesStoragePath(),
                getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass());
        }
        successMsg(null);
        return new RedirectResolution(this.getClass());
    }

    @DontValidate
    @HandlesEvent("addAdministrator")
    @RolesAllowed({"project_team_member if ${allowedManager}"})
    public Resolution addAdministrator() {
        if(!projectFacade.assignAdministrator(id, adminId, permission, getContext().getValidationErrors(), getContext().getMyId())){
            return new ForwardResolution(this.getClass(), "administratorsResolution").addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "administratorsResolution").addParameter("id", id);
    }

    @DontValidate
    @HandlesEvent("addAttachment")
    @RolesAllowed({"project_team_member if ${allowedEditor}"})
    public Resolution addAttachment() {
        return new ForwardResolution(PROJECT_DETAIL_ATTACHMENT_ADD);
    }

    @HandlesEvent("attachmentUpload")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedEditor}"})
    public Resolution attachmentUpload() {

        int result = projectFacade.createAttachment(attachmentFileBean,
                attachmentType, id, getContext().getPropertiesStoragePath(),
                getContext().getValidationErrors(), getContext().getMyId());

        if (result < 0) {
            return new ForwardResolution(this.getClass(), "addAttachment").addParameter("id", id);
        }

        if (result == 1) {
            getContext().getMessages().add(new LocalizableMessage("cz.bbmri.action.CreateProjectActionBean.AttachmentOverwritten"));
        }

        if (result == 0) {
            successMsg(null);
        }

        return new RedirectResolution(this.getClass(), "attachments").addParameter("id", id);
    }


    @HandlesEvent("approve")
    @RolesAllowed({"biobank_operator if ${biobankExecutor}"})
    public Resolution approve() {
        if (!projectFacade.approveProject(id, getContext().getMyId(), getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail").addParameter("id", id);
    }

    @HandlesEvent("deny")
    @RolesAllowed({"biobank_operator if ${biobankExecutor}"})
    public Resolution deny() {
        if (!projectFacade.denyProject(id, getContext().getMyId(), getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail").addParameter("id", id);
    }

    @HandlesEvent("markAsFinished")
    @RolesAllowed({"project_team_member if ${allowedExecutor}"})
    public Resolution markAsFinished() {
        if (!projectFacade.markAsFinished(id, getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("id", id);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail").addParameter("id", id);
    }

}
