package cz.bbmri.action.project;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.enumeration.AttachmentType;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.facade.ProjectFacade;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 12.3.14
 * Time: 9:32
 * To change this template use File | Settings | File Templates.
 */
@UrlBinding("/project/attachments/{$event}/{projectId}")
public class ProjectAttachmentsActionBean extends PermissionActionBean<Attachment> {

    @SpringBean
    private ProjectFacade projectFacade;

    public ProjectAttachmentsActionBean() {
        setPagination(new MyPagedListHolder<Attachment>(new ArrayList<Attachment>()));
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.ATTACHMENT_DETAIL,
                ComponentManager.PROJECT_DETAIL));
        getPagination().setIdentifierParam("projectId");
    }

    private Attachment attachment;

    private Long attachmentId;

    /* Attachment upload in project detail/edit view */
    private FileBean attachmentFileBean;

    /* Attachment upload in project detail/edit view
     * Important to specify what is the purpose of uploaded file
      * */
    private AttachmentType attachmentType;

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
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

    @DontValidate
    @DefaultHandler
    @HandlesEvent("attachmentsResolution")
    @RolesAllowed({"administrator", "developer", "project_team_member if ${allowedProjectVisitor}"})
    public Resolution attachmentsResolution() {
        if (projectId != null) {
            getPagination().setIdentifier(projectId);
        }

        initiatePagination();
        if (getOrderParam() == null) {
            // default
            getPagination().setOrderParam("fileName");
        }
        getPagination().setEvent("attachmentsResolution");
        getPagination().setSource(projectFacade.getSortedAttachments(
                projectId,
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(PROJECT_DETAIL_ATTACHMENTS);
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
            return new ForwardResolution(this.getClass(), "attachmentsResolution");
        }
    }

    @HandlesEvent("deleteAttachment")
    @RolesAllowed({"project_team_member if ${allowedProjectEditor}"})
    public Resolution deleteAttachment() {
        if (!projectFacade.deleteAttachment(attachmentId, getContext().getValidationErrors(), getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "attachmentsResolution").addParameter("projectId", projectId);
        }
        successMsg(null);
        return new RedirectResolution(this.getClass(), "attachmentsResolution").addParameter("projectId", projectId);
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

        return new RedirectResolution(this.getClass(), "attachmentsResolution").addParameter("projectId", projectId);
    }
}
