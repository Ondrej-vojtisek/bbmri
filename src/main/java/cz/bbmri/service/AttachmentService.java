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
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
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
