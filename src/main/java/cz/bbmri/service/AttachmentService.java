package cz.bbmri.service;

import cz.bbmri.entities.Attachment;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 15:59
 * To change this template use File | Settings | File Templates.
 */
public interface AttachmentService extends BasicService<Attachment> {

    Attachment create(Long projectId, Attachment attachment);

    List<Attachment> getAttachmentsByProject(Long projectId);

    List<Attachment> getSortedAttachments(Long projectId, String orderByParam, boolean desc);

}
