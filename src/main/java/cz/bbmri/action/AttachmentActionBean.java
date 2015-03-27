package cz.bbmri.action;

import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
import cz.bbmri.dao.impl.BiobankUserDAOImpl;
import cz.bbmri.dao.impl.ProjectUserDAOImpl;
import cz.bbmri.entity.*;
import cz.bbmri.entity.constant.Constant;
import cz.bbmri.entity.webEntities.ComponentManager;
import cz.bbmri.io.FileUtils;
import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;

import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * TODO describe class
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@UrlBinding("/attachment/{$event}/{id}")
public class AttachmentActionBean extends ComponentActionBean {

    @SpringBean
    private AttachmentDAO attachmentDAO;

    @SpringBean
    private NotificationDAO notificationDAO;

    @SpringBean
    private AttachmentTypeDAO attachmentTypeDAO;

    @SpringBean
    private BiobankDAO biobankDAO;

    @SpringBean
    private ProjectDAO projectDAO;

    private String getStoragePath(){
        Properties properties = getContext().getProperties("path");
        return properties.getProperty("path.storage");
    }

    private Long id;

    private Attachment attachment;

    private FileBean fileBean;

    private Integer attachmentTypeId;

    private Integer biobankId;

    private Long projectId;

    private Biobank biobank;

    public Attachment getAttachment() {
        if (attachment == null) {
            if (id != null) {
                attachment = attachmentDAO.get(id);
            }
        }

        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FileBean getFileBean() {
        return fileBean;
    }

    public void setFileBean(FileBean fileBean) {
        this.fileBean = fileBean;
    }

    public Integer getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(Integer attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }

    public Integer getBiobankId() {
        return biobankId;
    }

    public void setBiobankId(Integer biobankId) {
        this.biobankId = biobankId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @HandlesEvent("addBiobankAttachment")
    @RolesAllowed({"biobank_operator", "developer"})
    public Resolution addBiobankAttachment() {

        return new ForwardResolution(View.Attachment.ADD_BIOBANK_ATTACHMENT);

    }

    @HandlesEvent("addProjectAttachment")
    @RolesAllowed({"biobank_operator", "developer"})
    public Resolution addProjectAttachment() {

        return new ForwardResolution(View.Attachment.ADD_PROJECT_ATTACHMENT);

    }


    @HandlesEvent("download")
    @RolesAllowed({"biobank_operator", "developer"})
    public StreamingResolution download() {
        getAttachment();

        try {
            FileInputStream fis = new FileInputStream(attachment.getAbsolutePath());
            return new StreamingResolution(attachment.getContentType(), fis).setFilename(attachment.getFileName());
        } catch (FileNotFoundException ex) {
            getContext().getMessages().add(
                    new SimpleMessage("File does not exist."));
        }
        return null;
    }


    @HandlesEvent("delete")
    @RolesAllowed({"biobank_operator", "developer"})
    public Resolution delete() {
        getAttachment();

        if (attachment == null) {
            return new ForwardResolution(View.Attachment.NOTFOUND);
        }

        if (attachment.getProject() != null) {
            Long projectId = attachment.getProject().getId();
            deleteProjectAttachment();
            attachmentDAO.remove(attachment);
            return new RedirectResolution(ProjectActionBean.class, "attachments").addParameter("id", projectId);
        }

        if (attachment.getBiobank() != null) {
            Integer biobankId = attachment.getBiobank().getId();
            deleteBiobankAttachment();
            attachmentDAO.remove(attachment);
            return new RedirectResolution(BiobankActionBean.class, "attachments").addParameter("id", biobankId);
        }

        return new ForwardResolution(View.Attachment.NOTFOUND);
    }

    private void deleteProjectAttachment() {
        Project project = attachment.getProject();

        // Delete file and in situation of last file in folder delete also the folder
        if (FileUtils.deleteFileAndParentFolder(attachment.getAbsolutePath(), getContext().getValidationErrors()) != Constant.SUCCESS) {
            return;
        }

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.service.impl.AttachmentServiceImpl.attachmentDeleted",
                attachment.getFileName(), project.getName());

        notificationDAO.create(ProjectUserDAOImpl.getOtherProjectUsers(project, getLoggedUser()),
                NotificationType.PROJECT_ATTACHMENT, locMsg, project.getId());

        // Archive message
        //archive("Attachment of project: " + project.getName() + " was deleted!", loggedUserId);

    }

    private void deleteBiobankAttachment() {
        Biobank biobank = attachment.getBiobank();

        // Delete file and in situation of last file in folder delete also the folder
        if (FileUtils.deleteFileAndParentFolder(attachment.getAbsolutePath(), getContext().getValidationErrors()) != Constant.SUCCESS) {
            return;
        }

        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.service.impl.AttachmentServiceImpl.attachmentDeleted",
                attachment.getFileName(), biobank.getAcronym());


        notificationDAO.create(BiobankUserDAOImpl.getOtherBiobankUsers(biobank, getLoggedUser()),
                NotificationType.BIOBANK_ATTACHMENT, locMsg, (long) biobank.getId());

        // Archive message
        //archive("Attachment of project: " + project.getName() + " was deleted!", loggedUserId);
    }

