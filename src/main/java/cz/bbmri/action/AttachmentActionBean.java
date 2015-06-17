package cz.bbmri.action;

import cz.bbmri.action.base.AuthorizationActionBean;
import cz.bbmri.action.base.ComponentActionBean;
import cz.bbmri.action.map.View;
import cz.bbmri.dao.*;
import cz.bbmri.dao.impl.BiobankUserDAOImpl;
import cz.bbmri.dao.impl.ProjectUserDAOImpl;
import cz.bbmri.entity.*;
import cz.bbmri.entity.constant.Constant;
import cz.bbmri.entity.webEntities.Breadcrumb;
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
public class AttachmentActionBean extends AuthorizationActionBean {

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

    private String getStoragePath() {
        Properties properties = getContext().getProperties("path");
        return properties.getProperty("path.storage");
    }

    private Long id;

    private Attachment attachment;

    private FileBean fileBean;

    private Integer attachmentTypeId;

    private Integer biobankId;

    private Long projectId;

    public static Breadcrumb getAddBiobankAttachmentBreadcrumb(boolean active, Biobank biobank) {
        return new Breadcrumb(AttachmentActionBean.class.getName(), "addBiobankAttachment", false, "cz.bbmri.action.AttachmentActionBean.addBiobankAttachment",
                active, "biobankId", biobank.getId());
    }

