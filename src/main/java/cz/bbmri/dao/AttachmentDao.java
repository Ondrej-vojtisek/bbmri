package cz.bbmri.dao;

import cz.bbmri.dao.simple.BasicDao;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;

import java.util.List;

/**
 * TODO
 *
 * @author Ondrej Vojtisek (ondra.vojtisek@gmail.com)
 */

public interface AttachmentDao extends BasicDao<Attachment> {

    List<Attachment> getAttachmentsByProject(Project project);

    Attachment getAttachmentByPath(String path);

    List<Attachment> getAttachmentSorted(Project project, String orderByParam, boolean desc);
}
