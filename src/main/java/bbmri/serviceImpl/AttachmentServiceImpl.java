package bbmri.serviceImpl;

import bbmri.dao.AttachmentDao;
import bbmri.dao.ProjectDao;
import bbmri.entities.Attachment;
import bbmri.entities.Project;
import bbmri.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ori
 * Date: 29.8.13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service
public class AttachmentServiceImpl extends BasicServiceImpl implements AttachmentService {

    @Autowired
       private ProjectDao projectDao;

       @Autowired
       private AttachmentDao attachmentDao;

    public String getAttachmentPath(Attachment attachment) {
        notNull(attachment);
        return attachmentDao.getPath(attachment);
    }

    public Attachment get(Long id) {
        notNull(id);
        return attachmentDao.get(id);
    }

    public void create(Long projectId, Attachment attachment) {
           notNull(projectId);
           notNull(attachment);

           Project projectDB = projectDao.get(projectId);
           if(projectDB == null){
               return;
               // TODO: exception
           }

           attachment.setProject(projectDB);
           attachmentDao.create(attachment);
           projectDB.getAttachments().add(attachment);
           projectDao.update(projectDB);
       }

    public List<Attachment> all(){
        return attachmentDao.all();
    }

    public void remove(Long id){
        // TODO

    }

    public Attachment update(Attachment attachment){
        // TODO
        return null;
    }

    public Integer count(){
        return attachmentDao.count();
    }


      /*
    public Attachment getAttachmentByProject(Long projectId, AttachmentType attachmentType) {
        notNull(projectId);
        notNull(attachmentType);

        Project projectDB = projectDao.get(projectId);
        if(projectDB == null){
            return null;
            //TODO: exception
        }

        List<Attachment> attachments = projectDB.getAttachments();
        if (attachments != null) {
            for (Attachment attachment : attachments) {
                if (attachment.getAttachmentType().equals(attachmentType)) {
                    return attachment;
                }
            }
        }
        return null;
    } */



    public List<Attachment> getAttachmentsByProject(Long projectId) {
        Project projectDB = projectDao.get(projectId);
        List<Attachment> attachments = attachmentDao.all();
        List<Attachment> results = new ArrayList<Attachment>();
        for (int i = 0; i < attachments.size(); i++) {
            if (attachments.get(i).getProject().equals(projectDB)) {
                results.add(attachments.get(i));
            }
        }
        return results;
    }
}