    public static Breadcrumb getAddProjectAttachmentBreadcrumb(boolean active, Project project) {
        return new Breadcrumb(AttachmentActionBean.class.getName(), "addProjectAttachment", false, "cz.bbmri.action.AttachmentActionBean.addProjectAttachment",
                active, "projectId", project.getId());
    }

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
        setAuthBiobankId(biobankId);
        this.biobankId = biobankId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        setAuthProjectId(projectId);
        this.projectId = projectId;
    }

    @HandlesEvent("addBiobankAttachment")
    @RolesAllowed({"biobank_operator if ${biobankEditor}", "developer"})
    public Resolution addBiobankAttachment() {
        id = (long) biobankId;

        Biobank biobank = biobankDAO.get(biobankId);

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        getBreadcrumbs().add(BiobankActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(BiobankActionBean.getDetailBreadcrumb(false, biobank));
        getBreadcrumbs().add(BiobankActionBean.getAttachmentsBreadcrumb(false, biobank));
        getBreadcrumbs().add(AttachmentActionBean.getAddBiobankAttachmentBreadcrumb(true, biobank));

        // notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.AttachmentActionBean.fileUploaded",
                biobank.getAcronym());

        notificationDAO.create(biobank.getOtherBiobankUser(getLoggedUser()),
                NotificationType.BIOBANK_DETAIL, locMsg, new Long(biobank.getId()));

        return new ForwardResolution(View.Attachment.ADD_BIOBANK_ATTACHMENT);

    }

    @HandlesEvent("addProjectAttachment")
    @RolesAllowed({"project_team_member if ${projectEditor}", "developer"})
    public Resolution addProjectAttachment() {
        id = projectId;

        Project project = projectDAO.get(projectId);

        if (project == null) {
            return new ForwardResolution(View.Project.NOTFOUND);
        }

        getBreadcrumbs().add(ProjectActionBean.getAllBreadcrumb(false));
        getBreadcrumbs().add(ProjectActionBean.getDetailBreadcrumb(false, project));
        getBreadcrumbs().add(ProjectActionBean.getAttachmentsBreadcrumb(false, project));
        getBreadcrumbs().add(AttachmentActionBean.getAddProjectAttachmentBreadcrumb(true, project));

        // notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.AttachmentActionBean.fileUploadedProject",
                project.getName());

        // send notification to all other project workers
        notificationDAO.create(project.getOtherProjectUser(getLoggedUser()),
                NotificationType.PROJECT_ATTACHMENT, locMsg, project.getId());

        return new ForwardResolution(View.Attachment.ADD_PROJECT_ATTACHMENT);

    }


    @HandlesEvent("download")
    @RolesAllowed({"project_team_member if ${projectVisitor}", "biobank_operator if ${biobankVisitor}", "admin", "developer"})
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
    @RolesAllowed({"project_team_member if ${projectEditor}", "biobank_operator if ${biobankEditor}"})
    public Resolution delete() {
        getAttachment();

        if (attachment == null) {
            return new ForwardResolution(View.Attachment.NOTFOUND);
        }

        if (attachment.getProject() != null) {
            Long projectId = attachment.getProject().getId();
            deleteProjectAttachment();

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.AttachmentActionBean.attachmentDeleted",
                    attachment.getFileName(), projectId);

            notificationDAO.create(attachment.getProject().getOtherProjectUser(getLoggedUser()),
                    NotificationType.PROJECT_ATTACHMENT, locMsg, projectId);

            attachmentDAO.remove(attachment);
            return new RedirectResolution(ProjectActionBean.class, "attachments").addParameter("id", projectId);
        }

        if (attachment.getBiobank() != null) {
            Integer biobankId = attachment.getBiobank().getId();
            deleteBiobankAttachment();

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.action.AttachmentActionBean.attachmentDeleted",
                    attachment.getFileName(), biobankId);

            notificationDAO.create(attachment.getBiobank().getOtherBiobankUser(getLoggedUser()),
                    NotificationType.BIOBANK_ATTACHMENT, locMsg, new Long(biobankId));

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
    @RolesAllowed({"biobank_operator if ${biobankEditor}"})
    public Resolution biobankAttachmentUpload() {

        Biobank biobank = biobankDAO.get(biobankId);

        if (biobank == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        AttachmentType attachmentType = attachmentTypeDAO.get(attachmentTypeId);

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

    @HandlesEvent("projectAttachmentUpload")
    @RolesAllowed({"project_team_member if ${projectEditor}"})
    public Resolution projectAttachmentUpload() {

        Project project = projectDAO.get(projectId);

        if (project == null) {
            return new ForwardResolution(View.Biobank.NOTFOUND);
        }

        AttachmentType attachmentType = attachmentTypeDAO.get(attachmentTypeId);

        if (attachmentType == null) {
            return new ForwardResolution(View.AttachmentType.NOTFOUND);
        }

        String storagePath = getStoragePath();

        int result = FileUtils.createFolders(getContext().getValidationErrors(),
                storagePath, // base folder
                storagePath + Project.PROJECT_FOLDER, // Projects folder
                storagePath + project.getProjectFolderPath() // Folder for the project
        );

        // problem during folder creation
        if (result != Constant.SUCCESS) {
            return new ForwardResolution(View.Project.ATTACHMENTS).addParameter("id", projectId);
        }

        Attachment attachment = new Attachment();
        attachment.setFileName(fileBean.getFileName());
        attachment.setContentType(fileBean.getContentType());
        attachment.setSize(fileBean.getSize());
        attachment.setAttachmentType(attachmentType);
        attachment.setProject(project);
        attachment.setAbsolutePath(
                storagePath +
                        project.getProjectFolderPath() +
                        File.separator +
                        fileBean.getFileName());

        if (createFile(fileBean, attachment.getAbsolutePath(), getContext().getValidationErrors(), attachment) != Constant.SUCCESS) {
            logger.debug("CreateFile failed");
            return new ForwardResolution(View.Project.ATTACHMENTS).addParameter("id", projectId);
        }

        attachmentDAO.save(attachment);

        // notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.service.impl.AttachmentServiceImpl.fileUploaded",
                project.getName());

        // send notification to all other project workers
        notificationDAO.create(ProjectUserDAOImpl.getOtherProjectUsers(project, getLoggedUser()),
                NotificationType.PROJECT_DETAIL, locMsg, project.getId());

        // Archive message
        //        archive("New file was uploaded to biobank: " + biobankDB.getAbbreviation(), loggedUserId);

        return new RedirectResolution(ProjectActionBean.class, "attachments").addParameter("id", projectId);
    }


}
