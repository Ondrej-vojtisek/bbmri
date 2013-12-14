package cz.bbmri.service.impl;

import cz.bbmri.dao.AttachmentDao;
import cz.bbmri.dao.ProjectDao;
import cz.bbmri.entities.Attachment;
import cz.bbmri.entities.Project;
import cz.bbmri.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Attachment get(Long id) {
        notNull(id);
        return attachmentDao.get(id);
    }

    public Attachment create(Long projectId, Attachment attachment) {
        notNull(projectId);
        notNull(attachment);

        Project projectDB = projectDao.get(projectId);
        if (projectDB == null) {
            return null;
            // TODO: exception
        }

        attachment.setProject(projectDB);
        attachmentDao.create(attachment);
        projectDB.getAttachments().add(attachment);
        projectDao.update(projectDB);
        return attachment;
    }

    @Transactional(readOnly = true)
    public List<Attachment> all() {
        return attachmentDao.all();
    }

    public boolean remove(Long id) {
        notNull(id);
        Attachment attachment = attachmentDao.get(id);

        if (attachment == null) {
            return false;
        }
        attachmentDao.remove(attachment);
        return true;
    }

    public Attachment update(Attachment attachment) {
        notNull(attachment);
        Attachment attachmentDB = attachmentDao.getAttachmentByPath(attachment.getAbsolutePath());
        if(attachmentDB == null){
            return null;
        }
        attachmentDB.setAttachmentType(attachment.getAttachmentType());
        attachmentDB.setContentType(attachment.getContentType());
        attachmentDB.setFileName(attachment.getFileName());
        attachmentDB.setSize(attachment.getSize());
        attachmentDao.update(attachmentDB);
        return attachmentDB;
    }

    @Transactional(readOnly = true)
    public Integer count() {
        return attachmentDao.count();
    }

    @Transactional(readOnly = true)
    public List<Attachment> getAttachmentsByProject(Long projectId) {
        Project projectDB = projectDao.get(projectId);
        notNull(projectDB);
        return attachmentDao.getAttachmentsByProject(projectDB);
    }
}
