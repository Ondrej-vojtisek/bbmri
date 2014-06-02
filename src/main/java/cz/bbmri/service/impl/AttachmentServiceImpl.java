package cz.bbmri.service.impl;

import cz.bbmri.dao.AttachmentDao;
import cz.bbmri.dao.BiobankDao;
import cz.bbmri.dao.NotificationDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.entities.*;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.enumeration.BiobankAttachmentType;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.ProjectAttachmentType;
import cz.bbmri.service.AttachmentService;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.LocalizableMessage;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.validation.LocalizableError;
import net.sourceforge.stripes.validation.ValidationErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */
@Transactional
@Service("attachmentService")
public class AttachmentServiceImpl extends BasicServiceImpl implements AttachmentService {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private AttachmentDao attachmentDao;

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private BiobankDao biobankDao;

    @Transactional(readOnly = true)
    Attachment get(Long id) {
        notNull(id);
        return attachmentDao.get(id);
    }

    public int createBiobankAttachment(FileBean fileBean,
                                       BiobankAttachmentType biobankAttachmentType,
                                       Long biobankId,
                                       ValidationErrors errors,
                                       Long loggedUserId) {
        // Null errors will throw exception
        notNull(errors);

        // Null fields will inform user
        if (isNull(fileBean, "fileBean", errors)) return Constant.NOT_SUCCESS;
        if (isNull(biobankAttachmentType, "biobankAttachmentType", errors)) return Constant.NOT_SUCCESS;
        if (isNull(biobankId, "biobankId", errors)) return Constant.NOT_SUCCESS;
        if (isNull(loggedUserId, "loggedUserId", errors)) return Constant.NOT_SUCCESS;

        Biobank biobankDB = biobankDao.get(biobankId);

        if (isNull(biobankDB, "biobankDB", errors)) return Constant.NOT_SUCCESS;

        // create folder structure if it doesn't exist
        int result = ServiceUtils.createFolders(errors,
                storagePath,
                storagePath + Biobank.BIOBANK_FOLDER, // Biobanks folder
                storagePath + biobankDB.getBiobankFolderPath(), // Folder for the biobank
                storagePath + biobankDB.getBiobankCalibrationDataFolder()   // Folder for the biobank/calibration_data
        );
        // problem during folder creation
        if (result != Constant.SUCCESS) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.CantCreateFolderStructure"));
            return Constant.NOT_SUCCESS;
        }

        BiobankAttachment biobankAttachment = new BiobankAttachment();
        biobankAttachment.setFileName(fileBean.getFileName());
        biobankAttachment.setContentType(fileBean.getContentType());
        biobankAttachment.setSize(fileBean.getSize());
        biobankAttachment.setBiobankAttachmentType(biobankAttachmentType);
        biobankAttachment.setBiobank(biobankDB);
        biobankAttachment.setAbsolutePath(
                storagePath +
                        biobankDB.getBiobankCalibrationDataFolder() +
                        File.separator +
                        fileBean.getFileName());

        if (createFile(fileBean, biobankAttachment.getAbsolutePath(), errors, biobankAttachment) != Constant.SUCCESS) {
            return Constant.NOT_SUCCESS;
        }

        // notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.service.impl.AttachmentServiceImpl.fileUploaded",
                biobankDB.getAbbreviation());

        // send notification to all other project workers
        notificationDao.create(getOtherBiobankAdministrators(biobankDB, loggedUserId),
                NotificationType.BIOBANK_DETAIL, locMsg, biobankDB.getId());

        // Archive message
        archive("New file was uploaded to biobank: " + biobankDB.getAbbreviation(), loggedUserId);

        return Constant.SUCCESS;

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

        try {
            attachmentDao.create(attachment);
        } catch (DataAccessException ex) {
            // info for user
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.FileUploadedButDatabaseRecordNotCreated"));
            // info for developer
            operationFailed(null, ex);
            return Constant.NOT_SUCCESS;
        }


        return Constant.SUCCESS;
    }

    public int createProjectAttachment(FileBean fileBean,
                                       ProjectAttachmentType projectAttachmentType,
                                       Long projectId,
                                       ValidationErrors errors,
                                       Long loggedUserId) {
        // Null errors will throw exception
        notNull(errors);

        // Null fields will inform user
        if (isNull(fileBean, "fileBean", errors)) return Constant.NOT_SUCCESS;
        if (isNull(projectAttachmentType, "projectAttachmentType", errors)) return Constant.NOT_SUCCESS;
        if (isNull(projectId, "projectId", errors)) return Constant.NOT_SUCCESS;
        if (isNull(loggedUserId, "loggedUserId", errors)) return Constant.NOT_SUCCESS;

        Project projectDB = projectDao.get(projectId);

        if (isNull(projectDB, "projectDB", errors)) return Constant.NOT_SUCCESS;

        // create folder structure if it doesn't exist
        int result = ServiceUtils.createFolders(errors,
                storagePath, // base folder
                storagePath + Project.PROJECT_FOLDER, // Projects folder
                storagePath + projectDB.getProjectFolderPath() // Folder for the project
        );
        // problem during folder creation
        if (result != Constant.SUCCESS) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.CantCreateFolderStructure"));
            return Constant.NOT_SUCCESS;
        }

        // Create new attachment
        ProjectAttachment projectAttachment = new ProjectAttachment();
        projectAttachment.setFileName(fileBean.getFileName());
        projectAttachment.setContentType(fileBean.getContentType());
        projectAttachment.setSize(fileBean.getSize());
        projectAttachment.setProjectAttachmentType(projectAttachmentType);
        projectAttachment.setProject(projectDB);
        projectAttachment.setAbsolutePath(
                storagePath +
                        projectDB.getProjectFolderPath() +
                        File.separator +
                        fileBean.getFileName());

        // if not success than end execution
        if (createFile(fileBean, projectAttachment.getAbsolutePath(), errors, projectAttachment) != Constant.SUCCESS) {
            return Constant.NOT_SUCCESS;
        }

        // notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.service.impl.AttachmentServiceImpl.fileUploadedProject",
                projectDB.getName());

        // send notification to all other project workers
        notificationDao.create(getOtherProjectWorkers(projectDB, loggedUserId),
                NotificationType.PROJECT_ATTACHMENT, locMsg, projectDB.getId());

        // Archive message
        archive("New file was uploaded to project: " + projectDB.getName(), loggedUserId);

        return Constant.SUCCESS;
    }

    public Attachment update(Attachment attachment) {
        if (isNull(attachment, "attachment", null)) return null;

        Attachment attachmentDB = attachmentDao.getAttachmentByPath(attachment.getAbsolutePath());
        // get object from db with the same identifier
        isNull(attachmentDB, "attachmentDB", null);

        if (attachment.getContentType() != null) {
            attachmentDB.setContentType(attachment.getContentType());
        }
        if (attachment.getFileName() != null) {
            attachmentDB.setFileName(attachment.getFileName());
        }
        if (attachment.getSize() != null) {
            attachmentDB.setSize(attachment.getSize());
        }

        // store
        attachmentDao.update(attachmentDB);

        return attachmentDB;
    }

    @Transactional(readOnly = true)
    public List<ProjectAttachment> getSortedProjectAttachments(Long projectId, String orderByParam, boolean desc) {
        if (isNull(projectId, "projectId", null)) return null;

        try {
            return attachmentDao.getAttachmentSorted(projectDao.get(projectId), orderByParam, desc);
        } catch (DataAccessException ex) {
            operationFailed(null, ex);
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<BiobankAttachment> getSortedBiobankAttachments(Long biobankId, String orderByParam, boolean desc) {
        if (isNull(biobankId, "biobankId", null)) return null;

        try {
            return attachmentDao.getAttachmentSorted(biobankDao.get(biobankId), orderByParam, desc);
        } catch (DataAccessException ex) {
            operationFailed(null, ex);
            return null;
        }
    }


    public boolean deleteAttachment(Long attachmentId, ValidationErrors errors, Long loggedUserId) {
        // Null errors will throw exception
        notNull(errors);

        // Null fields will inform user
        if (isNull(attachmentId, "attachmentId", errors)) return false;
        if (isNull(loggedUserId, "loggedUserId", errors)) return false;

        Attachment attachment = attachmentDao.get(attachmentId);
        if (isNull(attachment, "attachment", errors)) return false;

        // Delete file and in situation of last file in folder delete also the folder
        if (ServiceUtils.deleteFileAndParentFolder(attachment.getAbsolutePath(), errors) != Constant.SUCCESS) {
            // not success
            return false;
        }

        if (attachment instanceof ProjectAttachment) {
            Project project = ((ProjectAttachment) attachment).getProject();

            // message for project administrators

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.service.impl.AttachmentServiceImpl.attachmentDeleted",
                    attachment.getFileName(), project.getName());

            notificationDao.create(getOtherProjectWorkers(project, loggedUserId),
                    NotificationType.PROJECT_ATTACHMENT, locMsg, project.getId());

            // Archive message
            archive("Attachment of project: " + project.getName() + " was deleted!", loggedUserId);
        }

        if (attachment instanceof BiobankAttachment) {
            Biobank biobank = ((BiobankAttachment) attachment).getBiobank();

            // message for project administrators

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.service.impl.AttachmentServiceImpl.attachmentDeleted",
                    attachment.getFileName(), biobank.getAbbreviation());

            notificationDao.create(getOtherBiobankAdministrators(biobank, loggedUserId),
                    NotificationType.BIOBANK_ATTACHMENT, locMsg, biobank.getId());

            // Archive message
            archive("Attachment of biobank: " + biobank.getAbbreviation() + " was deleted!", loggedUserId);
        }

        // message for biobank administrators

        try {
            // remove record in DB about attachment
            attachmentDao.remove(attachment);
        } catch (DataAccessException ex) {
            operationFailed(errors, ex);
            return false;
        }

        return true;


    }

    @Transactional(readOnly = true)
    public StreamingResolution downloadFile(Long attachmentId) throws FileNotFoundException {
        notNull(attachmentId);

        Attachment attachment = get(attachmentId);
        if (isNull(attachment, "attachment", null)) return null;

        FileInputStream fis = new FileInputStream(attachment.getAbsolutePath());
        return new StreamingResolution(attachment.getContentType(), fis).setFilename(attachment.getFileName());
    }


}
