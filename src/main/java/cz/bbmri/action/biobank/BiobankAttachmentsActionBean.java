package cz.bbmri.action.biobank;

import cz.bbmri.action.base.PermissionActionBean;
import cz.bbmri.entities.BiobankAttachment;
import cz.bbmri.entities.enumeration.BiobankAttachmentType;
import cz.bbmri.entities.enumeration.SystemRole;
import cz.bbmri.entities.webEntities.Breadcrumb;
import cz.bbmri.entities.webEntities.ComponentManager;
import cz.bbmri.entities.webEntities.MyPagedListHolder;
import cz.bbmri.service.AttachmentService;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import javax.annotation.security.RolesAllowed;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/biobank/attachments/{$event}/{biobankId}")
public class BiobankAttachmentsActionBean extends PermissionActionBean<BiobankAttachment> {

    @SpringBean
    private AttachmentService attachmentService;

    public static Breadcrumb getBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(BiobankAttachmentsActionBean.class.getName(),
                "attachmentsResolution", false, "cz.bbmri.action.biobank.BiobankActionBean.attachments",
                active, "biobankId", biobankId);
    }

    private static Breadcrumb getAddAttachmentBreadcrumb(boolean active, Long biobankId) {
        return new Breadcrumb(BiobankAttachmentsActionBean.class.getName(),
                "addAttachment", false, "cz.bbmri.action.biobank.BiobankAttachmentsActionBean.addAttachment",
                active, "biobankId", biobankId);
    }

    public BiobankAttachmentsActionBean() {

        setPagination(new MyPagedListHolder<BiobankAttachment>(new ArrayList<BiobankAttachment>()));
        // ribbon
        setComponentManager(new ComponentManager(
                ComponentManager.BIOBANK_ATTACHMENT_DETAIL,
                ComponentManager.BIOBANK_DETAIL));
        getPagination().setIdentifierParam("biobankId");
    }

    private BiobankAttachment biobankAttachment;

    private Long attachmentId;


    private FileBean attachmentFileBean;

    private BiobankAttachmentType biobankAttachmentType;

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public BiobankAttachment getBiobankAttachment() {
        return biobankAttachment;
    }

    public void setBiobankAttachment(BiobankAttachment biobankAttachment) {
        this.biobankAttachment = biobankAttachment;
    }

    public FileBean getAttachmentFileBean() {
        return attachmentFileBean;
    }

    public void setAttachmentFileBean(FileBean attachmentFileBean) {
        this.attachmentFileBean = attachmentFileBean;
    }

    public BiobankAttachmentType getBiobankAttachmentType() {
        return biobankAttachmentType;
    }

    public void setBiobankAttachmentType(BiobankAttachmentType biobankAttachmentType) {
        this.biobankAttachmentType = biobankAttachmentType;
    }

    @DontValidate
    @DefaultHandler
    @HandlesEvent("attachmentsResolution")
    @RolesAllowed({"administrator", "developer", "biobank_operator if ${allowedBiobankVisitor}", "project_team_member_confirmed"})
    public Resolution attachmentsResolution() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankAttachmentsActionBean.getBreadcrumb(true, biobankId));

        if (biobankId != null) {
            getPagination().setIdentifier(biobankId);
        }

        initiatePagination();
        if (getOrderParam() == null) {
            // default
            getPagination().setOrderParam("fileName");
        }

        getPagination().setEvent("attachmentsResolution");
        // Administrators are stored in Set
        getPagination().setSource(attachmentService.getSortedBiobankAttachments(
                biobankId,
                getPagination().getOrderParam(),
                getPagination().getDesc()));
        return new ForwardResolution(BIOBANK_DETAIL_ATTACHMENTS).addParameter("biobankId", biobankId);
    }

    @HandlesEvent("downloadAttachment")
    @RolesAllowed({"administrator", "developer", "project_team_member_confirmed",
            "biobank_operator if ${allowedBiobankVisitor}"})
    public Resolution downloadAttachment() {

        logger.debug("Id " + biobankAttachment.getId());

        try {
            return attachmentService.downloadFile(biobankAttachment.getId());
        } catch (FileNotFoundException ex) {
            getContext().getMessages().add(
                    new SimpleMessage("File does not exist.")

            );
            return new ForwardResolution(this.getClass(), "attachmentsResolution");
        }
    }

    @HandlesEvent("deleteAttachment")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution deleteAttachment() {
        if (!attachmentService.deleteAttachment(attachmentId,
                getContext().getValidationErrors(),
                getContext().getMyId())) {
            return new ForwardResolution(this.getClass(), "attachmentsResolution").addParameter("biobankId", biobankId);
        }
        successMsg();
        return new RedirectResolution(this.getClass(), "attachmentsResolution").addParameter("biobankId", biobankId);
    }

    @DontValidate
    @HandlesEvent("addAttachment")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution addAttachment() {

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobankId, getBiobank()));
        getBreadcrumbs().add(BiobankAttachmentsActionBean.getBreadcrumb(false, projectId));
        getBreadcrumbs().add(BiobankAttachmentsActionBean.getAddAttachmentBreadcrumb(true, projectId));

        if (biobankId != null) {
            getPagination().setIdentifier(biobankId);
        }

        initiatePagination();
        if (getOrderParam() == null) {
            // default
            getPagination().setOrderParam("fileName");
        }
        getPagination().setEvent("addAttachment");
        getPagination().setSource(attachmentService.getSortedBiobankAttachments(
                biobankId,
                getPagination().getOrderParam(),
                getPagination().getDesc()));

        return new ForwardResolution(BIOBANK_DETAIL_ATTACHMENT_ADD);
    }

    @HandlesEvent("attachmentUpload")
    @RolesAllowed({"biobank_operator if ${allowedBiobankExecutor}"})
    public Resolution attachmentUpload() {

        int result = attachmentService.createBiobankAttachment(attachmentFileBean,
                biobankAttachmentType, biobankId,
                getContext().getValidationErrors(),
                getContext().getMyId());

        if (result < 0) {
            return new ForwardResolution(this.getClass(), "addAttachment").addParameter("biobankId", biobankId);
        }

        if (result == 1) {
            getContext().getMessages().add(new LocalizableMessage("cz.bbmri.action.CreateProjectActionBean.AttachmentOverwritten"));
        }

        if (result == 0) {
            successMsg();
        }

        return new RedirectResolution(this.getClass(), "attachmentsResolution").addParameter("biobankId", biobankId);
    }

}
