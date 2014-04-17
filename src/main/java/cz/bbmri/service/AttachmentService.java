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
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 * @version 1.0
 */

public interface AttachmentService extends Update<Attachment> {

    int createAttachment(FileBean fileBean,
                          AttachmentType attachmentType,
                          Long projectId,
                          ValidationErrors errors,
                          Long loggedUserId);

    List<Attachment> getSortedAttachments(Long projectId, String orderByParam, boolean desc);

    StreamingResolution downloadFile(Long attachmentId) throws FileNotFoundException;

    boolean deleteAttachment(Long attachmentId, ValidationErrors errors, Long loggedUserId);



}