    @HandlesEvent("biobankAttachmentUpload")
    @RolesAllowed({"biobank_operator", "developer"})
    public Resolution biobankAttachmentUpload() {

        Biobank biobank = biobankDAO.get(biobankId);

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        AttachmentType attachmentType = attachmentTypeDAO.get(attachmentTypeId);

        System.err.println("AttachmentType: " + attachmentType);

        if (attachmentType == null) {
            return new ForwardResolution(View.AttachmentType.NOTFOUND);
        }

        String storagePath = getStoragePath();

        int result = FileUtils.createFolders(getContext().getValidationErrors(),
                storagePath,
                storagePath + Biobank.BIOBANK_FOLDER, // Biobanks folder
                storagePath + biobank.getBiobankFolderPath(), // Folder for the biobank
                storagePath + biobank.getBiobankCalibrationDataFolder()   // Folder for the biobank/calibration_data
        );

        // problem during folder creation
        if (result != Constant.SUCCESS) {
            return new ForwardResolution(View.Biobank.ATTACHMENTS).addParameter("id", biobankId);
        }

        Attachment attachment = new Attachment();
        attachment.setFileName(fileBean.getFileName());
        attachment.setContentType(fileBean.getContentType());
        attachment.setSize(fileBean.getSize());
        attachment.setAttachmentType(attachmentType);
        attachment.setBiobank(biobank);
        attachment.setAbsolutePath(
                storagePath +
                        biobank.getBiobankCalibrationDataFolder() +
                        File.separator +
                        fileBean.getFileName());

        if (createFile(fileBean, attachment.getAbsolutePath(), getContext().getValidationErrors(), attachment) != Constant.SUCCESS) {
            logger.debug("CreateFile failed");
            return new ForwardResolution(View.Biobank.ATTACHMENTS).addParameter("id", biobankId);
        }

        attachmentDAO.save(attachment);

        // notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.service.impl.AttachmentServiceImpl.fileUploaded",
                biobank.getAcronym());

        // send notification to all other project workers
        notificationDAO.create(BiobankUserDAOImpl.getOtherBiobankUsers(biobank, getLoggedUser()),
                NotificationType.BIOBANK_DETAIL, locMsg, (long) biobank.getId());

        // Archive message
//        archive("New file was uploaded to biobank: " + biobankDB.getAbbreviation(), loggedUserId);

        return new RedirectResolution(BiobankActionBean.class, "attachments").addParameter("id", biobankId);
    }

    private int createFile(FileBean fileBean, String path, ValidationErrors errors, Attachment attachment) {
        File file = new File(path);

        if (file.exists()) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.service.impl.AttachmentServiceImpl.fileAlreadyExist"));
            return Constant.NOT_SUCCESS;
        }

        try {
            // upload data to server
            fileBean.save(file);
        } catch (IOException e) {
            // info for user
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.IOException"));
            // info for developer
            operationFailed(null, e);
            return Constant.NOT_SUCCESS;
        }

        return Constant.SUCCESS;
    }

}
