package cz.bbmri.service;

import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.enumeration.AttachmentType;
import cz.bbmri.service.simpleService.Update;
import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.validation.ValidationErrors;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * API for handling Attachments
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface AttachmentService extends Update<Attachment> {

    /**
     * Store attachment on server in project folder, create instance of Attachment and stores it in DB.
     *
     * @param fileBean       - File submitted by HTTP POST (data uploaded by user)
     * @param attachmentType - description what is the purpose of data - i.e. MTA
     * @param projectId      - attachment will be assigned to project identified by the projectId
     * @param errors         - in case of error, error messages will be stored into errors
     * @param loggedUserId   - ID of event initiator. Notification about new file will be sent to project team except
     *                       initiator (BROADCAST)
     * @return SUCCESS, NOT_SUCCESS or OVERWRITTEN (file was uploaded correctly, attachment with same name
     *         has been overwritten)
     */
    int createAttachment(FileBean fileBean,
                         AttachmentType attachmentType,
                         Long projectId,
                         ValidationErrors errors,
                         Long loggedUserId);

    /**
     * Provide sorted list of attachments (associated with the project) by given parameters and with order specification
     *
     * @param projectId    - project identifier
     * @param orderByParam - which column (attribute) will be used to sort
     * @param desc         - order of sort. True means DESC
     * @return sorted list of attachments
     */
    List<Attachment> getSortedAttachments(Long projectId, String orderByParam, boolean desc);

    /**
     * Return uploaded file stored on server.
     *
     * @param attachmentId - ID of attachment stored in DB
     * @return file stored on server if present
     * @throws FileNotFoundException
     */
    StreamingResolution downloadFile(Long attachmentId) throws FileNotFoundException;

    /**
     * Delete stored file and delete record about it.
     *
     * @param attachmentId - attachment that will be deleted
     * @param errors       - in case of error, error messages will be stored into errors
     * @param loggedUserId - ID of event initiator. Notification about new file will be sent to project team except
     *                     initiator (BROADCAST)
     * @return true/false
     */
    boolean deleteAttachment(Long attachmentId, ValidationErrors errors, Long loggedUserId);


}
