package cz.bbmri.service.impl;

import cz.bbmri.dao.*;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.BiobankAdministrator;
import cz.bbmri.entities.Project;
import cz.bbmri.entities.constant.Constant;
import cz.bbmri.entities.enumeration.AttachmentType;
import cz.bbmri.entities.enumeration.NotificationType;
import cz.bbmri.entities.enumeration.Permission;
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
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
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
    private BiobankAdministratorDao biobankAdministratorDao;

    @Autowired
    private BiobankDao biobankDao;

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    public Attachment get(Long id) {
        notNull(id);
        return attachmentDao.get(id);
    }

    public int createAttachment(FileBean fileBean,
                                AttachmentType attachmentType,
                                Long projectId,
                                ValidationErrors errors,
                                Long loggedUserId) {
        // Null errors will throw exception
        notNull(errors);

        // Null fields will inform user
        if (isNull(fileBean, "fileBean", errors)) return Constant.NOT_SUCCESS;
        if (isNull(attachmentType, "attachmentType", errors)) return Constant.NOT_SUCCESS;
        if (isNull(projectId, "projectId", errors)) return Constant.NOT_SUCCESS;
        if (isNull(loggedUserId, "loggedUserId", errors)) return Constant.NOT_SUCCESS;

        Project projectDB = projectDao.get(projectId);

        if (projectDB == null) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.ProjectDoesntExist"));
            return Constant.NOT_SUCCESS;
        }
        // Create folder structure of projectDB
        if (!createFolderStructure(projectDB, errors)) {
            errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.CantCreateFolderStructure"));
            return Constant.NOT_SUCCESS;
        }

        // Create new attachment
        Attachment attachment = new Attachment();
        attachment.setFileName(fileBean.getFileName());
        attachment.setContentType(fileBean.getContentType());
        attachment.setSize(fileBean.getSize());
        attachment.setAttachmentType(attachmentType);
        attachment.setProject(projectDB);
        attachment.setAbsolutePath(
                storagePath +
                projectDB.getProjectFolderPath() +
                File.separator +
                fileBean.getFileName());

        // Create file on server
        File file = new File(attachment.getAbsolutePath());

        boolean overwrite = false;

        if (file.exists()) {
            // file exists and will be overwriten
            overwrite = true;
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

        //      Create DB record only if file is new
        if (!overwrite) {

            try {
                attachmentDao.create(attachment);
            } catch (DataAccessException ex) {
                // info for user
                errors.addGlobalError(new LocalizableError("cz.bbmri.facade.impl.ProjectFacadeImpl.FileUploadedButDatabaseRecordNotCreated"));
                // info for developer
                operationFailed(null, ex);
                return Constant.NOT_SUCCESS;
            }
        } else {
            // File is not new - it is enough to update
            update(attachment);
        }

        // notification
        LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.attachmentUploaded",
                projectDB.getName());

        notificationDao.create(getOtherProjectWorkers(projectDB, loggedUserId),
                NotificationType.PROJECT_ATTACHMENT, locMsg, projectDB.getId());

        // return different value to distinguish on actionBean between success and overwrite
        if (overwrite) {
            return Constant.OVERWRITTEN;
        }
        return Constant.SUCCESS;
    }


    @Transactional(readOnly = true)
    public List<Attachment> all() {
        return attachmentDao.all();
    }

    public boolean hasPermission(Permission permission, Long objectId, Long userId) {
        if(isNull(permission, "permission", null)) return false;
        if(isNull(objectId, "objectId", null)) return false;
        if(isNull(userId, "userId", null)) return false;

        BiobankAdministrator ba = biobankAdministratorDao.get(biobankDao.get(objectId), userDao.get(userId));

        if (ba == null) {
            return false;
        }

        return ba.getPermission().include(permission);
    }


    public Attachment update(Attachment attachment) {
        if(isNull(attachment, "attachment", null)) return null;

        Attachment attachmentDB = attachmentDao.getAttachmentByPath(attachment.getAbsolutePath());
        // get object from db with the same identifier
        isNull(attachmentDB, "attachmentDB", null);

        if (attachment.getAttachmentType() != null) {
            attachmentDB.setAttachmentType(attachment.getAttachmentType());
        }
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
    public Integer count() {
        return attachmentDao.count();
    }

    @Transactional(readOnly = true)
    public List<Attachment> getAttachmentsByProject(Long projectId) {
        if(isNull(projectId, "projectId", null)) return null;
        return attachmentDao.getAttachmentsByProject(projectDao.get(projectId));
    }

    @Transactional(readOnly = true)
    public List<Attachment> getSortedAttachments(Long projectId, String orderByParam, boolean desc) {
        if(isNull(projectId, "projectId", null)) return null;

        try {
            return attachmentDao.getAttachmentSorted(projectDao.get(projectId), orderByParam, desc);
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
        if(isNull(attachment, "attachment", errors)) return false;

        if (ServiceUtils.deleteFileAndParentFolder(attachment.getAbsolutePath(), errors) == Constant.SUCCESS) {

            Project project = attachment.getProject();

            LocalizableMessage locMsg = new LocalizableMessage("cz.bbmri.facade.impl.ProjectFacadeImpl.attachmentDeleted",
                    attachment.getFileName(), project.getName());

            notificationDao.create(getOtherProjectWorkers(project, loggedUserId),
                    NotificationType.PROJECT_ATTACHMENT, locMsg, project.getId());

            try {
                attachmentDao.remove(attachment);
            } catch (DataAccessException ex) {
                operationFailed(errors, ex);
                return false;
            }
            return true;
        }

        return false;
    }

    @Transactional(readOnly = true)
    public StreamingResolution downloadFile(Long attachmentId) throws FileNotFoundException {
        notNull(attachmentId);

        Attachment attachment = get(attachmentId);
        if(isNull(attachment, "attachment", null)) return null;

        FileInputStream fis = new FileInputStream(attachment.getAbsolutePath());
        return new StreamingResolution(attachment.getContentType(), fis).setFilename(attachment.getFileName());
    }


}
