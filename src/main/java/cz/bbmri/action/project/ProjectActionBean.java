package cz.bbmri.action.project;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.*;
import cz.bbmri.entities.enumeration.AttachmentType;
import cz.bbmri.entities.enumeration.Permission;
import cz.bbmri.facade.ProjectFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
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
@UrlBinding("/project/{$event}/{projectId}")
public class ProjectActionBean extends PermissionActionBean {

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

    public void setProject(Project project) {
        this.project = project;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Long getId() {
        return adminId;
    }

    public void setId(Long adminId) {
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
        return projectFacade.getAttachments(projectId);
    }

    public Set<ProjectAdministrator> getAdministrators() {

        if (projectId == null) {
            return null;
        }
        return getProject().getProjectAdministrators();
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

    public List<SampleRequest> getSampleRequests() {
        if (projectId == null) {
            return null;
        }

        logger.debug("ProjectActionBean: SampleRequestList: " + projectFacade.getProjectSampleRequests(projectId));

        return projectFacade.getProjectSampleRequests(projectId);
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
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectVisitor}"})
    public Resolution detail() {
        return new ForwardResolution(PROJECT_DETAIL_GENERAL);
    }

    @DontValidate
    @HandlesEvent("attachments")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectVisitor}"})
    public Resolution attachments() {
        return new ForwardResolution(PROJECT_DETAIL_ATTACHMENTS);
    }

    @DontValidate
    @HandlesEvent("administratorsResolution")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectVisitor}"})
    public Resolution administratorsResolution() {
        return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS).addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("editAdministrators")
    @RolesAllowed({"project_team_member if ${allowedProjectManager}"})
    public Resolution editAdministrators() {
        return new ForwardResolution(PROJECT_DETAIL_ADMINISTRATORS).addParameter("projectId", projectId);
    }

    /*Here only bcs of permission definition*/
    @DontValidate
    @HandlesEvent("edit")
    @RolesAllowed({"project_team_member if ${allowedProjectEditor}"})
    public Resolution edit() {
        return new ForwardResolution(PROJECT_DETAIL_GENERAL).addParameter("projectId", projectId);
    }

    @HandlesEvent("update")
    @RolesAllowed({"project_team_member if ${allowedProjectEditor}"})
    public Resolution update() {
        if (!projectFacade.updateProject(project, getContext().getMyId())) {
            return new ForwardResolution(PROJECT_DETAIL_GENERAL).addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(PROJECT_DETAIL_GENERAL).addParameter("projectId", projectId);
    }

    @HandlesEvent("downloadAttachment")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectVisitor}"})
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
    @RolesAllowed({"project_team_member if ${allowedProjectEditor}"})
    public Resolution deleteAttachment() {
        if (!projectFacade.deleteAttachment(attachmentId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "attachments").addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "attachments").addParameter("projectId", projectId);
    }


    @DontValidate
    @HandlesEvent("setPermission")
    @RolesAllowed({"project_team_member if ${alloweProjectdManager}"})
    public Resolution setPermission() {
        if (!projectFacade.changeAdministratorPermission(adminId, permission, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "administratorsResolution").addParameter("projectId", projectId);
        }
        // It changes data - redirect necessary
        successMsg(null);
        return new RedirectResolution(this.getClass(), "administratorsResolution").addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("removeAdministrator")
    @RolesAllowed({"project_team_member if ${allowedProjectManager or isMyAccount}"})
    //project_team_member if ${allowedManager},
    public Resolution removeAdministrator() {
        if (!projectFacade.removeAdministrator(adminId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "administratorsResolution").addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "administratorsResolution").addParameter("projectId", projectId);
    }

    public String getRemoveQuestion() {
        if (getIsMyAccount()) {
            return this.getName() + ".questionRemoveMyself";
        }
        return this.getName() + ".questionRemoveAdministrator";
    }

    @DontValidate
    @HandlesEvent("delete")
    @RolesAllowed({"developer"})
    public Resolution delete() {

        if (!projectFacade.removeProject(projectId,
                getContext().getPropertiesStoragePath(),
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass());
        }
        successMsg(null);
        return new RedirectResolution(this.getClass());
    }

    @DontValidate
    @HandlesEvent("addAdministrator")
    @RolesAllowed({"project_team_member if ${allowedProjectManager}"})
    public Resolution addAdministrator() {
        if (!projectFacade.assignAdministrator(projectId, adminId, permission, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "administratorsResolution").addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "administratorsResolution").addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("addAttachment")
    @RolesAllowed({"project_team_member if ${allowedProjectEditor}"})
    public Resolution addAttachment() {
        return new ForwardResolution(PROJECT_DETAIL_ATTACHMENT_ADD);
    }

    @HandlesEvent("attachmentUpload")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectEditor}"})
    public Resolution attachmentUpload() {

        int result = projectFacade.createAttachment(attachmentFileBean,
                attachmentType, projectId, getContext().getPropertiesStoragePath(),
                getContext().getValidationErrors(), getContext().getMyId());

        if (result < 0) {
            return new ForwardResolution(this.getClass(), "addAttachment").addParameter("projectId", projectId);
        }

        if (result == 1) {
            getContext().getMessages().add(new LocalizableMessage("cz.bbmri.action.CreateProjectActionBean.AttachmentOverwritten"));
        }

        if (result == 0) {
            successMsg(null);
        }

        return new RedirectResolution(this.getClass(), "attachments").addParameter("projectId", projectId);
    }


    @HandlesEvent("approve")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution approve() {

        // If project is mine than I can't approve it

        if (getAllowedProjectVisitor()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.project.ProjectActionBean.approveMyProject"));
            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }

        logger.debug("Approve - Start");

        if (!projectFacade.approveProject(projectId, getContext().getMyId(), getContext().getValidationErrors())) {

            logger.debug("Approve - failed");

            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }

        logger.debug("Approve - Finish");

        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail").addParameter("projectId", projectId);
    }

    @HandlesEvent("deny")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution deny() {
        if (getAllowedProjectVisitor()) {
            getContext().getValidationErrors().addGlobalError(new LocalizableError("cz.bbmri.action.project.ProjectActionBean.denyMyProject"));
            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }

        if (!projectFacade.denyProject(projectId, getContext().getMyId(), getContext().getValidationErrors())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail").addParameter("projectId", projectId);
    }

    @HandlesEvent("markAsFinished")
    @RolesAllowed({"project_team_member if ${allowedProjectExecutor}"})
    public Resolution markAsFinished() {
        if (!projectFacade.markAsFinished(projectId, getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "detail").addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "detail").addParameter("projectId", projectId);
    }

    @DontValidate
    @HandlesEvent("sampleRequests")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectVisitor}"})
    public Resolution sampleRequests() {
        return new ForwardResolution(PROJECT_DETAIL_SAMPLE_REQUESTS);
    }

}
