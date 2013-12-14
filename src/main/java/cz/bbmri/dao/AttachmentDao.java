package cz.bbmri.dao;

import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;

import java.util.List;

/**
 *
 * User: Ori
 * Date: 25.2.13
 * Time: 21:51
 * To change this template use File | Settings | File Templates.
 */
public interface AttachmentDao extends BasicDao<Attachment> {

    List<Attachment> getAttachmentsByProject(Project project);

    Attachment getAttachmentByPath(String path);
}
